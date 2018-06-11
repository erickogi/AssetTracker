package com.assettrack.assettrack.Data

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener
import com.assettrack.assettrack.Models.AssetModel
import com.google.gson.Gson
import org.json.JSONObject
import java.io.File
import java.util.*

class Request {


    companion object {
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
                            //  listener.onError(error)
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


        fun putRequest(url: String, params: JSONObject, token: String?, listener: RequestListener) {
            var mtoken = ""
            if (token != null) {

                mtoken = token

            }

            Log.d("putrequest", params.toString())
            Log.d("putrequest", url)

            AndroidNetworking.put(url)


                    .addHeaders("Authorization", "Bearer $mtoken")
                    .addHeaders("Accept", "application/json")

                    //.addBodyParameter(params)
                    // .addApplicationJsonBody(params)
                    .addJSONObjectBody(params)
                    //.setContentType(ContentT)

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

        fun deleteRequest(url: String, token: String?, listener: RequestListener) {
            var mtoken = ""
            if (token != null) {

                mtoken = token

            }
            Log.d("deleterequest", url)
            //Log.d("deleterequest", url)


            AndroidNetworking.delete(url)


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

        fun assetUpload(url: String, assetModel: AssetModel, path: String, token: String, listener: RequestListener) {
            val gson = Gson()
            val json = gson.toJson(assetModel.parts)

            // params.put("assetparts", json)
            // AndroidNetworking.enableLogging();
            AndroidNetworking.upload(url)


                    .addMultipartFile("image", File(path))


                    .addMultipartParameter("id", "" + assetModel.id)
                    .addMultipartParameter("Name", assetModel.asset_name)
                    .addMultipartParameter("Code", assetModel.asset_code)
                    .addMultipartParameter("State", "" + assetModel.state)
                    .addMultipartParameter("Warranty", assetModel.warranty)
                    .addMultipartParameter("WarrantyDuration", assetModel.warranty_duration)
                    .addMultipartParameter("Model", assetModel.model)
                    .addMultipartParameter("Serial", assetModel.serial)
                    .addMultipartParameter("Manufacturer", assetModel.manufacturer)
                    .addMultipartParameter("ManufacturerYear", assetModel.yr_of_manufacture)
                    .addMultipartParameter("NextService", assetModel.nextservice)
                    .addMultipartParameter("Customer", assetModel.customers_id)
                    .addMultipartParameter("ContactPosition", assetModel.contact_person_position)
                    .addMultipartParameter("Department", assetModel.department)
                    .addMultipartParameter("RoomStatus", assetModel.roomsizestatus)
                    .addMultipartParameter("RoomSpecs", assetModel.room_meets_specification)
                    .addMultipartParameter("RoomExplanation", assetModel.room_explanation)
                    .addMultipartParameter("Engineer", assetModel.engineer_id)
                    .addMultipartParameter("Trainees", assetModel.trainees)
                    .addMultipartParameter("TraineesPosition", assetModel.trainee_position)
                    .addMultipartParameter("EngineerRemarks", assetModel.engineer_remarks)
                    .addMultipartParameter("InstalationDate", assetModel.installation_date)
                    .addMultipartParameter("RecieversDate", assetModel.recieversDate)
                    .addMultipartParameter("RecieversName", assetModel.recievers_name)
                    .addMultipartParameter("RecieversDesignation", assetModel.receiver_designation)
                    .addMultipartParameter("Security", assetModel.warranty)
                    .addMultipartParameter("RecieversComment", assetModel.receiver_comments)


                    .addMultipartParameter("assetparts", json)


//                    .addMultipartParameter("user_id", imageUpload.getUser_id())
//                    .addMultipartParameter("title", imageUpload.getTitle())
//                    .addMultipartParameter("orderstatus", imageUpload.getOrderstatus())
//                    .addMultipartParameter("products_id", imageUpload.getProducts_id())
//                    .addMultipartParameter("ordername", imageUpload.getOrdername())
//                    .addMultipartParameter("location", imageUpload.getLocation())
//                    .addMultipartParameter("amount", imageUpload.getAmount())
//                    .addMultipartParameter("count", imageUpload.getCount())
//                    .addMultipartParameter("description", imageUpload.getDescription())
//                    .addMultipartParameter("mobile", imageUpload.getMobile())
//                    .addMultipartParameter("title_id", imageUpload.getTitle_id())
//                    .addMultipartParameter("caption", imageUpload.getCaption())
//                    .addMultipartParameter("size", imageUpload.getSize())
//                    .addMultipartParameter("pagenumber", imageUpload.getPagenumber())
//                    .addMultipartParameter("countforimages", imageUpload.getCountforimages())
//                    .addMultipartParameter("orderid", imageUpload.getOrderid())
//
//                    .addMultipartParameter("frontcover", String.valueOf(imageUpload.getFront()))
//                    .addMultipartParameter("backcover", imageUpload.getBackcover())
//                    //.addMultipartParameter("backcover", imageUpload.getBackcover())


                    //                .addMultipartParameter("user_id","1")
                    //                .addMultipartParameter("title","jjkjnkj")
                    //                .addMultipartParameter("orderstatus","1")
                    //                .addMultipartParameter("products_id","1")
                    //                .addMultipartParameter("ordername","mkj")
                    //                .addMultipartParameter("location","jnjknjn")
                    //                .addMultipartParameter("amount","200")
                    //                .addMultipartParameter("count","4")
                    //                .addMultipartParameter("description","jhbj j")
                    //                .addMultipartParameter("mobile","0714406984")
                    //                .addMultipartParameter("title_id","8767868")
                    //                .addMultipartParameter("caption","hhiuy")
                    //                .addMultipartParameter("size","1")
                    //                .addMultipartParameter("pagenumber","1")
                    //                .addMultipartParameter("countforimages","7")
                    //                .addMultipartParameter("orderid","")


                    //  .addMultipartParameter("title_id","3")


                    .addHeaders("Authorization", "Bearer $token")
                    .addHeaders("Accept", "application/json")


                    .setTag("image")
                    .setPriority(Priority.HIGH)

                    .build()

//                    .setUploadProgressListener(object : UploadProgressListener() {
//                        fun onProgress(bytesUploaded: Long, totalBytes: Long) {
//                            listener.onProgress(bytesUploaded, totalBytes)
//
//                        }
//                    })
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            // do anything with response
                            Log.d("v1Img", response.toString())
                            listener.onSuccess(response.toString())

                        }

                        override fun onError(error: ANError) {
                            // handle error
                            Log.d("v1Img", error.toString() + "  " + error.errorBody)
                            listener.onError(error.errorBody)

                        }
                    })


        }

    }

}
