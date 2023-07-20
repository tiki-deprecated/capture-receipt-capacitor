package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.microblink.FrameCharacteristics
import com.microblink.ScanOptions
import com.microblink.camera.ui.CameraScanActivity

@CapacitorPlugin(name = "ReceiptCapture")
class ReceiptCapturePlugin : Plugin() {
    private val implementation = ReceiptCapture()
    @PluginMethod
    fun echo(call: PluginCall) {
        val value = call.getString("value")
        val ret = JSObject()
        ret.put("value", implementation.echo(value!!))


        val scanOptions = ScanOptions.newBuilder()
            .frameCharacteristics(
                FrameCharacteristics.newBuilder()
                    .storeFrames(true)
                    .compressionQuality(100)
                    .externalStorage(false).build()
            )
            .logoDetection(true)
            .build()

        val bundle = Bundle()

        bundle.putParcelable(CameraScanActivity.SCAN_OPTIONS_EXTRA, scanOptions)

        val intent: Intent = Intent(context, CameraScanActivity::class.java)
            .putExtra(CameraScanActivity.BUNDLE_EXTRA, bundle)

        ActivityCompat.startActivityForResult(activity, intent, 1, null)

        call.resolve(ret)
    }
}