package com.liamdegrey.rocketlaunches.network.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpcomingRocketLaunches(
    @JsonProperty("results") val rocketLaunches: Array<RocketLaunch>
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UpcomingRocketLaunches

        if (!rocketLaunches.contentEquals(other.rocketLaunches)) return false

        return true
    }

    override fun hashCode() = rocketLaunches.contentHashCode()
}