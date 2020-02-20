package com.liamdegrey.demoapp.network.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

@Parcelize
data class RocketLaunch(
    @JsonProperty("name") val name: String,
    @JsonProperty("img_url") private val imageUrl: String?,
    @JsonProperty("image_url") val fallbackImageUrl: String?,
    @JsonProperty("pad") val launchPad: LaunchPad,
    @JsonProperty("status") val launchStatus: LaunchStatus,
    @JsonProperty("window_start") val date: DateTime,
    @JsonProperty("mission") val mission: Mission?,
    @JsonProperty("rocket") val rocket: Rocket?
) : Parcelable {

    fun getPhotoUrl(): String? =
        imageUrl ?: fallbackImageUrl ?: rocket?.spacecraftStage?.spacecraft?.configuration?.imageUrl

    fun getLaunchCountryCode(): String? =
        launchPad.location.countryCode.takeIf {
            COUNTRY_CODES_OF_INTEREST.contains(it)
        }

    companion object {
        const val ARGUMENT_KEY = "RocketLaunch"
        private val COUNTRY_CODES_OF_INTEREST = listOf("RUS", "CHN", "UNK")
    }
}