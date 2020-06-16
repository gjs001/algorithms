package com.samsung.zoo.tiger.tiltsensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

class TiltSensorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var tiltView: TiltView

    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiltsensor)

        tiltView = TiltView(this)
        setContentView(tiltView)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // 센서값이 변경되면 호출된데요~
        // values[0] : x축 값 위로 기울면 -10 ~ 0 / 아래로 기울면 0 ~ 10
        // 1 : y축, 2 : z축(미사용)
        event?.let {
            Log.d("TiltSensorActivity", "onSensorChanged : x : " + "${event.values[0]}, y : ${event.values[1]}, z : ${event.values[2]}")
            tiltView.onSensorEvent(event)
        }


    }

}