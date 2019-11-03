package com.github.mriegler.jcrdsl.extensions

import com.github.mriegler.jcrdsl.models.NodeDefinition
import javax.jcr.Node

/**
 * Creates and attaches nodes according to the [definition], returning the given [Node] for chaining.
 *
 * The top level node definition does not refer to the receiver, but
 * to a child to be created
 */
fun Node.attach(definition: NodeDefinition): Node {
    return definition.attach(this)
}

/**
 * Ensures the given [node] matches the [definition] by adding properties and nodes
 * where needed, returning the given node for chaining. This does not delete things not in the definition.
 *
 * The top level node definition refers to the receiver node
 */
fun Node.ensure(definition: NodeDefinition): Node {
    return definition.ensure(this)
}

/**
 * Compares the [Node] to the [definition], returning true if they match.
 */
fun Node.matches(definition: NodeDefinition): Boolean {
    return definition.matches(this)
}

/**
 * Compares the [Node] to the [definition], returning the difference.
 *
 * TODO: Figure out use case. Do we need more than [Node.matches]?
 */
fun Node.compare(definition: NodeDefinition): Node {
    TODO()
}