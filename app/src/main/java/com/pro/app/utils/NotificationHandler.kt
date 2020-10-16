package com.pro.app.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.RemoteViews
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.pro.app.R
import com.pro.app.data.models.PushNotificationModel
import com.pro.app.ui.views.activities.LogsActivity
import java.util.*


class NotificationHandler {

    fun generateNotification(ctx: Context, pushNotificationModel: PushNotificationModel, notifyIntent: Intent) {
        var notifyPendingIntent: PendingIntent? = null

        notifyIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val uniqueId = (System.currentTimeMillis() and 0xfffffff).toInt()
        notifyPendingIntent = PendingIntent.getActivity(ctx, uniqueId, notifyIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initNotificationChannels(ctx)
        }

        val channelId = ctx.getString(R.string.notification_channel_id_default)

        val builder = NotificationCompat.Builder(ctx, channelId)
        builder.setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setWhen(System.currentTimeMillis())
            .setContentTitle(if (pushNotificationModel.title.isNotEmpty()) pushNotificationModel.title else "${pushNotificationModel.type.capitalize()}")
            .setTicker(pushNotificationModel.title)
            .setColor(ContextCompat.getColor(ctx, R.color.colorPrimary))
            .setContentText(pushNotificationModel.body)
            .setContentIntent(notifyPendingIntent)

        builder.setStyle(NotificationCompat.BigTextStyle(builder)).setSmallIcon(R.drawable.ic_notification_icon)

        builder.setAutoCancel(true)
        builder.setStyle(NotificationCompat.BigTextStyle().bigText(pushNotificationModel.body))
        builder.setContentIntent(notifyPendingIntent)

        val nm = ctx.getSystemService(Context
            .NOTIFICATION_SERVICE) as NotificationManager

        if (pushNotificationModel.big_image.isNotEmpty()) {

            Handler(Looper.getMainLooper()).post {

                Glide.with(ctx).asBitmap().load(pushNotificationModel.big_image)
                    .into(object : CustomTarget<Bitmap?>() {

                        override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap?>?
                        ) {
                            val bps = NotificationCompat.BigPictureStyle().bigPicture(resource).setBigContentTitle(pushNotificationModel.title)
                            bps.setSummaryText(pushNotificationModel.body)
                            builder.setStyle(bps)
                            builder.setLargeIcon(BitmapFactory.decodeResource(ctx.resources, R.drawable.ic_notification_icon))
                            var notification2 = builder.build()
                            nm.notify(generateRandom(), notification2)
                        }
                    })
            }
        } else {
            var notification = builder.build()
            nm.notify(generateRandom(), notification)
        }

    }

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

