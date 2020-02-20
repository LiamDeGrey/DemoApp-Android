package com.liamdegrey.demoapp.network.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpacecraftStage(
    @JsonProperty("spacecraft") val spacecraft: Spacecraft?
) : Parcelable