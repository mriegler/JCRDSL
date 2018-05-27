package com.github.mriegler.jcrdsl.models

import org.apache.jackrabbit.commons.SimpleValueFactory
import java.io.InputStream
import java.math.BigDecimal
import java.util.*
import javax.jcr.Node
import javax.jcr.Value
import javax.jcr.PropertyType

data class PropertyDefinition(
    val name: String,
    val value: Value
) {
    constructor(name: String, value: String) : this(name, SimpleValueFactory().createValue(value))
    constructor(name: String, value: Boolean) : this(name, SimpleValueFactory().createValue(value))
    constructor(name: String, value: Long) : this(name, SimpleValueFactory().createValue(value))
    constructor(name: String, value: InputStream) : this(name, SimpleValueFactory().createValue(value))
    constructor(name: String, value: Calendar) : this(name, SimpleValueFactory().createValue(value))
    constructor(name: String, value: Node) : this(name, SimpleValueFactory().createValue(value))
    constructor(name: String, value: Double) : this(name, SimpleValueFactory().createValue(value))
    constructor(name: String, value: BigDecimal) : this(name, SimpleValueFactory().createValue(value))

    /**
     * Delegates to [SimpleValueFactory.createValue] with [type] coming from [PropertyType]
     */
    constructor(name: String, value: String, type: Integer) : this(name, SimpleValueFactory().createValue(value, type.toInt()))
}