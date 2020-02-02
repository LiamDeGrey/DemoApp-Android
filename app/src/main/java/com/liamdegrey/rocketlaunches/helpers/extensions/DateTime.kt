package com.liamdegrey.rocketlaunches.helpers.extensions

import org.joda.time.DateTime

fun DateTime.getSimpleDate() =
    toString("h:mma, d MMMM yyyy")

