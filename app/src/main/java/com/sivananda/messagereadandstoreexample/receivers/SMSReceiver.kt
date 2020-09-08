package com.sivananda.messagereadandstoreexample.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.util.Log
import com.sivananda.messagereadandstoreexample.listener.MessageListener
import com.sivananda.messagereadandstoreexample.msgvalidator.MessageValidator
import java.text.SimpleDateFormat
import java.util.*

class SMSReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val extras = intent?.extras

        if (extras != null) {
            val sms = extras.get("pdus") as Array<Any>

            for (i in sms.indices) {
                val format = extras.getString("format")

                var smsMsg = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    SmsMessage.createFromPdu(sms[i] as ByteArray, format)
                } else {
                    SmsMessage.createFromPdu(sms[i] as ByteArray)
                }

                val phoneNum = smsMsg.originatingAddress.toString()
                val msgText : String? = smsMsg.messageBody.toString()
                val msgDate = smsMsg.timestampMillis

                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                val dateVal = formatter.format(Date(msgDate.toLong()))

                Log.v("Receiver", "Num : $phoneNum - Msg : $msgText - Date : $dateVal")

                val isMsgFromBank = MessageValidator.isPhoneNumberFromBank(phoneNum)
                val isMsgFromContact = MessageValidator.isPhoneNumberFromOtherContact(phoneNum)

                if (isMsgFromBank || isMsgFromContact) {
                    if(MessageValidator.validateMessage(msgText) && MessageValidator.checkForDate(msgText) && MessageValidator.checkForAmount(msgText)) {
                        mListener?.messageReceived(phoneNum, msgText, dateVal)
                    }
                }
            }
        }
    }

    companion object {
        var mListener: MessageListener? = null
        fun bindListener(listener: MessageListener) {
            mListener = listener
        }
    }
}