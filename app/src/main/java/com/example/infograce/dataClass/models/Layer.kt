package com.example.infograce.dataClass.models

import androidx.annotation.ColorInt

data class Layer(
    val layerId: String,
    val sourceLayer: String,
    @ColorInt val fillColor: Int,
    @ColorInt val fillOutlineColor: Int,
    val fillOpacity: Double,
    val source: Source,
)