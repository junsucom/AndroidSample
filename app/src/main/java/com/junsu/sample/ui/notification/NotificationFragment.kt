package com.junsu.sample.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.fragment.app.viewModels
import com.junsu.base.BaseFragment
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentNotificationBinding
import com.junsu.sample.ui.blank.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {

    private val viewModel by viewModels<NotificationViewModel>()

    override fun getLayoutResId() = R.layout.fragment_notification

    override fun initFragment() {
        activity?.title = getString(R.string.menu_notification)

        bind.createButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                    getString(R.string.notification_channel_id),
                    getString(R.string.notification_channel_name),
                    // TODO: Step 2.4 change importance
                    NotificationManager.IMPORTANCE_HIGH
                )

                notificationChannel.setShowBadge(false) //앱 아이콘에 벳지 표시
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.RED
                notificationChannel.enableVibration(true)
                notificationChannel.description = getString(R.string.notification_channel_description)

                val notificationManager = requireActivity().getSystemService(
                    NotificationManager::class.java
                )
                notificationManager.createNotificationChannel(notificationChannel)

            }
        }
    }

}
