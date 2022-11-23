package com.tutorial.acl.security.acl

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cache.ehcache.EhCacheFactoryBean
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.acls.AclPermissionEvaluator
import org.springframework.security.acls.domain.*
import org.springframework.security.acls.jdbc.BasicLookupStrategy
import org.springframework.security.acls.jdbc.JdbcMutableAclService
import org.springframework.security.acls.jdbc.LookupStrategy
import org.springframework.security.acls.model.PermissionGrantingStrategy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import javax.sql.DataSource


@Configuration
@EnableAutoConfiguration
class AclContext(
    private val dataSource: DataSource
) {
    @Bean
    fun defaultMethodSecurityExpressionHandler(): MethodSecurityExpressionHandler? {
        val expressionHandler = DefaultMethodSecurityExpressionHandler()
        val permissionEvaluator = AclPermissionEvaluator(aclService())
        expressionHandler.setPermissionEvaluator(permissionEvaluator)
        return expressionHandler
    }

    @Bean
    fun aclService(): JdbcMutableAclService? {
        return JdbcMutableAclService(
            dataSource, lookupStrategy(), aclCache()
        )
    }

    @Bean
    fun lookupStrategy(): LookupStrategy? {
        return BasicLookupStrategy(
            dataSource,
            aclCache(),
            aclAuthorizationStrategy(),
            ConsoleAuditLogger()
        )
    }

    @Bean
    fun aclCache(): EhCacheBasedAclCache {
        return EhCacheBasedAclCache(
            aclEhCacheFactoryBean().getObject(),
            permissionGrantingStrategy(),
            aclAuthorizationStrategy()
        )
    }

    @Bean
    fun aclAuthorizationStrategy(): AclAuthorizationStrategy {
        return AclAuthorizationStrategyImpl(
            SimpleGrantedAuthority("ROLE_ADMIN")
        )
    }

    @Bean
    fun aclEhCacheFactoryBean(): EhCacheFactoryBean {
        val ehCacheFactoryBean = EhCacheFactoryBean()
        ehCacheFactoryBean.setCacheManager(aclCacheManager().`object`!!)
        ehCacheFactoryBean.setCacheName("aclCache")
        return ehCacheFactoryBean
    }

    @Bean
    fun permissionGrantingStrategy(): PermissionGrantingStrategy {
        return DefaultPermissionGrantingStrategy(
            ConsoleAuditLogger()
        )
    }

    @Bean
    fun aclCacheManager(): EhCacheManagerFactoryBean {
        return EhCacheManagerFactoryBean()
    }
}