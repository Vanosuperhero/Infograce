package com.example.infograce.dataClass

import com.example.infograce.R

class DataSource {
    companion object{
        fun createDataSet(search: String):ArrayList<Layers> {
            val list = ArrayList<Layers>()
            list.add(
                Layers(
                    R.drawable.grometry_collection,
                    "Слой делян",
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18"
                )
            )
            list.add(
                Layers(
                    R.drawable.waypoint,
                    "Сигналы о лесоизменениях, тестовая выборка с ув-ным шагом",
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18"
                )
            )
            list.add(
                Layers(
                    R.drawable.line,
                    "Преграды для прохождения огня",
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18"
                )
            )
            list.add(
                Layers(
                    R.drawable.polygon,
                    "Маска облачности от 02.07.2021",
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18"
                )
            )
            list.add(
                Layers(
                    R.drawable.polygon,
                    "Маска облачности от 02.07.2021",
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18"
                )
            )
            list.add(
                Layers(
                    R.drawable.folder,
                    "Папка со слоями",
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18"
                )
            )
            list.add(
                Layers(
                    R.drawable.polygon_hatched_2,
                    "Маска облачности от 01.07.2021",
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18"
                )
            )
            list.add(
                Layers(
                    R.drawable.polygon,
                    "Маска облачности от 12.01.2022",
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18"
                )
            )
            list.add(
                Layers(
                    R.drawable.polygon,
                    "Маска облачности от 12.01.2022",
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18"
                )
            )
            list.add(
                Layers(
                    R.drawable.polygon,
                    "Маска облачности от 12.01.2022",
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18"
                )
            )
            list.add(
                Layers(
                    R.drawable.polygon,
                    "Маска облачности от 12.01.2022",
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18"
                )
            )
            list.add(
                Layers(
                    R.drawable.polygon,
                    "Маска облачности от 12.01.2022",
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18"
                )
            )
            return list
        }
        var addLayer = listOf(
            Layers(
                R.drawable.polygon,
                "Маска облачности от 12.01.2022",
                "Прозрачность: 60%",
                "Синхр.: 12.02.2022",
                "Эл-ов: 23",
                "Зум: 16-18"
            ))
    }

}