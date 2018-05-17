package com.assettrack.assettrack.Interfaces.UtilListeners

/**
 * Created by Eric on 1/17/2018.
 */

interface downloadListener {
    fun onDownloaded(path: String)

    fun onError(error: String)

    fun onDownloaded(datam: Array<Array<String>>)
}
