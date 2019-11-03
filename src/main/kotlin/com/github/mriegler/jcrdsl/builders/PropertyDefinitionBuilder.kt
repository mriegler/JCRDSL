package com.github.mriegler.jcrdsl.builders

import com.github.mriegler.jcrdsl.enums.PropertyType
import com.github.mriegler.jcrdsl.models.PropertyDefinition
import org.apache.jackrabbit.commons.SimpleValueFactory
import javax.jcr.Property
import javax.jcr.Value

class PropertyDefinitionBuilder(var name: String = "",
                                var _value: Value = SimpleValueFactory().createValue(false),
                                var multiple: Boolean = false,
                                var type: PropertyType = PropertyType.UNDEFINED) {

    var value: Any
        get() = _value
        set(newVal) {
            val f = SimpleValueFactory()
            when(newVal) {
                is String -> f.createValue(newVal)
                is Long -> f.createValue(newVal)
                is Boolean -> f.createValue(newVal)
            }
        }
    fun build(): PropertyDefinition {
        return PropertyDefinition(name, _value)
    }

    companion object {
    }
}