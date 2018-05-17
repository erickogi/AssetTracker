package com.assettrack.assettrack.Interfaces.UtilListeners

//import com.android.volley.VolleyError;

//import com.android.volley.error.VolleyError;

import com.android.volley.VolleyError

/**
 * Created by Eric on 12/15/2017.
 */

interface RequestListener {
    fun onError(error: VolleyError)

    fun onError(error: String)

    fun onSuccess(response: String)
}
