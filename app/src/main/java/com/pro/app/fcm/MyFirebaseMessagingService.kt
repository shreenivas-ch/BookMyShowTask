package com.pro.app.fcm

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pro.app.R
import com.pro.app.data.models.PushNotificationModel
import com.pro.app.extensions.showLog
import com.pro.app.ui.views.activities.MainActivity
import com.pro.app.utils.NotificationHandler

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private val TAG = MyFirebaseMessagingService::class.java.simpleName
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        "Refreshed token: $token".showLog()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (!remoteMessage.data.isNullOrEmpty()) {
            val pushNotificationModel = PushNotificationModel()
            pushNotificationModel.title =
                remoteMessage.data["title"] ?: getString(R.string.app_name)
            pushNotificationModel.body = remoteMessage.data["body"] ?: ""
            pushNotificationModel.type = remoteMessage.data["type"] ?: ""
            pushNotificationModel.element_id = remoteMessage.data["element_id"] ?: ""

            if (remoteMessage.data.containsKey("big_image")) {
                pushNotificationModel.big_image = remoteMessage.data["big_image"] ?: ""
            }

            val resultIntent = Intent(applicationContext, MainActivity::class.java)
            resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            NotificationHandler().generateNotification(this, pushNotificationModel, resultIntent)
        }
    }
}