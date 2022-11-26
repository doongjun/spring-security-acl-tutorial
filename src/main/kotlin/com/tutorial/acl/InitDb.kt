package com.tutorial.acl

import com.tutorial.acl.domain.Board
import com.tutorial.acl.domain.NoticeMessage
import com.tutorial.acl.domain.acl.AclClass
import com.tutorial.acl.domain.acl.AclEntry
import com.tutorial.acl.domain.acl.AclObjectIdentity
import com.tutorial.acl.domain.acl.AclSid
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@Component
class InitDb(
    private val initService: InitService
) {
    @PostConstruct
    fun init() {
//        initService.dbInit1()
        initService.dbInit2()
    }

    companion object {
        @Component
        @Transactional
        class InitService(
            private val em: EntityManager
        ) {
            fun dbInit1() {
                val userManager = AclSid(principal = true, sid = "manager")
                val userHr = AclSid(principal = true, sid = "hr")
                val roleEditor = AclSid(principal = false, sid = "ROLE_EDITOR")
                persistObjects(userManager, userHr, roleEditor)

                val aclClass = AclClass(className = "com.tutorial.acl.domain.NoticeMessage")
                persistObjects(aclClass)

                val message1 = NoticeMessage(id = 1, content = "First Level Message")
                val message2 = NoticeMessage(id = 2, content = "Second Level Message")
                val message3 = NoticeMessage(id = 3, content = "Third Level Message")
                persistObjects(message1, message2, message3)

                val aclObjectIdentity1 = AclObjectIdentity(
                    objectClass = aclClass,
                    objectIdIdentity = 1,
                    owner = roleEditor,
                    entriesInheriting = false
                )
                val aclObjectIdentity2 = AclObjectIdentity(
                    objectClass = aclClass,
                    objectIdIdentity = 2,
                    owner = roleEditor,
                    entriesInheriting = false
                )
                val aclObjectIdentity3 = AclObjectIdentity(
                    objectClass = aclClass,
                    objectIdIdentity = 3,
                    owner = roleEditor,
                    entriesInheriting = false
                )
                persistObjects(aclObjectIdentity1, aclObjectIdentity2, aclObjectIdentity3)

                val aclEntry1 = AclEntry(
                    aclObject = aclObjectIdentity1,
                    aceOrder = 1,
                    aclSid = userManager,
                    mask = 1
                )
                val aclEntry2 = AclEntry(
                    aclObject = aclObjectIdentity1,
                    aceOrder = 2,
                    aclSid = userManager,
                    mask = 2
                )
                val aclEntry3 = AclEntry(
                    aclObject = aclObjectIdentity1,
                    aceOrder = 3,
                    aclSid = roleEditor,
                    mask = 1
                )
                val aclEntry4 = AclEntry(
                    aclObject = aclObjectIdentity2,
                    aceOrder = 1,
                    aclSid = userHr,
                    mask = 1
                )
                val aclEntry5 = AclEntry(
                    aclObject = aclObjectIdentity2,
                    aceOrder = 2,
                    aclSid = roleEditor,
                    mask = 1
                )
                val aclEntry6 = AclEntry(
                    aclObject = aclObjectIdentity3,
                    aceOrder = 1,
                    aclSid = roleEditor,
                    mask = 1
                )
                val aclEntry7 = AclEntry(
                    aclObject = aclObjectIdentity3,
                    aceOrder = 2,
                    aclSid = roleEditor,
                    mask = 2
                )
                persistObjects(aclEntry1, aclEntry2, aclEntry3, aclEntry4, aclEntry5, aclEntry6, aclEntry7)
            }

            fun dbInit2() {
                val userA = AclSid(principal = true, sid = "userA")
                val userB = AclSid(principal = true, sid = "userB")
                val roleEditor = AclSid(principal = false, sid = "ROLE_EDITOR")
                persistObjects(userA, userB, roleEditor)

                val aclClass = AclClass(className = "com.tutorial.acl.domain.Board")
                persistObjects(aclClass)

                val notice = Board(id = 1, name = "공지사항")
                val freeBoard = Board(id = 2, name = "자유게시판")
                persistObjects(notice, freeBoard)

                val aclObjectIdentity1 = AclObjectIdentity(
                    objectClass = aclClass,
                    objectIdIdentity = 1,
                    owner = roleEditor,
                    entriesInheriting = false
                )
                val aclObjectIdentity2 = AclObjectIdentity(
                    objectClass = aclClass,
                    objectIdIdentity = 2,
                    owner = roleEditor,
                    entriesInheriting = false
                )
                persistObjects(aclObjectIdentity1, aclObjectIdentity2)

                val aclEntry1 = AclEntry(
                    aclObject = aclObjectIdentity1,
                    aceOrder = 1,
                    aclSid = userA,
                    mask = 1
                )
                val aclEntry2 = AclEntry(
                    aclObject = aclObjectIdentity1,
                    aceOrder = 2,
                    aclSid = userA,
                    mask = 2
                )
                persistObjects(aclEntry1, aclEntry2)
            }

            private fun persistObjects(vararg objects: Any) =
                objects.forEach { em.persist(it) }

        }
    }

}