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

    fun ensure(node: Node): Node {
        if (!node.primaryNodeType.isNodeType(primaryType)) {
            node.setPrimaryType(primaryType)
        }

        mixins.forEach { node.addMixin(it) }
        properties.forEach { node.setProperty(it.name, it.value) }
        children.forEach {
            if (node.hasNode(it.name))
                it.ensure(node.getNode(it.name))
            else
                it.attach(node)
        }

        return node
    }

    fun matches(node: Node): Boolean {
        if (!node.primaryNodeType.isNodeType(primaryType) ||
                node.name != name ||
                mixins.any { !node.isNodeType(it) } ||
                properties.any { !node.hasProperty(it.name) || node.getProperty(it.name) != it.value } ||
                children.any { !node.hasNode(it.name) || !it.matches(node.getNode(it.name))}) {
            return false
        }

        return true
    }
}