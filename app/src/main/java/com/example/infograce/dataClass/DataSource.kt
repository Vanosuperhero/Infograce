package com.example.infograce.dataClass

import android.graphics.Color
import com.example.infograce.R
import com.example.infograce.dataClass.models.Layer
import com.example.infograce.dataClass.models.Source
import java.util.*
import kotlin.collections.ArrayList

class DataSource {
    companion object {
        fun createDataSet(): ArrayList<RecyclerViewItems> {
            val list = ArrayList<RecyclerViewItems>()
            list.add(
                RecyclerViewItems.LayersGroup(
                    "Зоны и районы",
                    "11.03.2022",
                    Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("METEO"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
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
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("CTR"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "CTR",
                        sourceLayer = "CTR",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "1e6rib3l",
                            url = "mapbox://azmetov.1e6rib3l"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("CTA__"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "CTA__",
                        sourceLayer = "CTA__",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "1e6rib3l",
                            url = "mapbox://azmetov.1e6rib3l"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable(".geojson"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = ".geojson",
                        sourceLayer = ".geojson",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "1e6rib3l",
                            url = "mapbox://azmetov.1e6rib3l"
                        )
                    )
                )
            )
            // Зоны ограничения полетов azmetov.6qrv5562
            list.add(
                RecyclerViewItems.LayersGroup(
                    "Зоны ограничения полетов",
                    "11.03.2022",
                    Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UA"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UA",
                        sourceLayer = "UA",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "6qrv5562",
                            url = "mapbox://azmetov.6qrv5562"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UH"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UH",
                        sourceLayer = "UH",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "6qrv5562",
                            url = "mapbox://azmetov.6qrv5562"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UK"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UK",
                        sourceLayer = "UK",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "6qrv5562",
                            url = "mapbox://azmetov.6qrv5562"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UL"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UL",
                        sourceLayer = "UL",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "6qrv5562",
                            url = "mapbox://azmetov.6qrv5562"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UM"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UM",
                        sourceLayer = "UM",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "6qrv5562",
                            url = "mapbox://azmetov.6qrv5562"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UN"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UN",
                        sourceLayer = "UN",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "6qrv5562",
                            url = "mapbox://azmetov.6qrv5562"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UR"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UR",
                        sourceLayer = "UR",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "6qrv5562",
                            url = "mapbox://azmetov.6qrv5562"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("US"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "US",
                        sourceLayer = "US",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "6qrv5562",
                            url = "mapbox://azmetov.6qrv5562"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UU"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UU",
                        sourceLayer = "UU",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "6qrv5562",
                            url = "mapbox://azmetov.6qrv5562"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UW"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UW",
                        sourceLayer = "UW",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "6qrv5562",
                            url = "mapbox://azmetov.6qrv5562"
                        )
                    )
                )
            )
                // Опасные зоны azmetov.0v8a3gs8
            list.add(
                RecyclerViewItems.LayersGroup(
                    "Опасные зоны",
                    "11.03.2022",
                    Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UA"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UA",
                        sourceLayer = "UA",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "0v8a3gs8",
                            url = "mapbox://azmetov.0v8a3gs8"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UH"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UH",
                        sourceLayer = "UH",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "0v8a3gs8",
                            url = "mapbox://azmetov.0v8a3gs8"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UK"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UK",
                        sourceLayer = "UK",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "0v8a3gs8",
                            url = "mapbox://azmetov.0v8a3gs8"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UL"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UL",
                        sourceLayer = "UL",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "0v8a3gs8",
                            url = "mapbox://azmetov.0v8a3gs8"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UM"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UM",
                        sourceLayer = "UM",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "0v8a3gs8",
                            url = "mapbox://azmetov.0v8a3gs8"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UR"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UR",
                        sourceLayer = "UR",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "0v8a3gs8",
                            url = "mapbox://azmetov.0v8a3gs8"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.LayersGroup(
                    "Запретные зоны",
                    "11.03.2022",
                    Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UA"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UA",
                        sourceLayer = "UA",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "4dfspkho",
                            url = "mapbox://azmetov.4dfspkho"
                        )
                    )
                )
            )

            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UH"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UH",
                        sourceLayer = "UH",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "4dfspkho",
                            url = "mapbox://azmetov.4dfspkho"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UK"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UK",
                        sourceLayer = "UK",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "4dfspkho",
                            url = "mapbox://azmetov.4dfspkho"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UL"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UL",
                        sourceLayer = "UL",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "4dfspkho",
                            url = "mapbox://azmetov.4dfspkho"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UM"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UM",
                        sourceLayer = "UM",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "4dfspkho",
                            url = "mapbox://azmetov.4dfspkho"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UN"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UN",
                        sourceLayer = "UN",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "4dfspkho",
                            url = "mapbox://azmetov.4dfspkho"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UR"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UR",
                        sourceLayer = "UR",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "4dfspkho",
                            url = "mapbox://azmetov.4dfspkho"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("US"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "US",
                        sourceLayer = "US",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "4dfspkho",
                            url = "mapbox://azmetov.4dfspkho"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UU"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UU",
                        sourceLayer = "UU",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "4dfspkho",
                            url = "mapbox://azmetov.4dfspkho"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("UW"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "UW",
                        sourceLayer = "UW",
                        fillColor = getRandomColor(),
                        fillOpacity = 0.5,
                        fillOutlineColor = getRandomColor(),
                        source = Source(
                            sourceId = "4dfspkho",
                            url = "mapbox://azmetov.4dfspkho"
                        )
                    )
                )
            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.waypoint,
//                    RecyclerViewItems.TitleSpannable("Сигналы о лесоизменениях, тестовая выборка с ув-ным шагом"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.BLUE
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.line,
//                    RecyclerViewItems.TitleSpannable("Преграды для прохождения огня"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.BLUE
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 02.07.2021"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.BLUE
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 02.07.2021"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.BLUE
//                )
//            )
//            list.add(
//                RecyclerViewItems.LayersGroup(
//                    "Общие слои",
//                    "11.03.2022",
//                    Group.RED
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.folder,
//                    RecyclerViewItems.TitleSpannable("Папка со слоями"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.RED
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon_hatched_2,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 01.07.2021"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.RED
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.RED
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.RED
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.RED
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.RED
//                )
//            )
            return list
        }

        private fun getRandomColor() =
            Color.argb(
                255,
                Random().nextInt(256),
                Random().nextInt(256),
                Random().nextInt(256)
            )
    }
}