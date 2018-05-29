package com.github.mriegler.jcrdsl.extensions

import com.github.mriegler.jcrdsl.helpers.session
import com.github.mriegler.jcrdsl.node
import io.kotlintest.matchers.boolean.shouldBeTrue
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

    "matches should compare the nodes correctly" {
        val newNode = session.rootNode.addNode("master", "nt:unstructured")
        newNode.setProperty("test", "test")

        newNode.matches(node {
            name = "master"
            property("test", "test")
        })
    }

    "ensure should create missing nodes and properties" {
        val newNode = session.rootNode.addNode("test", "nt:unstructured")
        newNode.setProperty("test", "test")
        val child = newNode.addNode("child")
        child.setProperty("test", 3)

        val def = node {
            name = "test"
            property("test2", "test2")
            node {
                name = "child"
                property("test", 3)
            }
        }

        newNode.ensure(def)

        newNode.name shouldBe "test"
        newNode.getProperty("test").string shouldBe "test"
        newNode.getProperty("test2").string shouldBe "test2"
        newNode.hasNode("child").shouldBeTrue()
        newNode.getNode("child").getProperty("test").long shouldBe 3L
    }
})
