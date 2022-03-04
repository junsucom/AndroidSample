package com.junsu.base.models

class Version(private val value: String) : Comparable<Version> {
    private val splitted by lazy { value.split("-").first().split(".").map { it.toIntOrNull() ?: 0 } }

    override fun compareTo(other: Version): Int {
        for (i in 0 until maxOf(splitted.size, other.splitted.size)) {
            val compare = splitted.getOrElse(i) { 0 }.compareTo(other.splitted.getOrElse(i) { 0 })
            if (compare != 0)
                return compare
        }
        return 0
    }
}