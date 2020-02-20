package com.liamdegrey.demoapp.network.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    @JsonProperty("name") val label: String,
    @JsonProperty("country_code") val countryCode: String
) : Parcelable