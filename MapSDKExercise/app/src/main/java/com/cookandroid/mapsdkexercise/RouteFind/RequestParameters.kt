package com.cookandroid.mapsdkexercise.RouteFind

import com.google.gson.annotations.SerializedName


data class RequestParameters (

  @SerializedName("busCount"           ) var busCount           : Int?    = null,
  @SerializedName("expressbusCount"    ) var expressbusCount    : Int?    = null,
  @SerializedName("subwayCount"        ) var subwayCount        : Int?    = null,
  @SerializedName("airplaneCount"      ) var airplaneCount      : Int?    = null,
  @SerializedName("locale"             ) var locale             : String? = null,
  @SerializedName("endY"               ) var endY               : String? = null,
  @SerializedName("endX"               ) var endX               : String? = null,
  @SerializedName("wideareaRouteCount" ) var wideareaRouteCount : Int?    = null,
  @SerializedName("subwayBusCount"     ) var subwayBusCount     : Int?    = null,
  @SerializedName("startY"             ) var startY             : String? = null,
  @SerializedName("startX"             ) var startX             : String? = null,
  @SerializedName("ferryCount"         ) var ferryCount         : Int?    = null,
  @SerializedName("trainCount"         ) var trainCount         : Int?    = null,
  @SerializedName("reqDttm"            ) var reqDttm            : String? = null

)