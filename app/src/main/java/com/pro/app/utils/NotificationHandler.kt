package com.pro.app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.pro.app.R
import com.pro.app.ui.views.activities.LogsActivity
import java.util.*


class NotificationHandler {

    fun generateNotificationForLogs(ctx: Context) {

        val notifyIntent = Intent(ctx, LogsActivity::class.java)

        var notifyPendingIntent = PendingIntent.getActivity(ctx, 0,
                notifyIntent, PendingIntent.FLAG_ONE_SHOT)

        val nm = ctx.getSystemService(Context
                .NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("id", "name",
                    NotificationManager.IMPORTANCE_LOW)
            channel.setSound(null, null)
            channel.setShowBadge(false)
            channel.enableVibration(false)
            nm.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(ctx, "id")
        builder.setSound(null)
                .setVibrate(null)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Click Here for SmartBuy Logs")
                .setContentText("Click Here for SmartBuy Logs")
                .setColor(ContextCompat.getColor(ctx, R.color.colorPrimary))
                .setContentIntent(notifyPendingIntent)
                .setOngoing(true)
                .setWhen(0)

        builder.setStyle(NotificationCompat.BigTextStyle(builder)).setSmallIcon(R.drawable.ic_notification_icon)
        builder.setContentIntent(notifyPendingIntent)

        var notification_view = RemoteViews(ctx.packageName, R.layout.remoteview_notification_log)

        builder.setCustomContentView(notification_view)
        builder.setStyle(NotificationCompat.DecoratedCustomViewStyle())
        var notification = builder.build()
        notification.flags = NotificationCompat.FLAG_NO_CLEAR
        nm.notify(-1, notification)

    }

    private fun initChannels(context: Context) {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("default",
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "Channel description"
        notificationManager.createNotificationChannel(channel)
    }

    private fun generateRandom(): Int {
        val random = Random()
        return random.nextInt(9999 - 1000) + 1000
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initNotificationChannels(context: Context) {

        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        val channelIdOne = context.getString(R.string.notification_channel_id_default)
        val nameOne = context.getString(R.string.notification_channel_name_default)
        val descriptionOne = context.getString(R.string.notification_channel_description_default)
        val importanceOne = NotificationManager.IMPORTANCE_HIGH

        val channelOne = NotificationChannel(channelIdOne, nameOne, importanceOne)
        channelOne.description = descriptionOne
        channelOne.enableLights(true)
        channelOne.lightColor = ContextCompat.getColor(context, R.color.colorPrimary)
        channelOne.enableVibration(false)
        mNotificationManager!!.createNotificationChannel(channelOne)
    }
}

