package com.cookandroid.mapsdkexercise

import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import com.cookandroid.mapsdkexercise.Geocoding.Geocoding
import com.cookandroid.mapsdkexercise.Geocoding.GeocodingService
import com.cookandroid.mapsdkexercise.RouteFind.FindRoutesService
import com.cookandroid.mapsdkexercise.RouteFind.Legs
import com.cookandroid.mapsdkexercise.RouteFind.MetaData
import com.cookandroid.mapsdkexercise.RouteFind.Route
import com.google.gson.annotations.SerializedName
import com.skt.tmap.TMapPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class RouteApiReqeust(
    @SerializedName("startX") val startX : String,
    @SerializedName("startY") val startY : String,
    @SerializedName("endX") val endX : String,
    @SerializedName("endY") val endY : String,
    @SerializedName("lang") val lang : Int,
    @SerializedName("format") val format : String,
    @SerializedName("count") val count : Int
)

class APIRequestHandler(private var routeList: ArrayList<TPointList>, private val mainActivity: MainActivity) {

    private val API_KEY = BuildConfig.api_key
    private var departPoint : TMapPoint = TMapPoint(0.0,0.0)
    private var destinationPoint : TMapPoint = TMapPoint(0.0,0.0)
    private var departFlag = false
    private var destinationFlag = false

    private val retrofit = Retrofit.Builder()
        .baseUrl(mainActivity.getString(R.string.BASE_URL))
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun requestAPIs(addr1 : String, addr2 : String){
        this.requestFullTextGeocodeingAPI(addr1, mainActivity.getString(R.string.DEPART))
        this.requestFullTextGeocodeingAPI(addr2, mainActivity.getString(R.string.DESTINATION))

        CoroutineScope(Dispatchers.IO).launch {
            while(true){
                if(departFlag && destinationFlag){
                    Log.d(R.string.TAG.toString(),"depart = {${departPoint.latitude}, ${departPoint.longitude}, dest = {${destinationPoint.latitude}, ${destinationPoint.longitude}}")
                    requestTransmitRouteAPI()
                    departFlag = false
                    destinationFlag = false
                    break
                }
            }
        }

    }
    private fun requestFullTextGeocodeingAPI(addr : String, value : String){
        val encodedAddress1 = Uri.encode(addr)

        val service = retrofit.create(GeocodingService::class.java)
        service.geocoding(API_KEY,"1", encodedAddress1).enqueue(object : Callback<Geocoding>{
            override fun onResponse(call: Call<Geocoding>, response: Response<Geocoding>) {
                if(response.isSuccessful){
                    try{
                        val coordinate = response.body()?.coordinateInfo?.coordinate?.get(0)
                        val lat : String = if (coordinate?.lat != null) coordinate.lat!!
                            else throw IllegalArgumentException("null")

                        val lon : String = if (coordinate?.lon != null) coordinate.lon!!
                            else throw IllegalArgumentException("null")

                        if (value == mainActivity.getString(R.string.DEPART)) {
                            departPoint.latitude = lat.toDouble()
                            departPoint.longitude = lon.toDouble()
                            departFlag = true
                        } else if (value == mainActivity.getString(R.string.DESTINATION)) {
                            destinationPoint.latitude = lat.toDouble()
                            destinationPoint.longitude = lon.toDouble()
                            destinationFlag = true
                        }
                    } catch(_:NullPointerException){
                        Log.d(mainActivity.getString(R.string.TAG),"RESPONSE'S PROPERTY IS NULL")
                    }
                } else {
                    Log.d(mainActivity.getString(R.string.TAG),"RESPONSE FAILED")
                }
            }
            override fun onFailure(call: Call<Geocoding>, t: Throwable) {
                Log.d(mainActivity.getString(R.string.TAG), "FAILED : " + t.message)
            }
        })
    }
    private fun requestTransmitRouteAPI() {
        val apiRequest = RouteApiReqeust(
            startX = "${departPoint.longitude}",
            startY = "${departPoint.latitude}",
            endX = "${destinationPoint.longitude}",
            endY = "${destinationPoint.latitude}",
            lang = 0,
            format = "json",
            count = 10
        )

        val service = retrofit.create(FindRoutesService::class.java)
        service.findRoutes(API_KEY,apiRequest).enqueue(object : Callback<Route>{
            override fun onResponse(call: Call<Route>, response: Response<Route>) {
                while(routeList.size != 0)
                    routeList.removeLast()

                if(response.isSuccessful){
                    try{
                        Log.d(mainActivity.getString(R.string.TAG),response.body().toString())
                        parsingRouteResponse(response)
                        mainActivity.drawOnMap()
                    } catch(_:NullPointerException){
                        Log.d(mainActivity.getString(R.string.TAG),"RESPONSE'S PROPERTY IS NULL")
                    }
                } else {
                    Log.d(mainActivity.getString(R.string.TAG),"RESPONSE FAILED")
                }
            }
            override fun onFailure(call: Call<Route>, t: Throwable) {
                Log.d(mainActivity.getString(R.string.TAG), "FAILED : " + t.message)
            }
        })
    }

    private fun parsingRouteResponse(response: Response<Route>) {
        val result = response.body()
        val metaData: MetaData? = result?.metaData
        val plans = metaData?.plan
        val itineraries = plans?.itineraries
        val legsList = mutableListOf<Legs>()
        itineraries?.get(0)?.legs?.forEach { it ->
            legsList.add(it)
        }

        legsList.forEach() { legIterator ->
            val tPointList = arrayListOf<TMapPoint>()

            val mode : String = if (legIterator.mode != null) legIterator.mode!!
                    else throw IllegalArgumentException("null")

            when (legIterator.mode) {
                "WALK" -> {
                    legIterator.steps.forEachIndexed { stepIndex, stepIterator ->
                        val lineString = stepIterator.linestring
                        val lineStringTokens = lineString?.split(" ")
                        lineStringTokens?.forEach { tIt ->
                            val start = tIt.split(",")
                            tPointList.add(
                                TMapPoint(
                                    start[1].toDouble(),
                                    start[0].toDouble()
                                )
                            )
                        }
                    }
                }

                "SUBWAY", "BUS" -> {
                    val stationList = legIterator.passStopList.stationList
                    stationList.forEach() { stationIterator ->

                        val lat = if (stationIterator.lat != null) stationIterator.lat
                                else throw IllegalArgumentException("null")

                        val lon = if (stationIterator.lon != null) stationIterator.lon
                                else throw IllegalArgumentException("null")

                        if (lat != null && lon != null)
                            tPointList.add(TMapPoint(lat.toDouble(), lon.toDouble()))
                    }
                }

                else -> {
                    // TODO: 다른 교통수단
                }
            }
            routeList.add(TPointList(tPointList, mode))
        }
    }
}