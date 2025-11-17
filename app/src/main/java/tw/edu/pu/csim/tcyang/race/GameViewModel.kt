package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tw.edu.pu.csim.tcyang.race.ui.theme.Horse

class GameViewModel: ViewModel() {

    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set
    var gameRunning by mutableStateOf(false)

    // circleX/Y 作為馬匹的繪製座標 (可拖曳和自動移動)
    var circleX by mutableStateOf(100f)
    var circleY by mutableStateOf(100f)

    var score by mutableStateOf(0)
        private set
    val horse = Horse()

    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
        // 初始化馬匹的 Y 座標到螢幕底部附近
        circleY = screenHeightPx - 300f
    }

    fun StartGame() {
        // 重置為起始位置
        circleX = 100f
        circleY = screenHeightPx - 300f

        viewModelScope.launch {
            while (gameRunning) { // 每0.1秒循環
                delay(100)

                // 🎯 關鍵修正：讓 horse.Run() 獨立於 circleX 的重置條件。
                // 這樣馬匹的圖片就會每 100ms 切換一次，實現持續的奔跑動畫。
                horse.Run()

                // 自動奔跑邏輯 (circleX)
                circleX += 10

                if (circleX >= screenWidthPx - 100){
                    circleX = 100f

                    // 馬匹內部 X 座標重置
                    if (horse.HorseX >= screenWidthPx - 300){
                        horse.HorseX = 0
                    }
                }
            }
        }
    }

    fun MoveCircle(x: Float, y: Float) {
        // 拖曳功能：只負責更新座標
        circleX += x
        circleY += y
    }
}