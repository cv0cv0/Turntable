package me.gr.turntable.ext

import android.util.TypedValue
import me.gr.turntable.app.App

fun Int.toDp() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toFloat(), App.context.resources.displayMetrics)

fun Int.toSp() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, toFloat(), App.context.resources.displayMetrics)