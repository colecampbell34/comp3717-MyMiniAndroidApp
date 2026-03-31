package com.bcit.myminiandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import com.bcit.myminiandroidapp.data.AssetRepository
import com.bcit.myminiandroidapp.data.MyDatabase
import com.bcit.myminiandroidapp.appui.MainContent
import com.bcit.myminiandroidapp.appui.PortfolioState

class MainActivity : ComponentActivity() {

    private val db by lazy { MyDatabase.getDatabase(applicationContext) }
    private val assetRepo by lazy { AssetRepository(db.assetDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Injecting the repository into our state holder
            val portfolioState = remember { PortfolioState(assetRepo) }

            // Pass state holder to UI
            MainContent(portfolioState)
        }
    }
}