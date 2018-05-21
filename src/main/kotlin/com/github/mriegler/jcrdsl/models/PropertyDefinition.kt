package com.github.mriegler.jcrdsl.models

import javax.jcr.Value

data class PropertyDefinition(
    val name: String,
    val value: Value
)