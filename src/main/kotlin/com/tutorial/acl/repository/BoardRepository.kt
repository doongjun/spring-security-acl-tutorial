package com.tutorial.acl.repository

import com.tutorial.acl.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository: JpaRepository<Board, Long> {

    @PostFilter("hasPermission(filterObject, 'READ')")
    override fun findAll(): List<Board>

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    fun findById(id: Int?): Board?

    @PreAuthorize("hasPermission(#board, 'WRITE')")
    fun save(@Param("board") board: Board?): Board?
}