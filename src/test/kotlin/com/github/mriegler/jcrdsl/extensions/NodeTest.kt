package com.github.mriegler.jcrdsl.extensions

import com.github.mriegler.jcrdsl.helpers.session
import com.github.mriegler.jcrdsl.node
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

val nodeDef = node {
    name = "master"
    property("test", "test")
}

class NodeTest : StringSpec({
    "attach should apply the node definition" {
        val newNode = session.rootNode.attach(nodeDef)
        newNode.name shouldBe "master"
        newNode.getProperty("test").value should {
            it.string shouldBe "test"
        }

    }
})
