package com.tutorial.acl.domain.acl

import javax.persistence.*

@Entity(name = "AclObjectIdentity")
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["objectIdClass", "objectIdIdentity"])])
class AclObjectIdentity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var objectIdIdentity: Long? = null,

    @Column(nullable = false)
    var entriesInheriting: Boolean? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentObject")
    var parent: AclObjectIdentity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "objectIdClass", nullable = false)
    var objectClass: AclClass? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerSid")
    var owner: AclSid? = null,
)