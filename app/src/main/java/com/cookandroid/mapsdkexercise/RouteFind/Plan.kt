package com.cookandroid.mapsdkexercise.RouteFind

import com.google.gson.annotations.SerializedName


data class Plan (

  @SerializedName("itineraries" ) var itineraries : ArrayList<Itineraries> = arrayListOf()

)