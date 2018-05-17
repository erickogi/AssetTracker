package com.assettrack.assettrack.Interfaces.UtilListeners

import android.widget.ImageView

/**
 * Created by Eric on 12/18/2017.
 */

interface AppOnclickRecyclerListener {
    fun onClickListener(position: Int)

    fun onLongClickListener(position: Int)

    fun onClickListener(adapterPosition: Int, thumbnail: ImageView, select: ImageView)

    // void onDeleteListener(int adapterPosition);
}
