package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel : ViewModel() {

    // --- Screen Dimensions ---
    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    // --- Game State ---
    var gameRunning by mutableStateOf(false)

    // (2) 獲勝訊息狀態
    var winMessage by mutableStateOf("")

    // --- Horse Entities ---
    // 總共三匹馬：horse (第1馬) 和 horses[0], horses[1] (第2, 3馬)
    // Id: 1, Y: 50 (Top)
    val horse = Horse(0, 50, 1)
    val horses = mutableListOf<Horse>() // 競爭者 (第2、3馬)

    /**
     * Sets the screen dimensions and initializes the competitor horses.
     */
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h

        // 初始化競爭者馬匹 (第2馬, 第3馬)
        for (i in 0..1) { // 兩個競爭者
            val horseId = i + 2 // ID 從 2 開始 (第2馬, 第3馬)
            // 300 + 0*300 = 300 (Y for Horse 2)
            // 300 + 1*300 = 600 (Y for Horse 3)
            val startY = 300 + (i * 300)
            horses.add(Horse(i + 1, startY, horseId))
        }
    }

    /**
     * 重設所有馬匹到起點 (X軸 = 0)
     */
    private fun ResetHorsesToStart() {
        horse.HorseX = 0
        horses.forEach { it.HorseX = 0 }
    }

    /**
     * Starts the game loop for movement and animation.
     */
    fun StartGame() {
        gameRunning = true
        winMessage = "" // 清除獲勝訊息
        ResetHorsesToStart()

        viewModelScope.launch {
            while (gameRunning) {
                delay(100) // Update every 100ms

                // 1. 所有馬匹移動
                horse.Run()
                for (competitor in horses) {
                    competitor.Run()
                }

                // 2. 檢查是否有馬匹到達終點
                CheckWinnerAndReset()
            }
        }
    }

    /**
     * 檢查是否有馬匹到達終點，如果有，則宣告獲勝並重置遊戲。
     */
    private fun CheckWinnerAndReset() {
        // 假設馬匹圖片寬度為 300，終點線為螢幕寬度減去圖片寬度
        val finishLine = (screenWidthPx - 300).toInt()

        // 建立所有馬匹的列表 (Id: 1, 2, 3)
        val allHorses = listOf(horse) + horses

        var winnerId: Int? = null

        // 檢查是否有馬匹過終點
        for (h in allHorses) {
            if (h.HorseX >= finishLine) {
                winnerId = h.Id
                break // 找到第一匹到達終點的馬
            }
        }

        if (winnerId != null) {
            // 宣告獲勝並停止遊戲
            gameRunning = false
            winMessage = "第${winnerId}馬獲勝"

            // 延遲一段時間後自動重置馬匹位置，準備下一輪
            viewModelScope.launch {
                delay(3000) // 顯示訊息 3 秒
                ResetHorsesToStart() // 所有馬匹回到起點
            }
        }
    }
}