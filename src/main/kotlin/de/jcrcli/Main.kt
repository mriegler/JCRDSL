package de.jcrcli

import com.authzee.kotlinguice4.getInstance
import com.google.inject.Guice
import com.google.inject.Inject
import mu.KLogging
import mu.KotlinLogging
import org.apache.jackrabbit.core.RepositoryImpl
import org.apache.jackrabbit.core.config.RepositoryConfig
import javax.jcr.Node
import javax.jcr.SimpleCredentials

private val logger = KotlinLogging.logger {}
fun main(args: Array<String>) {
    logger.debug { "Starting main with args $args, size: ${args.size}" }
    val injector = Guice.createInjector(Module())
    val publicRelations = injector.getInstance<PublicRelations>()

    publicRelations.handleUser("Marcel")

    val classLoader = Thread.currentThread().contextClassLoader
    val conf = classLoader.getResourceAsStream("repo.xml")

    val repoConf = RepositoryConfig.create(conf, "build/storage")
    val repo = RepositoryImpl.create(repoConf)
    val session = repo.login(SimpleCredentials("admin", "admin".toCharArray()))

    val root = session.rootNode
    root.addNode("test").setProperty("count", 1)
    logger.info { "Got repo: $repo, node: ${root.getNode("test")}" }
}

object node {
    operator fun invoke(node: Node, init: Node.() -> Unit) {
    }
}

class PublicRelations @Inject constructor(val greeter: Greeter) {
    fun handleUser(name: String) {
        logger.debug { "PR handling user $name" }
        println(greeter.greet(name))
    }

    companion object : KLogging()
}

interface Greeter {
    fun greet(name: String): String
}

class RudeGreeter : Greeter {
    override fun greet(name: String) = "Hi, $name, you fatass!"
}

class NiceGreeter : Greeter {
    override fun greet(name: String): String = "Hello, my dear $name"
}
