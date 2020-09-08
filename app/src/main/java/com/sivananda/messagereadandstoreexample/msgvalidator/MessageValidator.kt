package com.sivananda.messagereadandstoreexample.msgvalidator

import java.util.regex.Matcher
import java.util.regex.Pattern

object MessageValidator {

    fun isPhoneNumberFromBank(phoneNum: String) : Boolean {
        val regEx = Pattern.compile("[a-zA-Z0-9]{2}-[a-zA-Z0-9]{6}") // Ex : AM-HDFCBK
        val m: Matcher = regEx.matcher(phoneNum)
        return m.find()
    }

    fun isPhoneNumberFromOtherContact(phoneNum: String) : Boolean {
        return phoneNum.length >= 10
    }

    fun validateMessage(msgText: String?) : Boolean {
        return (msgText!!.contains("credited")|| msgText!!.contains("debited") || msgText!!.contains("withdrawn"))
    }

    fun checkForDate(msgText: String?) : Boolean {
        val regEx = Pattern.compile("\\d{2}-\\d{2}-\\d{2}") // Ex : 01-02-2020
        val m: Matcher = regEx.matcher(msgText)
        return m.find()
    }

    fun checkForAmount(msgText: String?) : Boolean {
        return (msgText!!.contains("Rs.") || msgText!!.contains("INR"))
    }

}