// Bug happens on JVM , JVM -Xuse-ir
// WITH_REFLECT
// IGNORE_BACKEND_FIR: JVM_IR
// TARGET_BACKEND: JVM
// FILE: tmp0.kt

import kotlin.reflect.full.declaredMemberProperties

annotation class Ann(val value: String)

public class Bar(public val value: String)

annotation class Foo {
    companion object {
        @JvmField @Ann("O")
        val FOO = Bar("K")
    }
}

fun box(): String {
    val field = Foo.Companion::class.declaredMemberProperties.single()
    return (field.annotations.single() as Ann).value + (field.get(Foo.Companion) as Bar).value
}
