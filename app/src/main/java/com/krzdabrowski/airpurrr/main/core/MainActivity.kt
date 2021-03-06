package com.krzdabrowski.airpurrr.main.core

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.krzdabrowski.airpurrr.R
import com.krzdabrowski.airpurrr.main.detector.DetectorViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val detectorViewModel: DetectorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportFragmentManager.commit { add(R.id.activity_main, MainFragment()) }
        onBackPressedDispatcher.addCallback { moveTaskToBack(true) }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
