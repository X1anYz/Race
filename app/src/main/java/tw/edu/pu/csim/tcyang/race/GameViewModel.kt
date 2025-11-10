package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {

    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set
    var gameRunning by mutableStateOf(false)

    var circleX by mutableStateOf(0f)
    var circleY by mutableStateOf(0f)

    // *** 新增：儲存分數的變數 ***
    var score by mutableStateOf(0)
        private set // 外部只能讀取，不能直接設定

    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
    }
    fun StartGame() {
        //回到初使位置
        circleX = 100f
        circleY = screenHeightPx - 100f

        viewModelScope.launch {
            while (gameRunning) { // 每0.1秒循環
                delay(100)
                circleX += 10

                if (circleX >= screenWidthPx - 100){
                    circleX = 100f
                    // *** 修改：碰到右邊邊界時分數 + 1 ***
                    score += 1
                }
            }
        }
    }

    fun MoveCircle(x: Float, y: Float) {
        circleX += x
        circleY += y
    }


}