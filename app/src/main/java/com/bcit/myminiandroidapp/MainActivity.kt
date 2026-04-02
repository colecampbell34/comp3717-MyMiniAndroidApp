package com.bcit.myminiandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import com.bcit.myminiandroidapp.data.DriverRepository
import com.bcit.myminiandroidapp.data.MyDatabase
import com.bcit.myminiandroidapp.data.remote.F1ApiRepository
import com.bcit.myminiandroidapp.appui.F1State
import com.bcit.myminiandroidapp.appui.MainContent

class MainActivity : ComponentActivity() {

    // Initialize Local Database and Repository
    private val db by lazy { MyDatabase.getDatabase(applicationContext) }
    private val localRepo by lazy { DriverRepository(db.driverDao()) }

    // Initialize Remote API Repository
    private val remoteRepo by lazy { F1ApiRepository() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Inject both repositories into the state holder
            val f1State = remember { F1State(localRepository = localRepo, remoteRepository = remoteRepo) }

            // The rest of the UI uses this single source of truth
            MainContent(f1State)
        }
    }
}