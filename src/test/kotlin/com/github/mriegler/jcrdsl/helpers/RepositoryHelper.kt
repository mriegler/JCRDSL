package com.github.mriegler.jcrdsl.helpers

import org.apache.jackrabbit.core.RepositoryImpl
import org.apache.jackrabbit.core.config.RepositoryConfig
import javax.jcr.Repository
import javax.jcr.Session
import javax.jcr.SimpleCredentials


val basicRepository: Repository by lazy {
    val classLoader = Thread.currentThread().contextClassLoader
    val conf = classLoader.getResourceAsStream("repo.xml")

    val repoConf = RepositoryConfig.create(conf, "build/storage")
    val repo = RepositoryImpl.create(repoConf)

    repo
}

val session: Session
    get() {
        return basicRepository.login(SimpleCredentials("admin", "admin".toCharArray()))
    }