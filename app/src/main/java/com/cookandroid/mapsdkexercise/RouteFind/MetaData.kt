package com.cookandroid.mapsdkexercise.RouteFind

import com.google.gson.annotations.SerializedName


data class MetaData (

  @SerializedName("requestParameters" ) var requestParameters : RequestParameters? = RequestParameters(),
  @SerializedName("plan"              ) var plan              : Plan?              = Plan()

)