package com.liamdegrey.demoapp.network.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rocket(
    @JsonProperty("spacecraft_stage") val spacecraftStage: SpacecraftStage?
) : Parcelable