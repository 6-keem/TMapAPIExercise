package com.cookandroid.mapsdkexercise.RouteFind

import com.google.gson.annotations.SerializedName


data class StationList (

  @SerializedName("index"       ) var index       : Int?    = null,
  @SerializedName("stationName" ) var stationName : String? = null,
  @SerializedName("lon"         ) var lon         : String? = null,
  @SerializedName("lat"         ) var lat         : String? = null,
  @SerializedName("stationID"   ) var stationID   : String? = null

)