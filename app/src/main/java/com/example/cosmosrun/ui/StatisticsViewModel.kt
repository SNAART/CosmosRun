package com.example.cosmosrun.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cosmosrun.R
import com.example.cosmosrun.database.RunDAO
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StatisticsViewModel : AppCompatActivity() {

    @Inject
    lateinit var runDAO: RunDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}