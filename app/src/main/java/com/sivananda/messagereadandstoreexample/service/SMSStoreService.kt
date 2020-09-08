package com.sivananda.messagereadandstoreexample.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.sivananda.messagereadandstoreexample.apimanager.RestApiManager
import com.sivananda.messagereadandstoreexample.listener.MessageListener
import com.sivananda.messagereadandstoreexample.model.BankMessageItem
import com.sivananda.messagereadandstoreexample.receivers.SMSReceiver


class SMSStoreService : Service(), MessageListener {

    lateinit var apiService : RestApiManager
    lateinit var message : String

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= 26) {
            val CHANNEL_ID = "my_channel_01"
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                channel
            )
            val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("")
                .setContentText("").build()
            startForeground(1, notification)
        }

        //RestApiManager.addUser()
        SMSReceiver.bindListener(this)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        //apiService.addUser()
        return START_STICKY
    }

    override fun messageReceived(msg: String?, msgText: String?, msgDate: String?) {
        message = msg!!
        Log.v("Receiver Service", "$msg - $msgText - $msgDate")
        RestApiManager.addUser(BankMessageItem(msgText!!, msgDate!!, msg!!))
    }

}
