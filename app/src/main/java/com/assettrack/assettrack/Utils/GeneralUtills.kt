package com.assettrack.assettrack.Utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.widget.*
import com.assettrack.assettrack.Interfaces.UtilListeners.downloadListener
import com.assettrack.assettrack.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.*
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class GeneralUtills {

    private val sNextGeneratedId = AtomicInteger(1)
    internal lateinit var path: String
    private val spinner: Spinner? = null
    private val arrayAdapter: ArrayAdapter<*>? = null
    private var downloaded = 0
    internal lateinit var  context: Context
    private val bitmapString = ""

    constructor(context: Context) {
        this.context = context
    }


    constructor()


    fun getRandon(max: Int, min: Int): Int {
        val r = Random()
        return r.nextInt(max - min + 1) + min
    }

    /**
     * @param target
     * @return
     */
    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    /**
     * @param mobile
     * @return
     */
    fun isValidPhoneNumber(mobile: String): Boolean {
        val regEx = "^[0-9]{10}$"
        return mobile.matches(regEx.toRegex())
    }

    /**
     * @param spinner
     * @param arrayAdapter
     */
    fun setSpinner(spinner: Spinner, arrayAdapter: ArrayAdapter<*>): Spinner {

        spinner.adapter = arrayAdapter

        return spinner

    }

    /**
     * @param spinner
     * @param pos
     * @return
     */
    fun setSpinner(spinner: Spinner, pos: Int): Spinner {

        spinner.setSelection(pos)


        return spinner


    }

    /**
     * @param textInputEditText
     * @return
     */
    fun isFilledTextInputEditText(textInputEditText: TextInputEditText?): Boolean {
        if (textInputEditText == null || textInputEditText.text.toString() == "") {
            if (textInputEditText != null) {
                textInputEditText.error = "Required"
            }
            return false
        }
        return true

    }

    /**
     * @param field
     * @return
     */
    fun validateFields(field: TextInputEditText): Boolean {
        return isValidEmail(field.text.toString()) || isValidPhoneNumber(field.text.toString())

    }

    /**
     * Generate a value suitable for use in .
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    fun generateViewId(): Int {
        while (true) {
            val result = sNextGeneratedId.get()
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            var newValue = result + 1
            if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result
            }
        }
    }

    fun isFilledTextInputEditText(edttitle: EditText?): Boolean {
        if (edttitle == null || edttitle.text.toString() == "") {
            if (edttitle != null) {
                edttitle.error = "Required"
            }
            return false
        }
        return true


    }

//    public void upload(Context context,String path, ProgressBar progressBar){
//        Ion.with(context).load( "https://koush.clockworkmod.com/test/echo")
//                .uploadProgressBar(progressBar)
//                .setMultipartFile("filename.pdf", new File(path))
//                .asJsonObject()
//                .setCallback((e, result) -> {
//
//                });
//    }

    fun sanitizePhone(phone: String): String {

        if (phone == "") {
            return ""
        }

        if ((phone.length < 11) and phone.startsWith("0")) {
            return phone.replaceFirst("^0".toRegex(), "254")
        }
        return if (phone.length == 13 && phone.startsWith("+")) {
            phone.replaceFirst("^+".toRegex(), "")
        } else phone
    }

    fun base64ToBitmap(b64: String): Bitmap? {
        try {
            val imageAsBytes = Base64.decode(b64.toByteArray(), Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
        } catch (nm: Exception) {
            return null
        }

    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun getStringImage(bmp: Bitmap?): String {
        var re = "n"
        try {
            // create lots of objects here and stash them somewhere
            val baos = ByteArrayOutputStream()
            if (bmp != null) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val imageBytes = baos.toByteArray()
                re = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            }
        } catch (E: OutOfMemoryError) {

            // release some (all) of the above objects
        }


        //        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        //        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        //        byte [] b = baos.toByteArray();
        //        return Base64.encodeToString(b, Base64.DEFAULT);
        return re
    }

    fun getStringImage5(bmp: Bitmap): String {
        //        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        //        byte[] imageBytes = baos.toByteArray();
        //        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        //

        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
        // return encodedImage;
    }

    fun loadImagesFromStorage(path: String, context: Context): Bitmap? {
        var b: Bitmap? = null
        try {
            val f = File(path)
            b = BitmapFactory.decodeStream(FileInputStream(f))

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Log.d("loadException", e.toString())
        }

        return b
    }

    fun isEmptyOrNullOrnull(s: String?, msg: String): Boolean {
        if (s != null && !s.isEmpty() && s != "null" && s != "Null") return false
        errorToast(msg)
        return true
    }

    /**
     * @param radioGroup
     * @param msg
     * @param context
     * @return
     */
    fun isSelected_RadioGroup(radioGroup: RadioGroup, msg: String): Boolean {

        if (radioGroup.checkedRadioButtonId > -1) {

            return true
        }
        errorToast(msg)
        return false

    }

    /**
     * @param spinner
     * @param errmsg
     * @return
     */
    fun isSpinnerSelected(spinner: Spinner, errmsg: String): Boolean {
        if (spinner.selectedItemPosition < 1) {
            errorToast(errmsg)
            return false
        }
        return true
    }

    /**
     * @param button
     * @param defaultText
     * @param errMsg
     *
     * @return
     */
    fun isButtonTextChanged(button: Button, defaultText: String, errMsg: String): Boolean {
        if (button.text.toString() == defaultText) {
            errorToast(errMsg)
            return false
        }
        return true
    }

    /**
     * @param msg
     */
    private fun errorToast(msg: String) {
        MyToast.toast(msg, context, R.drawable.ic_error_outline_black_24dp, Toast.LENGTH_LONG)
    }

    fun saveProfilePic(resizedbitmap: Bitmap, name: String): String {
        val cw = ContextWrapper(context.applicationContext)
        val directory = cw.getDir("images", Context.MODE_PRIVATE)
        if (!directory.exists()) {
            directory.mkdir()
        }
        val mypath = File(directory, name + "thumbnail.png")

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            //   new AndroidBmpUtil().save(bmImage, file);
            //resizedbitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            AndroidBmpUtil().save(resizedbitmap, mypath.path)
            val androidBmpUtil = AndroidBmpUtil()

            if (androidBmpUtil.save(resizedbitmap, mypath.absolutePath)) {
                Log.d("mypathsave", mypath.absolutePath)
            }
            fos.close()
        } catch (e: Exception) {
            Log.e("SAVE_IMAGE", e.message, e)
        }

        return mypath.absolutePath
    }

    fun loadImageFromStorage(name: String): Bitmap? {
        var b: Bitmap? = null
        try {
            val cw = ContextWrapper(context.applicationContext)
            val path1 = cw.getDir("images", Context.MODE_PRIVATE)
            val f = File(path1, name + "thumbnail.png")

            Log.d("drawingbit", f.toString() + "    \n")
            b = BitmapFactory.decodeStream(FileInputStream(f))
            // ImageView img = (ImageView) findViewById(R.id.viewImage);
            // img.setImageBitmap(b);
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Log.d("loadException", e.toString())
        }

        return b
    }

    fun loadImageFromStorage2(path: String): Bitmap? {
        var b: Bitmap? = null
        try {
            //  ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
            //  File path1 = cw.getDir("images", Context.MODE_PRIVATE);
            val f = File(path)

            // Log.d("drawingbit", f.toString() + "    \n");
            b = BitmapFactory.decodeStream(FileInputStream(f))
            // ImageView img = (ImageView) findViewById(R.id.viewImage);
            // img.setImageBitmap(b);
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            // Log.d("loadException", e.toString());
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            //Log.d("outOfMemory", e.toString());
        }

        return b
    }

    fun saveBitmap(id: String, photoUrl: String, downloadListener: downloadListener): String {
        path = photoUrl
        Picasso.with(context).load(photoUrl).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                Log.d("onBitmap", "success")
                path = saveProfilePic(bitmap, id)
                downloadListener.onDownloaded(path)
                //Toast.makeText(context,"Success",Toast.LENGTH_LONG);

            }

            override fun onBitmapFailed(errorDrawable: Drawable) {

                downloadListener.onError("error")
                Log.d("onBitmap", "failed$photoUrl")
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable) {

            }
        })
        return path
    }


}