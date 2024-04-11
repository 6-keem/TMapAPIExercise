package com.cookandroid.mapsdkexercise.RouteFind

import com.google.gson.annotations.SerializedName


data class Legs (

  @SerializedName("mode"        ) var mode        : String?          = null,
  @SerializedName("sectionTime" ) var sectionTime : Int?             = null,
  @SerializedName("distance"    ) var distance    : Int?             = null,
  @SerializedName("start"       ) var start       : Start?           = Start(),
  @SerializedName("end"         ) var end         : End?             = End(),
  @SerializedName("steps"       ) var steps       : ArrayList<Steps> = arrayListOf(),
  @SerializedName("passStopList") var passStopList: PassStopList     = PassStopList()
)