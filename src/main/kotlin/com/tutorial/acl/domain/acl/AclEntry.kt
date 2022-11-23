package com.tutorial.acl.domain.acl

import javax.persistence.*

@Entity(name = "AclEntry")
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["aclObjectIdentity", "aceOrder"])])
class AclEntry (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var aceOrder: Int? = null,

    @Column(nullable = false, columnDefinition = "INTEGER UNSIGNED")
    var mask: Int? = null,

    @Column(nullable = false)
    var granting: Boolean = false,

    @Column(nullable = false)
    var auditSuccess: Boolean = false,

    @Column(nullable = false)
    var auditFailure: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aclObjectIdentity", nullable = false)
    var aclObject: AclObjectIdentity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sid", nullable = false)
    var aclSid: AclSid? = null,
)