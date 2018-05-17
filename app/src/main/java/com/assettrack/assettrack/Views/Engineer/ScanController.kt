package com.assettrack.assettrack.Views.Engineer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.widget.Toast
import com.assettrack.assettrack.R
import com.assettrack.assettrack.Utils.MyToast
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder
import com.google.android.gms.vision.barcode.Barcode
import java.util.ArrayList

class ScanController {



    private fun startScan(activity: Activity) {
        /**
         * Build a new MaterialBarcodeScanner
         */
        val materialBarcodeScanner = MaterialBarcodeScannerBuilder()
                .withActivity(activity)
                .withBackfacingCamera()
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withCenterTracker()
                .withOnly2DScanning()
                .withBarcodeFormats(Barcode.AZTEC or Barcode.EAN_13 or Barcode.CODE_93)
                // .withBackfacingCamera()
                // .withText("Scanning...")
                .withResultListener { barcode ->
                    if (barcode.rawValue.equals("")) {



                    } else {

                    }
                }
                .build()
        materialBarcodeScanner.startScan()
    }

}