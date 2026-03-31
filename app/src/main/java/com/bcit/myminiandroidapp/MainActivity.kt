package com.bcit.myminiandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import com.bcit.myminiandroidapp.data.DriverRepository
import com.bcit.myminiandroidapp.data.MyDatabase
import com.bcit.myminiandroidapp.appui.F1State
import com.bcit.myminiandroidapp.appui.MainContent

class MainActivity : ComponentActivity() {

    // Initialize Database and Repository lazily outside onCreate
    private val db by lazy { MyDatabase.getDatabase(applicationContext) }
    private val repository by lazy { DriverRepository(db.driverDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Inject repository into state holder
            val f1State = remember { F1State(repository) }

            MainContent(f1State)
        }
    }
}