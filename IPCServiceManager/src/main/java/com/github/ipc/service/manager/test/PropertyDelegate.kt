package com.github.ipc.service.manager.test

import kotlin.reflect.KProperty

class PropertyDelegate<T> {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return "$thisRef, thank you for delegating '${property.name}' to me!" as? T
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}