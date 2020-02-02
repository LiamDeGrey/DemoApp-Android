package com.liamdegrey.rocketlaunches.network.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpcomingRocketLaunches(
    @JsonProperty("results") val rocketLaunches: List<RocketLaunch>
) : Parcelable