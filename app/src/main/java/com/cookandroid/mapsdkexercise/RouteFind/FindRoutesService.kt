package com.cookandroid.mapsdkexercise.RouteFind

import com.cookandroid.mapsdkexercise.RouteApiReqeust
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface FindRoutesService {
    @Headers("Accept: application/json", "content-type: application/json")
    @POST("transit/routes")
    fun findRoutes(@Header("appKey") appKey: String, @Body body: RouteApiReqeust): Call<Route>
}
