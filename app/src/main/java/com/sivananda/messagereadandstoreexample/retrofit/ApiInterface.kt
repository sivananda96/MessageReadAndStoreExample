package com.sivananda.assignment.retrofit

import com.sivananda.messagereadandstoreexample.model.BankMessage
import com.sivananda.messagereadandstoreexample.model.BankMessageItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @GET("sms")
    fun getMessagesList(): Call<BankMessage>

    @Headers("Content-Type: application/json")
    @POST("sms")
    fun addMessage(@Body userData: BankMessageItem): Call<BankMessageItem>
}