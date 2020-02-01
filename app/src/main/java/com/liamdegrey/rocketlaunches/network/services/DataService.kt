package com.liamdegrey.rocketlaunches.network.services

import com.liamdegrey.rocketlaunches.network.models.UpcomingRocketLaunches
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {

    @GET("/api/3.3.0/launch/upcoming/")
    fun getUpcomingRocketLaunches(@Query("limit") maxItems: Int): Single<UpcomingRocketLaunches>

}