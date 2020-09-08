package com.sivananda.messagereadandstoreexample.msgvalidator

import org.junit.Test

import org.junit.Assert.*

class MessageValidatorTest {

    @Test
    fun isPhoneNumberFromBank() {
        assertTrue(MessageValidator.isPhoneNumberFromBank("VM-HDFCBK"))
    }

    @Test
    fun isPhoneNumberFromOtherContact() {
        assertFalse(MessageValidator.isPhoneNumberFromBank("9912345678"))
    }

    @Test
    fun validateMessageForDebitAsTrue() {
        assertTrue(MessageValidator.validateMessage("You have debited"))
    }

    @Test
    fun validateMessageForCreditAsTrue() {
        assertTrue(MessageValidator.validateMessage("You have credited"))
    }

    @Test
    fun validateMessageForWithdrawAsTrue() {
        assertTrue(MessageValidator.validateMessage("You have withdrawn"))
    }

    @Test
    fun checkForDateTrue() {
        assertTrue(MessageValidator.checkForDate("You have debited on 12-06-2020"))
    }

    @Test
    fun checkForDateFalse() {
        assertFalse(MessageValidator.checkForDate("You have debited on 12-06-20"))
    }

    @Test
    fun checkForAmountTrue() {
        assertTrue(MessageValidator.checkForAmount("You have withdrawn Rs. 10000"))
    }

    @Test
    fun checkForAmountFalse() {
        assertFalse(MessageValidator.checkForAmount("You have withdrawn Rs10000"))
    }

    @Test
    fun validateMessageAsFalse() {
        assertFalse(MessageValidator.validateMessage("This is normal Message"))
    }
}