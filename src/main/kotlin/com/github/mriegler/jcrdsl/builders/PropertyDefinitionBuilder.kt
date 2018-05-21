package com.github.mriegler.jcrdsl.builders

import com.github.mriegler.jcrdsl.models.PropertyDefinition
import org.apache.jackrabbit.commons.SimpleValueFactory
import javax.jcr.Value

class PropertyDefinitionBuilder {
    var name: String = ""
    var value: Value = SimpleValueFactory().createValue(false)
    fun build(): PropertyDefinition {
        return PropertyDefinition(name, value)
    }
}