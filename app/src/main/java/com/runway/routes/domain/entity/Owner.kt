package com.runway.routes.domain.entity


enum class Owner(val value: String) {

    CA("ГА"),
    GA("АОН"),
    EA("ЭА"),
    MD("МО"),
    MUS("МВД"),
    FSB("ФСБ"),
    DOSAAF("ДОСААФ"),
    MES("МЧС");

    companion object {
        fun fromString(value: String): Owner? {
            values().forEach { owner ->
                if (value == owner.value) return owner
            }
            return null
        }
    }
}