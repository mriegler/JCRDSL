package com.github.mriegler.jcrdsl

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class DslTest : StringSpec({
    "writing the same dsl twice should result in the same definition" {
        val def1 = node {
            name = "test"
            property("test", "test")
        }
        val def2 = node {
            name = "test"
            property("test", "test")
        }

        node {
            name = "asd"
            property("asd", "asd")
            property("aqwe", "asd")
        }


        def1 shouldBe def2
    }

    "definitions should be the same, no matter whether they are a subnode or not" {
        val def = node {
            name = "test"
            node {
                name = "child"
            }
        }

        def.children[0] shouldBe node { name = "child" }
    }
})