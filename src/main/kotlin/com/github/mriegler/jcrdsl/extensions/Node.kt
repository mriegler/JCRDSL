package com.github.mriegler.jcrdsl.extensions

import com.github.mriegler.jcrdsl.models.NodeDefinition
import javax.jcr.Node

fun Node.attach(definition: NodeDefinition): Node {
    return definition.attach(this)
}