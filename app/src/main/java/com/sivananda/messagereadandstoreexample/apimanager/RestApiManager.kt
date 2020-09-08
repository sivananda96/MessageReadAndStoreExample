package com.sivananda.messagereadandstoreexample.apimanager

import android.app.Activity
import android.util.Log
import com.sivananda.assignment.retrofit.ApiClient
import com.sivananda.messagereadandstoreexample.adapter.MessagesAdapter
import com.sivananda.messagereadandstoreexample.listener.RefreshListListener
import com.sivananda.messagereadandstoreexample.listener.ResponseListener
import com.sivananda.messagereadandstoreexample.model.BankMessage
import com.sivananda.messagereadandstoreexample.model.BankMessageItem
import com.sivananda.messagereadandstoreexample.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

object RestApiManager {

    lateinit var activity: ResponseListener
    lateinit var refreshListInActivity: RefreshListListener

    fun setListener(mainActivity: ResponseListener, refreshListListener: RefreshListListener) {
        activity = mainActivity
        refreshListInActivity = refreshListListener
    }

    fun addUser(msg: BankMessageItem) { //}, onResult: (BankMessageItem?) -> Unit) {
        val retrofit = ApiClient.getClient
        retrofit.addMessage(msg).enqueue(
            object : Callback<BankMessageItem> {
                override fun onFailure(call: Call<BankMessageItem>, t: Throwable) {
                    Log.v("Post Response Failure", t.message.toString())
                    //onResult(null)
                }

                override fun onResponse(
                    call: Call<BankMessageItem>,
                    response: Response<BankMessageItem>
                ) {
                    val addedUser = response.body()
                    //onResult(addedUser)
                    Log.v("Post Response Success", addedUser.toString())
                    if(MainActivity.activityStatus == true) {
                       /* val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                        msg.date = formatter.format(Date(msg.date.toLong()))*/
                        refreshListInActivity.onRefreshList(msg)
                    }
                }
            }
        )
    }

    fun getUserList() {
        val retrofit = ApiClient.getClient
        retrofit.getMessagesList().enqueue(object : Callback<BankMessage> {

            override fun onResponse(
                call: Call<BankMessage>?,
                response: Response<BankMessage>?
            ) {
                Log.v("SuccessResponse", response!!.body()!!.toString())
                val list = response!!.body()
                activity.responseReceived(list)
            }

            override fun onFailure(call: Call<BankMessage>?, t: Throwable?) {
                Log.v("FailureResponse", t?.message)
            }

        })
    }
}