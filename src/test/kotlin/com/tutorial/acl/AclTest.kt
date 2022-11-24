package com.tutorial.acl

import com.tutorial.acl.domain.NoticeMessage
import com.tutorial.acl.repository.NoticeMessageRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.test.context.support.WithMockUser

@SpringBootTest
class AclTest {

    @Autowired
    private lateinit var noticeMessageRepository: NoticeMessageRepository

    val firstMessageId = 1
    val secondMessageId = 2
    val editedContent = "댓글 수정"

    @Test
    @WithMockUser(username = "manager")
    fun givenUserManager_whenFindAllMessage_thenReturnFirstMessage() {
        val details: List<NoticeMessage> = noticeMessageRepository.findAll()
        assertNotNull(details)
        assertEquals(1, details.size)
        assertEquals(firstMessageId, details[0].id)
    }

    @Test
    @WithMockUser(roles = ["EDITOR"])
    fun givenRoleEditor_whenFindAllMessage_thenReturn3Message() {
        val details: List<NoticeMessage> = noticeMessageRepository.findAll()
        assertNotNull(details)
        assertEquals(3, details.size)
    }

    @Test
    @WithMockUser(username = "manager")
    fun givenUserManager_whenFind1stMessageByIdAndUpdateItsContent_thenOK() {
        val firstMessage: NoticeMessage? = noticeMessageRepository.findById(firstMessageId)
        assertNotNull(firstMessage)
        assertEquals(firstMessageId, firstMessage?.id)

        firstMessage?.content = editedContent
        noticeMessageRepository.save(firstMessage)

        val editedFirstMessage: NoticeMessage? = noticeMessageRepository.findById(firstMessageId)
        assertNotNull(editedFirstMessage)
        assertEquals(firstMessageId, editedFirstMessage?.id)
        assertEquals(editedContent, editedFirstMessage?.content)
    }

    @Test
    @WithMockUser(username = "hr")
    fun givenUsernameHr_whenFindMessageById2_thenOK() {
        val secondMessage: NoticeMessage? = noticeMessageRepository.findById(secondMessageId)
        assertNotNull(secondMessage)
        assertEquals(secondMessageId, secondMessage?.id)
    }

    @Test
    @WithMockUser(username = "hr")
    fun givenUsernameHr_whenUpdateMessageWithId2_thenFail() {
        val secondMessage = NoticeMessage(
            id = secondMessageId,
            content = editedContent
        )

        assertThrows<AccessDeniedException> {
            noticeMessageRepository.save(secondMessage)
        }
    }

}