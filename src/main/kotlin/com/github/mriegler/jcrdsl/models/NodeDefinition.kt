package com.github.mriegler.jcrdsl.models

import javax.jcr.Node

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