package com.flexcode.wedate.common.ext

import android.util.Patterns
import com.flexcode.wedate.common.utils.Constants
import java.util.regex.Pattern

fun String.isFirstNameValid(): Boolean {
    return this.isNotBlank()
}

fun String.isPhoneNumberValid(): Boolean {
    return this.isNotBlank()
}

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotBlank() &&
            this.length >= Constants.MIN_PASS_LENGTH &&
            Pattern.compile(Constants.PASS_PATTERN).matcher(this).matches()
}

fun String.passwordMatches(repeated: String): Boolean {
    return this == repeated
}

fun String.idFromParameter(): String {
    return this.substring(1, this.length - 1)
}