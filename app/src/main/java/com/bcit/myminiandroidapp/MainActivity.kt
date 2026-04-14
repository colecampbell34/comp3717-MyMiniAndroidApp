package com.bcit.myminiandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bcit.myminiandroidapp.data.DriverRepository
import com.bcit.myminiandroidapp.data.MyDatabase
import com.bcit.myminiandroidapp.data.remote.F1ApiRepository
import com.bcit.myminiandroidapp.appui.F1State
import com.bcit.myminiandroidapp.appui.MainContent

/**
Cole Campbell A01412289.
*/
class MainActivity : ComponentActivity() {
    private val db by lazy { MyDatabase.getDatabase(applicationContext) }
    private val localRepo by lazy { DriverRepository(db.driverDao()) }
    private val remoteRepo by lazy { F1ApiRepository() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel {
                F1State(localRepository = localRepo, remoteRepository = remoteRepo)
            }
            MainContent()
        }
    }
}
