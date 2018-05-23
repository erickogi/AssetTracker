package com.assettrack.assettrack.Constatnts

import com.assettrack.assettrack.Models.AssetModel

class GLConstants {
    companion object {
        var WORKING = 1
        var FAULTY = 2
        var WRITTEN_OFF = 3
        var ALL = 0


        var id = "0"


        var assetModel: AssetModel? = null


        fun setIsd(nid: String) {
            this.id = nid
        }

    }


}