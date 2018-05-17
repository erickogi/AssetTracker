package com.assettrack.assettrack.Data

import android.util.Log

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener

import java.util.HashMap

class Request {


    fun postRequest(url: String, params: HashMap<String, String>, token: String?, listener: RequestListener) {

        var mtoken = ""
        if (token != null) {

            mtoken = token

        }

        AndroidNetworking.post(url)
                .addBodyParameter(params)
                .addHeaders("Authorization", "Bearer $mtoken")
                .addHeaders("Accept", "application/json")


                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String) {
                        // do anything with response
                        listener.onSuccess(response)

                    }

                    override fun onError(error: ANError) {
                        // handle error

                        Log.d("eww", error.toString())
                        listener.onError("" + error.errorBody)
                    }
                })
    }

    fun getRequest(url: String, token: String?, listener: RequestListener) {
        var mtoken = ""
        if (token != null) {

            mtoken = token

        }


        AndroidNetworking.get(url)


                .addHeaders("Authorization", "Bearer $mtoken")
                .addHeaders("Accept", "application/json")


                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String) {
                        // do anything with response
                        listener.onSuccess(response)

                    }

                    override fun onError(error: ANError) {
                        // handle error
                        listener.onError(error.errorBody)
                    }
                })
    }

}
