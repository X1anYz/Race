package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random

// Data class to represent a single horse in the race.
// 賽馬的資料類別，使用 mutableIntStateOf 來確保 Compose UI 能響應狀態變化。
class Horse(
    // horseIndex 用於設置初始 Y 位置。
    private val horseIndex: Int = 0,
    initialY: Int = 0,
    val Id: Int // 馬匹的編號 (1, 2, 3)
) {
    // Current X position (horizontal movement) - 當前 X 軸位置
    var HorseX by mutableIntStateOf(0)

    // Current Y position (vertical position on track) - 當前 Y 軸位置
    var HorseY by mutableIntStateOf(initialY)

    // Image number (0-3) for animation frames - 圖片編號 (0-3) 用於動畫幀
    var HorseNo by mutableIntStateOf(0)

    /**
     * Updates the horse's position and advances the animation frame.
     * 更新賽馬的位置並推進動畫幀。
     */
    fun Run() {
        // 賽馬移動處理：每一步以 10 到 30 的隨機距離前進
        HorseX += Random.nextInt(10, 31) // Random.nextInt(10, 31) 包含 10 到 30

        // 循環動畫幀 (0, 1, 2, 3)
        HorseNo = (HorseNo + 1) % 4
    }
}