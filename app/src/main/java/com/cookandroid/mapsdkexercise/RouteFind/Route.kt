package com.cookandroid.mapsdkexercise.RouteFind

import com.google.gson.annotations.SerializedName


data class Route (

  @SerializedName("metaData" ) var metaData : MetaData? = MetaData()

)