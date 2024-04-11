package com.cookandroid.mapsdkexercise.RouteFind

import com.google.gson.annotations.SerializedName


data class Fare (

  @SerializedName("regular" ) var regular : Regular? = Regular()

)