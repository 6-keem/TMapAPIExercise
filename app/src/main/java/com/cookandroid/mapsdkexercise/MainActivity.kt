package com.cookandroid.mapsdkexercise

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.skt.tmap.TMapData
import com.skt.tmap.TMapPoint
import com.skt.tmap.TMapView
import com.skt.tmap.overlay.TMapMarkerItem
import com.skt.tmap.overlay.TMapPolyLine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class TPointList(var pointList : ArrayList<TMapPoint>, val mode : String)

class MainActivity : AppCompatActivity() {

    private lateinit var tMapView: TMapView
    private val tMapData = TMapData()

    private var isAuthenticated = false;

    private val markers = mutableListOf<TMapMarkerItem>()
    private val routeList : ArrayList<TPointList> = arrayListOf()
    private lateinit var apiRequestHandler : APIRequestHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setInitalizationWithAuthentication()
        tMapView.setOnMapReadyListener {
            //todo 맵 로딩 완료 후 구현
            tMapView.setCenterPoint(37.54736767740843,127.00606742285552)
            apiRequestHandler = APIRequestHandler(routeList, this)
            setUIListener()
        }
    }

    private fun setInitalizationWithAuthentication(){
        tMapView = TMapView(this)
        val container = findViewById<FrameLayout>(R.id.tmapViewContainer)

        container.addView(tMapView)
        tMapView.setSKTMapApiKey(BuildConfig.api_key)

        CoroutineScope(Dispatchers.Main).launch {
            waitForAuthentication()
        }
    }

    private suspend fun waitForAuthentication(){
        while(!isAuthenticated){
            delay(1000)
            isAuthenticated = !isAuthenticated
        }
    }
    
    @SuppressLint("ClickableViewAccessibility")
    private fun setUIListener(){
        findViewById<Button>(R.id.zoomIN).setOnTouchListener(object : OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                tMapView.mapZoomIn()
                return false
            }
        })
        findViewById<Button>(R.id.zoomOUT).setOnTouchListener(object : OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                tMapView.mapZoomOut()
                return false;
            }
        })
        findViewById<Button>(R.id.search).setOnTouchListener(object :OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                val departAddress : String = findViewById<EditText>(R.id.depart).text.toString()
                val destinationAddress : String = findViewById<EditText>(R.id.destination).text.toString()
                apiRequestHandler.requestAPIs(departAddress,destinationAddress)
                return false;
            }
        })
    }

    fun drawOnMap() {
        tMapView.removeAllTMapPolyLine()
        routeList.forEachIndexed { index, it ->
            val tMapPolyLine = TMapPolyLine("route${index}", it.pointList)

            tMapPolyLine.lineWidth = 3.0f
            tMapPolyLine.lineColor = when (it.mode) {
                "WALK" -> Color.GRAY
                "SUBWAY" -> Color.GREEN
                "BUS" -> Color.BLUE
                else -> Color.RED
            }

            tMapView.addTMapPolyLine(tMapPolyLine)
        }
    }

    //TODO: MARKER
    private fun setMarker(lat : Double, lon : Double){
        val marker = TMapMarkerItem()
        marker.id = "marker" + (markers.size + 1).toString()
        marker.setTMapPoint(lat, lon)
        marker.setIcon(BitmapFactory.decodeResource(getResources(), com.skt.tmap.R.drawable.point_dot));
        markers.add(marker)
    }
}