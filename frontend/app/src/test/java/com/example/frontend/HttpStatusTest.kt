package com.example.frontend

import com.example.frontend.utilities.HttpStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class HttpStatusTest {
    @Test
    fun `fromCode returns OK for 200`() {
        val status = HttpStatus.fromCode(200)
        assertEquals(HttpStatus.OK, status)
    }

    @Test
    fun `fromCode returns CREATED for 201`() {
        val status = HttpStatus.fromCode(201)
        assertEquals(HttpStatus.CREATED, status)
    }

    @Test
    fun `fromCode throws exception for invalid code`() {
        assertThrows(Exception::class.java) {
            HttpStatus.fromCode(999)
        }
    }

    @Test
    fun `fromCode throws exception for negative code`() {
        assertThrows(Exception::class.java) {
            HttpStatus.fromCode(-1)
        }
    }
}
