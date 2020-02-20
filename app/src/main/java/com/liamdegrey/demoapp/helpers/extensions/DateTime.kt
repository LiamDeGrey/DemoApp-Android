package com.liamdegrey.demoapp.helpers.extensions

import org.joda.time.DateTime

fun DateTime.getSimpleDate(): String =
    toString("h:mma, d MMMM yyyy")

