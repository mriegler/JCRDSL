package com.github.mriegler.jcrdsl.builders

import com.github.mriegler.jcrdsl.models.NodeDefinition
import com.github.mriegler.jcrdsl.models.PropertyDefinition

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