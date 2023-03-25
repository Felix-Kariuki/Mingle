/*
 * Copyright 2023 Felix Kariuki.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flexcode.wedate.common

import com.flexcode.wedate.common.ext.isDateValid
import com.flexcode.wedate.common.ext.isFirstNameValid
import com.flexcode.wedate.common.ext.isMonthValid
import com.flexcode.wedate.common.ext.isPhoneNumberValid
import com.flexcode.wedate.common.ext.isYearValid
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StringExtTest {

    @Test
    fun `test invalid name or string returns false`() {
        val firstName = "".isFirstNameValid()
        assertThat(firstName).isFalse()
    }

    @Test
    fun `test valid name or string returns true`() {
        val firstName = "Felix".isFirstNameValid()
        assertThat(firstName).isTrue()
    }

    // have removed the 10 digit check so wont work
//    @Test
//    fun `test invalid phone number returns false`(){
//        val phone = "07956038711".isPhoneNumberValid()
//        assertThat(phone).isFalse()
//    }

    @Test
    fun `test valid phone number returns true`() {
        val phone = "0795603871".isPhoneNumberValid()
        assertThat(phone).isTrue()
    }

    @Test
    fun `test date is valid`() {
        val date = "12".isDateValid()
        assertThat(date).isTrue()
    }

    @Test
    fun `test invalid date returns false`() {
        val date = "32".isDateValid()
        assertThat(date).isFalse()
    }

    @Test
    fun `test month is valid`() {
        val date = "12".isMonthValid()
        assertThat(date).isTrue()
    }

    @Test
    fun `test invalid month returns false`() {
        val date = "13".isMonthValid()
        assertThat(date).isFalse()
    }

    @Test
    fun `test year is valid`() {
        val date = "2004".isYearValid()
        assertThat(date).isTrue()
    }

    @Test
    fun `test invalid year returns false`() {
        val date = "20004".isYearValid()
        assertThat(date).isFalse()
    }
}
