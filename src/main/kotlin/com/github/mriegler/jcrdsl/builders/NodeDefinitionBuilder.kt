package com.github.mriegler.jcrdsl.builders

import com.github.mriegler.jcrdsl.models.NodeDefinition
import com.github.mriegler.jcrdsl.models.PropertyDefinition
import java.io.InputStream
import java.math.BigDecimal
import java.util.*
import javax.jcr.Node

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

    fun property(name: String, value: String?) {
        props += when(value) {
            null -> PropertyDefinition(name)
            else -> PropertyDefinition(name, value)
        }
    }

    fun property(name: String, value: Boolean?) {
        props += when(value) {
            null -> PropertyDefinition(name)
            else -> PropertyDefinition(name, value)
        }
    }

    fun property(name: String, value: Long?) {
        props += when(value) {
            null -> PropertyDefinition(name)
            else -> PropertyDefinition(name, value)
        }
    }

    fun property(name: String, value: InputStream?) {
        props += when(value) {
            null -> PropertyDefinition(name)
            else -> PropertyDefinition(name, value)
        }
    }

    fun property(name: String, value: Calendar?) {
        props += when(value) {
            null -> PropertyDefinition(name)
            else -> PropertyDefinition(name, value)
        }
    }

    fun property(name: String, value: Node?) {
        props += when(value) {
            null -> PropertyDefinition(name)
            else -> PropertyDefinition(name, value)
        }
    }

    fun property(name: String, value: Double?) {
        props += when(value) {
            null -> PropertyDefinition(name)
            else -> PropertyDefinition(name, value)
        }
    }

    fun property(name: String, value: BigDecimal?) {
        props += when(value) {
            null -> PropertyDefinition(name)
            else -> PropertyDefinition(name, value)
        }
    }

    fun node(setup: NodeDefinitionBuilder.() -> Unit) {
        val builder = NodeDefinitionBuilder()
        builder.setup()
        children += builder.build()
    }
}