package com.cookandroid.mapsdkexercise.RouteFind

import com.google.gson.annotations.SerializedName


data class Itineraries (

  @SerializedName("fare"              ) var fare              : Fare?           = Fare(),
  @SerializedName("totalTime"         ) var totalTime         : Int?            = null,
  @SerializedName("legs"              ) var legs              : ArrayList<Legs> = arrayListOf(),
  @SerializedName("totalWalkTime"     ) var totalWalkTime     : Int?            = null,
  @SerializedName("transferCount"     ) var transferCount     : Int?            = null,
  @SerializedName("totalDistance"     ) var totalDistance     : Int?            = null,
  @SerializedName("pathType"          ) var pathType          : Int?            = null,
  @SerializedName("totalWalkDistance" ) var totalWalkDistance : Int?            = null

)