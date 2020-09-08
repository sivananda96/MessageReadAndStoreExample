package com.sivananda.messagereadandstoreexample.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sivananda.assignment.retrofit.ApiClient
import com.sivananda.messagereadandstoreexample.R
import com.sivananda.messagereadandstoreexample.adapter.MessagesAdapter
import com.sivananda.messagereadandstoreexample.apimanager.RestApiManager
import com.sivananda.messagereadandstoreexample.listener.RefreshListListener
import com.sivananda.messagereadandstoreexample.listener.ResponseListener
import com.sivananda.messagereadandstoreexample.model.BankMessage
import com.sivananda.messagereadandstoreexample.model.BankMessageItem
import com.sivananda.messagereadandstoreexample.service.SMSStoreService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), ResponseListener, RefreshListListener {

    private val PERMISSIONS_RECEIVE_SMS = 123
    private val TAG = "MainActivity"
    lateinit var recyclerView: RecyclerView
    lateinit var emptyView : TextView
    var msgAdapter: MessagesAdapter? = null
    var list: List<BankMessageItem>? = mutableListOf<BankMessageItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvMsgs)
        emptyView = findViewById(R.id.empty_view)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MessagesAdapter(list as MutableList<BankMessageItem>?)
        }

        checkPermissions()

        RestApiManager.setListener(this, this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, SMSStoreService::class.java))
        } else {
            startService(Intent(this, SMSStoreService::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        RestApiManager.getUserList()
        activityStatus = true
    }

    override fun onPause() {
        super.onPause()
        activityStatus = false
    }

    private fun checkPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.RECEIVE_SMS
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.RECEIVE_SMS),
                    PERMISSIONS_RECEIVE_SMS
                )
            }
        } else {
            // Permission has already been granted
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_RECEIVE_SMS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted
                    Log.d(TAG, "PERMISSIONS_REQUEST_READ_SMS permission granted")
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(TAG, "PERMISSIONS_REQUEST_READ_SMS permission denied")
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    companion object {
        var activityStatus = false
    }

    override fun responseReceived(responseList: MutableList<BankMessageItem>?) {
        list = responseList
        Log.v("ListResponse", list?.toString())

        if(list.isNullOrEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE)
            emptyView.setVisibility(View.GONE)
            msgAdapter = MessagesAdapter(list as MutableList<BankMessageItem>?)
            recyclerView.adapter = msgAdapter
        }
    }

    override fun onRefreshList(bankMsg: BankMessageItem) {
        recyclerView.setVisibility(View.VISIBLE)
        emptyView.setVisibility(View.GONE)
        msgAdapter?.addItem(bankMsg)
        msgAdapter?.notifyDataSetChanged()
    }
}