package com.liamdegrey.rocketlaunches.network.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

@Parcelize
data class RocketLaunch(
    @JsonProperty("name") val name: String,
    @JsonProperty("img_url") val imageUrl: String?,
    @JsonProperty("pad") val launchPad: LaunchPad,
    @JsonProperty("status") val launchStatus: LaunchStatus,
    @JsonProperty("window_start") val date: DateTime,
    @JsonProperty("mission") val mission: Mission?
) : Parcelable