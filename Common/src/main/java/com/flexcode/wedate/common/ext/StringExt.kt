package com.flexcode.wedate.common.ext

import android.util.Patterns
import com.flexcode.wedate.common.utils.Constants
import java.util.regex.Pattern

fun String.isFirstNameValid(): Boolean {
    return this.isNotBlank()
}

fun String.isPhoneNumberValid(): Boolean {
    return this.isNotBlank() && this.length == Constants.MIN_PHONE_LENGTH
}

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isNameValid(): Boolean{
    return this.isNotBlank()
}

fun String.isValidPassword(): Boolean {
    return this.isNotBlank() &&
            this.length >= Constants.MIN_PASS_LENGTH &&
            Pattern.compile(Constants.PASS_PATTERN).matcher(this).matches()
}

fun String.passwordMatches(repeated: String): Boolean {
    return this == repeated
}

fun String.isDateValid(): Boolean{
    return this.isNotBlank() && this.toInt() <= Constants.MAX_DATE && this.toInt() > 0
}

fun String.isMonthValid(): Boolean{
    return this.isNotBlank()  && this.toInt() <= Constants.MAX_MONTH && this.toInt() > 0
}

fun String.isYearValid(): Boolean{
    return this.isNotBlank()  && this.length == Constants.MIN_YEAR_LENGTH && this.toInt() > 0
}