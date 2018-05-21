package de.jcrcli

import mu.KotlinLogging
import org.apache.jackrabbit.commons.SimpleValueFactory
import org.apache.jackrabbit.core.RepositoryImpl
import org.apache.jackrabbit.core.config.RepositoryConfig
import javax.jcr.Node
import javax.jcr.SimpleCredentials
import javax.jcr.Value

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

fun Node.attach(definition: NodeDefinition): Node {
    return definition.attach(this)
}

fun node(setup: NodeDefinitionBuilder.() -> Unit): NodeDefinition {
    val nodeDefinitionBuilder = NodeDefinitionBuilder()
    nodeDefinitionBuilder.setup()
    return nodeDefinitionBuilder.build()
}

class NodeDefinitionBuilder {
    val props = mutableListOf<PropertyDefinition>()
    val children = mutableListOf<NodeDefinition>()
    val mixins = mutableListOf<String>()
    var primaryType = "nt:unstructured"
    var name: String? = null

    fun build(): NodeDefinition {
        val name = name ?: throw IllegalStateException("a node definition requires a name")

        return NodeDefinition(name, primaryType, mixins, props, children)
    }

    fun property(setup: PropertyDefinitionBuilder.() -> Unit) {
        val builder = PropertyDefinitionBuilder()
        builder.setup()
        props += builder.build()
    }

    fun node(setup: NodeDefinitionBuilder.() -> Unit) {
        val builder = NodeDefinitionBuilder()
        builder.setup()
        children += builder.build()
    }
}

class PropertyDefinitionBuilder {
    var name: String = ""
    var value: Value = SimpleValueFactory().createValue(false)
    fun build(): PropertyDefinition {
        return PropertyDefinition(name, value)
    }
}

data class NodeDefinition(
    val name: String,
    val primaryType: String,
    val mixins: List<String>,
    val properties: List<PropertyDefinition>,
    val children: List<NodeDefinition>
) {
    fun attach(parent: Node): Node {
        val newNode = parent.addNode(name, primaryType)
        mixins.forEach { newNode.addMixin(it) }
        properties.forEach { newNode.setProperty(it.name, it.value) }
        children.forEach { it.attach(newNode) }

        return newNode
    }
}

data class PropertyDefinition(
    val name: String,
    val value: Value
)

