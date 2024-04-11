package com.cookandroid.mapsdkexercise.RouteFind

import com.google.gson.annotations.SerializedName


data class Steps (

  @SerializedName("streetName"  ) var streetName  : String? = null,
  @SerializedName("distance"    ) var distance    : Int?    = null,
  @SerializedName("description" ) var description : String? = null,
  @SerializedName("linestring"  ) var linestring  : String? = null

)