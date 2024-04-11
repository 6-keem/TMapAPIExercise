package com.cookandroid.mapsdkexercise.RouteFind

import com.google.gson.annotations.SerializedName


data class End (

  @SerializedName("name" ) var name : String? = null,
  @SerializedName("lon"  ) var lon  : Double? = null,
  @SerializedName("lat"  ) var lat  : Double? = null

)