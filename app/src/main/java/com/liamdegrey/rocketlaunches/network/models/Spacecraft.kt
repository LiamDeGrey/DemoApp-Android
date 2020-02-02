package com.liamdegrey.rocketlaunches.network.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Spacecraft(
    @JsonProperty("configuration") val configuration: SpacecraftConfiguration?
) : Parcelable