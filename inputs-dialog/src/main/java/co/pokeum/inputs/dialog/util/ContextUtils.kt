package co.pokeum.inputs.dialog.util

import android.content.Context

internal fun Context.dpToPixels(dp: Int) : Int {
    return (dp * resources.displayMetrics.density + 0.5).toInt()
}

internal fun Context.pixelsToDp(pixel: Int) : Int {
    return (pixel / resources.displayMetrics.density + 0.5).toInt()
}