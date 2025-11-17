package tw.edu.pu.csim.tcyang.race

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.window.layout.WindowMetricsCalculator
import tw.edu.pu.csim.tcyang.race.ui.theme.RaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 強迫橫式螢幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        // 隱藏狀態列
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())

        // 隱藏下方巡覽列
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

        // 確保內容延伸到至邊緣
        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )

        // 計算螢幕像素尺寸
        val windowMetricsCalculator =
            WindowMetricsCalculator.getOrCreate()

        val currentWindowMetrics =
            windowMetricsCalculator.computeCurrentWindowMetrics(this)

        val bounds = currentWindowMetrics.bounds

        val screenWidthPx = bounds.width().toFloat()
        val screenHeightPx = bounds.height().toFloat()

        val gameViewModel: GameViewModel by viewModels()
        gameViewModel.SetGameSize(screenWidthPx, screenHeightPx)

        setContent {
            // 假設 RaceTheme 存在並使用
            RaceTheme {
                // (1) 修改文字
                GameScreen(message = "賽馬遊戲(作者：張佑先)", gameViewModel)
            }
        }
    }
}