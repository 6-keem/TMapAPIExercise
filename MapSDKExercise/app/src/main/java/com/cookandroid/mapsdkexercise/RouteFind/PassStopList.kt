package com.cookandroid.mapsdkexercise.RouteFind

import com.google.gson.annotations.SerializedName


data class PassStopList (

  @SerializedName("stationList" ) var stationList : ArrayList<StationList> = arrayListOf()

)