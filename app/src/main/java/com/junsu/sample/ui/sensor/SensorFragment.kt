package com.junsu.sample.ui.sensor

import android.Manifest
import android.hardware.Sensor
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.junsu.base.extension.sensorChangesFlow
import com.junsu.sample.ui.AppBaseFragment
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentBlankBinding
import com.junsu.sample.databinding.FragmentSensorBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SensorFragment : AppBaseFragment<FragmentSensorBinding>() {

    private val viewModel by viewModels<SensorViewModel>()

    override fun getLayoutResId() = R.layout.fragment_sensor

    override fun initFragment() {
        activity?.title = getString(R.string.menu_sensor)

        bind.stepCountButton.setOnClickListener {
            lifecycleScope.launch {
                requireContext().sensorChangesFlow(Sensor.TYPE_STEP_COUNTER).catch { e ->
                    bind.stepCountText.text = e.message
                }.collectLatest {
                    bind.stepCountText.text = it.values.joinToString(", ")
                }
            }
        }

        bind.proximityButton.setOnClickListener {
            lifecycleScope.launch {
                requireContext().sensorChangesFlow(Sensor.TYPE_PROXIMITY).catch { e ->
                    bind.proximityText.text = e.message
                }.collectLatest {
                    bind.proximityText.text = it.values.joinToString(", ")
                }
            }
        }

        bind.accelerometerButton.setOnClickListener {
            lifecycleScope.launch {
                requireContext().sensorChangesFlow(Sensor.TYPE_ACCELEROMETER).catch { e ->
                    bind.accelerometerText.text = e.message
                }.collectLatest {
                    bind.accelerometerText.text = it.values.joinToString(", ")
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestPermission(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION))
        }
    }

}
