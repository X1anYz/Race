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

// NOTE: Assumes R.drawable, GameViewModel, and associated data classes exist in the project.

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
        // 1. Game Canvas (fills the entire box)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Draw the main horse (assuming 'horse' is the player's controlled entity)
            drawImage(
                image = imageBitmaps[gameViewModel.horse.HorseNo],
                dstOffset = IntOffset(gameViewModel.horse.HorseX, gameViewModel.horse.HorseY),
                dstSize = IntSize(300, 300)
            )

            // Draw other horses/competitors
            for (i in 0 until gameViewModel.horses.size) { // Changed loop to iterate based on actual list size
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

        // 2. Overlay UI elements (Text and Button)
        // Positioned at the bottom center of the screen for controls and information
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Display message and screen dimensions using string interpolation
            Text(
                text = "$message${gameViewModel.screenWidthPx} * ${gameViewModel.screenHeightPx}"
            )

            // Start Game Button
            Button(
                onClick = {
                    gameViewModel.gameRunning = true
                    gameViewModel.StartGame()
                }
            ) {
                Text("遊戲開始") // Game Start
            }
        }
    }
}