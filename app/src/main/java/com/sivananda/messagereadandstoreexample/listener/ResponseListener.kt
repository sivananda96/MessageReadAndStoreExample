package com.sivananda.messagereadandstoreexample.listener

import com.sivananda.messagereadandstoreexample.model.BankMessageItem

interface ResponseListener {
    fun responseReceived(list : MutableList<BankMessageItem>?)
}