package com.example.cosmosrun.ui.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.cosmosrun.R
import kotlinx.android.synthetic.main.fragment_musicplayer.*
import timber.log.Timber

class MusicFragment:Fragment(R.layout.fragment_musicplayer), SensorEventListener {
    private var mp:MediaPlayer? = null
    private var currentSong = mutableListOf(R.raw.music)

    var sensor : Sensor? = null
    var sensorManager : SensorManager? = null
    var isClicked = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorManager = this.context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        controlSound(currentSong[0])
    }
    private fun controlSound(id:Int){
        fab_play.setOnClickListener{
            if (mp == null){
                mp = MediaPlayer.create(this.context,id)
                Timber.d("ID: ${mp!!.audioSessionId}")
                initSeekBar()
            }
            isClicked = true
            mp?.start()
        }

        fab_pause.setOnClickListener {
            if (mp !== null) {
                mp?.pause()
            }
        }
        fab_stop.setOnClickListener{
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
    private fun initSeekBar() {
        seekbar.max = mp!!.duration
        val handler = Handler()
        handler.postDelayed(object: Runnable{
            override fun run() {
                try {
                    seekbar.progress = mp!!.currentPosition
                    handler.postDelayed(this,1000)
                } catch (e: Exception) {
                }
            }
        }, 0)
    }
    override fun onSensorChanged(event: SensorEvent?) {
        try {
            if (isClicked == true) {
                if (event!!.values[0] < 30) {
                    if (mp !== null) {
                        mp?.pause()
                    }
                }
            }
        }
        catch (e : Exception) { }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }
}