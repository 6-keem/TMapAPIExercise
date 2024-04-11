package com.cookandroid.mapsdkexercise.RouteFind

import com.google.gson.annotations.SerializedName


data class Currency (

  @SerializedName("symbol"       ) var symbol       : String? = null,
  @SerializedName("currency"     ) var currency     : String? = null,
  @SerializedName("currencyCode" ) var currencyCode : String? = null

)