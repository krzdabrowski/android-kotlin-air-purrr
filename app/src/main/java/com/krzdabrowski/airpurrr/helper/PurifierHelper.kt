package com.krzdabrowski.airpurrr.helper

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.krzdabrowski.airpurrr.R
import com.krzdabrowski.airpurrr.model.DetectorModel
import com.krzdabrowski.airpurrr.viewmodel.DetectorViewModel
import com.google.android.material.snackbar.Snackbar

class PurifierHelper(private val detectorViewModel: DetectorViewModel) {

    fun getPurifierState(value: DetectorModel, login: String, password: String, previousState: Boolean, rootView: SwipeRefreshLayout): Boolean {
        return when (value.workstate) {
            "WorkStates.Sleeping" -> {
                Snackbar.make(rootView, R.string.main_message_turn_on, Snackbar.LENGTH_LONG).show()
                detectorViewModel.controlFan(!previousState, login, password)
                !previousState
            }
            "WorkStates.Measuring" -> {
                Snackbar.make(rootView, R.string.main_message_error_measuring, Snackbar.LENGTH_LONG).show()
                previousState
            }
            else -> {
                Snackbar.make(rootView, R.string.main_message_error, Snackbar.LENGTH_LONG).show()
                previousState
            }
        }
    }
}