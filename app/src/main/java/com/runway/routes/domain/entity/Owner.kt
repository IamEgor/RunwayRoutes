package com.runway.routes.domain.entity

import com.runway.routes.R


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

fun Owner.ownerStringResource() = when (this) {
    Owner.CA -> R.string.owner_civil
    Owner.GA -> R.string.owner_general
    Owner.EA -> R.string.owner_experimental
    Owner.MD -> R.string.owner_ministry_of_defence
    Owner.MUS -> R.string.owner_ministry_of_internal_affairs
    Owner.FSB -> R.string.owner_federal_security_service
    Owner.DOSAAF -> R.string.owner_DOSAAF
    Owner.MES -> R.string.owner_ministry_of_emergency_situations
}