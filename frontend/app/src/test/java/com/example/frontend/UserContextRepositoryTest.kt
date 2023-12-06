package com.example.frontend

import com.example.frontend.repository.UserContextRepository
import com.example.frontend.utilities.InMemoryKVStore
import com.example.frontend.utilities.KVStore
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class UserContextRepositoryTest {
    private lateinit var userContextRepository: UserContextRepository
    private lateinit var inMemoryKVStore: InMemoryKVStore

    @Before
    fun setUp() {
        inMemoryKVStore = InMemoryKVStore()
        userContextRepository = UserContextRepository(inMemoryKVStore)
    }

    @Test
    fun `getUserName returns default when no username is set`() {
        val username = userContextRepository.getUserName()
        assertEquals(UserContextRepository.DEFAULT_USERNAME, username)
    }

    @Test
    fun `getUserName returns saved username`() {
        val expectedUsername = "TestUser"
        userContextRepository.saveUserName(expectedUsername)
        val username = userContextRepository.getUserName()
        assertEquals(expectedUsername, username)
    }

    @Test
    fun `getUserMail returns default when no user mail is set`() {
        val userMail = userContextRepository.getUserMail()
        assertEquals(UserContextRepository.DEFAULT_USER_MAIL, userMail)
    }

    @Test
    fun `getAuthToken returns empty string when no auth token is set`() {
        val authToken = userContextRepository.getAuthToken()
        assertEquals("", authToken)
    }

    @Test
    fun `getSelectedPredefinedImage returns null when no image is set`() {
        val imageId = userContextRepository.getSelectedPredefinedImage()
        assertNull(imageId)
    }

    @Test
    fun `getSelectedPredefinedImage returns saved image id`() {
        val expectedImageId = 1
        userContextRepository.saveSelectedPredefinedImage(expectedImageId)
        val imageId = userContextRepository.getSelectedPredefinedImage()
        assertEquals(expectedImageId, imageId)
    }

    @Test
    fun `saveSelectedPredefinedImage does not save default int`() {
        userContextRepository.saveSelectedPredefinedImage(KVStore.DEFAULT_INT)
        val imageId = userContextRepository.getSelectedPredefinedImage()
        assertNull(imageId)
    }

    @Test
    fun `saveAuthToken should save token to store`() {
        val expectedToken = "testToken"
        userContextRepository.saveAuthToken(expectedToken)
        val token = userContextRepository.getAuthToken()
        assertEquals(expectedToken, token)
    }
}
