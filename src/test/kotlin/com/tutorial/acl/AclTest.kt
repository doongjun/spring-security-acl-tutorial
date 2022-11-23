package com.tutorial.acl

import com.tutorial.acl.domain.NoticeMessage
import com.tutorial.acl.repository.NoticeMessageRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser

@SpringBootTest
class AclTest {

    @Autowired
    private lateinit var noticeMessageRepository: NoticeMessageRepository

    @Test
    @WithMockUser(username = "manager")
    fun givenUserManager_whenFindAllMessage_thenReturnFirstMessage() {
        val details: List<NoticeMessage> = noticeMessageRepository.findAll()
        val firstMessageId = 1
        assertNotNull(details)
        assertEquals(1, details.size)
        assertEquals(firstMessageId, details[0].id)
    }

}