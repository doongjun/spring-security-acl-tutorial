package com.tutorial.acl

import com.tutorial.acl.domain.Board
import com.tutorial.acl.repository.BoardRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser

@SpringBootTest
class AclBoardTest {
    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var boardRepository: BoardRepository

    private val noticeId = 1

    @Test
    @WithMockUser(username = "userA")
    fun `userA_게시판_전체조회`() {
        //when
        val findAll: List<Board> = boardRepository.findAll()
        val names = findAll.map { it.name }.joinToString(",")
        log.info(names)

        //then
        assertThat(findAll.size).isEqualTo(1)
        assertThat(findAll[0].id).isEqualTo(noticeId)
        assertThat(findAll[0].name).isEqualTo("공지사항")
    }

}