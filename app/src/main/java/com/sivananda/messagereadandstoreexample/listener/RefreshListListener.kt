package com.sivananda.messagereadandstoreexample.listener

import com.sivananda.messagereadandstoreexample.model.BankMessageItem

interface RefreshListListener {
    fun onRefreshList(bankMsg : BankMessageItem)
}