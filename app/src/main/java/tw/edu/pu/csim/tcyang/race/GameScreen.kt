package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {

    val studentName = "張佑先"
    val scoreDisplay = "分數: ${gameViewModel.score}"
    // 確保您的資源檔 R.drawable.horse0 到 horse3 存在
    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)
    ){
        Canvas (modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume() // 告訴系統已經處理了這個事件
                    gameViewModel.MoveCircle( dragAmount.x, dragAmount.y)
                }
            }
        ) {
            // 🎯 關鍵：使用 horse.HorseNo 來切換圖片，確保動畫效果。
            // 使用 circleX/Y 來設定位置，確保可以被拖曳。
            drawImage(
                image = imageBitmaps[gameViewModel.horse.HorseNo],
                dstOffset = IntOffset(gameViewModel.circleX.toInt(), gameViewModel.circleY.toInt()),
                dstSize = IntSize(300, 300)
            )
        }

        // 使用 Column 將文字和按鈕垂直排列
        Column(
            // 讓 Column 靠左上角顯示
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            // 顯示姓名和分數及其他訊息
            Text(text = "$studentName\n$scoreDisplay\n${message}${gameViewModel.screenWidthPx.toString()}*${gameViewModel.screenHeightPx.toString()}")

            // 遊戲開始按鈕
            Button(onClick = {gameViewModel.gameRunning = true
                gameViewModel.StartGame()
            }
            ){
                Text("遊戲開始")
            }
        }
    }
}