package com.example.infograce.dataClass

import com.example.infograce.R
import com.example.infograce.dataClass.models.Layer
import com.example.infograce.dataClass.models.Source

class AddLayer {

companion object {
    fun createLayer(): RecyclerViewItems.Layers {
        return RecyclerViewItems.Layers(
            R.drawable.polygon,
            RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
            "60%",
            241,
            "16-18",
            Group.RED,
            data = Layer(
                layerId = "METEO",
                sourceLayer = "METEO",
                fillColor = 7766,
                fillOpacity = 1.0,
                fillOutlineColor = 56654,
                source = Source(
                    sourceId = "1e6rib3l",
                    url = "mapbox://azmetov.1e6rib3l"
                )
            )
        )
    }
}
}

