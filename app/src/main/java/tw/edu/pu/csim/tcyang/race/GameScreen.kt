package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column // 👈 導入 Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment // 👈 導入 Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
    val imageBitmap = ImageBitmap.imageResource(R.drawable.horse0)

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
            // 繪製圓形
            drawImage(
                image = imageBitmap,
                // 使用 circleX 和 circleY 作為圖片的左上角位置
                dstOffset = IntOffset(gameViewModel.circleX.toInt(), gameViewModel.circleY.toInt()),
                dstSize = IntSize(300, 300)
            )
        }

        // 👇 使用 Column 將文字和按鈕垂直排列
        Column(
            // 讓 Column 靠左上角顯示
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            // 顯示姓名和分數及其他訊息
            Text(text = "$studentName\n$scoreDisplay\n${message}${gameViewModel.screenWidthPx.toString()}*${gameViewModel.screenHeightPx.toString()}")

            // 遊戲開始按鈕，現在會在 Text 下方
            Button(onClick = {gameViewModel.gameRunning = true
                gameViewModel.StartGame()
            }
            ){
                Text("遊戲開始")
            }
        }
    }
}