package com.github.mriegler.jcrdsl

import com.github.mriegler.jcrdsl.builders.NodeDefinitionBuilder
import com.github.mriegler.jcrdsl.extensions.attach
import com.github.mriegler.jcrdsl.models.NodeDefinition
import mu.KotlinLogging
import org.apache.jackrabbit.commons.SimpleValueFactory
import org.apache.jackrabbit.core.RepositoryImpl
import org.apache.jackrabbit.core.config.RepositoryConfig
import javax.jcr.SimpleCredentials

private val logger = KotlinLogging.logger {}
fun main(args: Array<String>) {
    logger.debug { "Starting main with args $args, size: ${args.size}" }
    val classLoader = Thread.currentThread().contextClassLoader
    val conf = classLoader.getResourceAsStream("repo.xml")

    val repoConf = RepositoryConfig.create(conf, "build/storage")
    val repo = RepositoryImpl.create(repoConf)
    val session = repo.login(SimpleCredentials("admin", "admin".toCharArray()))

    val root = session.rootNode
    root.addNode("test").setProperty("count", 1)
    logger.info { "Got repo: $repo, node: ${root.getNode("test")}" }
    val definition = node {
        name = "masternode"

        property {
            name = "testProp"
            value = SimpleValueFactory().createValue("Stringval")
        }
        node {
            name = "childnode"
            primaryType = "nt:unstructured"

            property {
                name = "childProp"
                value = SimpleValueFactory().createValue(true)
            }
        }
    }

    logger.info("Definition is: $definition")

    val attachedNode = root.attach(definition)

    logger.info("Attached node $attachedNode, node name ${attachedNode.name}")

}

fun node(setup: NodeDefinitionBuilder.() -> Unit): NodeDefinition {
    val nodeDefinitionBuilder = NodeDefinitionBuilder()
    nodeDefinitionBuilder.setup()
    return nodeDefinitionBuilder.build()
}

