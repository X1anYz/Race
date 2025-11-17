package tw.edu.pu.csim.tcyang.race.ui.theme

class Horse() {
    var HorseX = 0 // 用於自動奔跑邏輯的座標 (目前未用於繪製)
    var HorseY = 100 // 用於自動奔跑邏輯的座標 (目前未用於繪製)
    var HorseNo = 0 // 圖片編號，控制圖片切換

    fun Run(){
        // 賽馬圖片處理：負責切換圖片編號
        HorseNo ++
        if (HorseNo > 3){
            HorseNo = 0
        }

        // 馬匹的 X 座標增加 (用於自動奔跑的邏輯，與圓圈同步)
        HorseX += (10..30).random()
    }
}