package com.example.frontend

import com.example.frontend.utilities.isValidSnuMail
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class ValidatorsTest {
    @Test
    fun `isValidSnuMail should return true when email ends with @snu_ac_kr`() {
        val email = "test@snu.ac.kr"

        val result = isValidSnuMail(email)

        assertTrue(result)
    }

    @Test
    fun `isValidSnuMail should return false when email does not end with @snu_ac_kr`() {
        val email = "test@gmail.com"

        val result = isValidSnuMail(email)

        assertFalse(result)
    }
}