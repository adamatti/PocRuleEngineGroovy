package adamatti

import com.beust.klaxon.Klaxon

fun String.toJson() = Klaxon().parse<MutableMap<*, *>>(this)
