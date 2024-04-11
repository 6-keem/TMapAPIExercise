package com.cookandroid.mapsdkexercise.RouteFind

import com.google.gson.annotations.SerializedName


data class Regular (

  @SerializedName("totalFare" ) var totalFare : Int?      = null,
  @SerializedName("currency"  ) var currency  : Currency? = Currency()

)