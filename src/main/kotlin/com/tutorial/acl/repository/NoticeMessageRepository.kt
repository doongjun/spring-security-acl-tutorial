package com.tutorial.acl.repository

import com.tutorial.acl.domain.NoticeMessage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize


interface NoticeMessageRepository: JpaRepository<NoticeMessage, Long> {

    @PostFilter("hasPermission(filterObject, 'READ')")
    override fun findAll(): List<NoticeMessage>

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    fun findById(id: Int?): NoticeMessage?

    @PreAuthorize("hasPermission(#noticeMessage, 'WRITE')")
    fun save(@Param("noticeMessage") noticeMessage: NoticeMessage?): NoticeMessage?

}