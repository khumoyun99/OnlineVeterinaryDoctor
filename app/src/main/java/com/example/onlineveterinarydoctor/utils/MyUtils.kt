package com.example.onlineveterinarydoctor.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


fun <T :ViewBinding> T.scope(block: T.() -> Unit) {
    block(this)
}

fun Fragment.vibratePhone() {
    val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(200)
    }
}