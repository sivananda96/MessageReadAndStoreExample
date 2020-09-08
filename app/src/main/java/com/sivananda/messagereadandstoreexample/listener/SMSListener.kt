package com.sivananda.messagereadandstoreexample.listener

interface MessageListener {
    fun messageReceived(msg: String?, msgText: String?, msgDate: String?)
}