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

    // --- Horse Entities ---
    val horse = Horse(0, 50) // Main horse (Player/Control)
    val horses = mutableListOf<Horse>() // Competitors

    /**
     * Sets the screen dimensions and initializes the competitor horses.
     */
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h

        // Initialize competitor horses with staggered starting positions
        // Only 2 competitor horses now (original was 3)
        for (i in 0..1) { // Loop changed from 0..2 to 0..1
            // Start Y position is based on index, for stacking them visually
            val startY = 300 + (i * 300)
            horses.add(Horse(i + 1, startY))
        }
    }

    /**
     * Starts the game loop for movement and animation.
     */
    fun StartGame() {
        gameRunning = true

        // Reset positions to the start line
        horse.HorseX = 0
        horses.forEach { it.HorseX = 0 }

        viewModelScope.launch {
            while (gameRunning) {
                delay(100) // Update every 100ms

                // 1. Main Horse movement and reset
                horse.Run()
                if (horse.HorseX >= screenWidthPx - 300) {
                    // Reset the main horse to the start
                    horse.HorseX = 0
                }

                // 2. Competitor Horse movement and reset
                val finishLine = (screenWidthPx - 300).toInt() // Max X position for horse image
                for (competitor in horses) {
                    competitor.Run()
                    if (competitor.HorseX >= finishLine) {
                        // Reset the competitor horse to the start
                        competitor.HorseX = 0
                    }
                }
            }
        }
    }

    /**
     * Removed the MoveCircle function as the draggable circle is no longer present.
     */
}