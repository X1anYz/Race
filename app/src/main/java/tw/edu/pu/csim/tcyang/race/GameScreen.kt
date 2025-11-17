package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

// NOTE: 假設 R.drawable.horse0 到 R.drawable.horse3 圖片資源已存在。

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {

    // Load all horse image assets
    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        // 1. Game Canvas (繪製馬匹)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Draw the main horse (第1馬)
            drawImage(
                image = imageBitmaps[gameViewModel.horse.HorseNo],
                dstOffset = IntOffset(gameViewModel.horse.HorseX, gameViewModel.horse.HorseY),
                dstSize = IntSize(300, 300)
            )

            // Draw other horses/competitors (第2、3馬)
            for (i in 0 until gameViewModel.horses.size) {
                drawImage(
                    image = imageBitmaps[gameViewModel.horses[i].HorseNo],
                    dstOffset = IntOffset(
                        gameViewModel.horses[i].HorseX,
                        gameViewModel.horses[i].HorseY
                    ),
                    dstSize = IntSize(300, 300)
                )
            }
        }

        // 2. Overlay UI elements (文字和按鈕)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 顯示標題
            Text(
                text = message
            )

            // 顯示獲勝訊息
            if (gameViewModel.winMessage.isNotEmpty()) {
                Text(
                    text = gameViewModel.winMessage,
                    color = Color.Red
                )
            }

            // Start Game Button
            Button(
                onClick = {
                    // 只有在遊戲未運行時才允許重新開始
                    if (!gameViewModel.gameRunning) {
                        gameViewModel.StartGame()
                    }
                },
                enabled = !gameViewModel.gameRunning // 遊戲運行中時禁用按鈕
            ) {
                Text("遊戲開始") // Game Start
            }
        }
    }
}