// Bug happens on JVM -Xuse-ir
//File: tmp/tmp0.kt
package a.b


import a.A
// IGNORE_BACKEND_FIR: JVM_IR
// IGNORE_BACKEND: JS_IR
// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS, NATIVE

// WITH_REFLECT

class A(val result: String)

fun box0(): String {
    val clazz = A::class.java
    val constructor = clazz.getDeclaredConstructor(String::class.java, Int::class.java, String::class.java)
    val parameters = constructor.getParameters()

    if (parameters[0].name != "\$enum\$name") return "wrong entry name: ${parameters[0].name}"
    if (!parameters[0].isSynthetic() || parameters[0].isImplicit()) return "wrong name flags: ${parameters[0].modifiers}"

    if (parameters[1].name != "\$enum\$ordinal") return "wrong ordinal name: ${parameters[1].name}"
    if (!parameters[1].isSynthetic() || parameters[1].isImplicit()) return "wrong ordinal flags: ${parameters[1].modifiers}"

    if (parameters[2].modifiers != 0) return "wrong modifier on value parameter: ${parameters[2].modifiers}"
    return parameters[2].name
}

//File: tmp/tmp1.kt
package a


import a.b.A
// IGNORE_BACKEND_FIR: JVM_IR
// SKIP_JDK6
// TARGET_BACKEND: JVM
// WITH_RUNTIME
// FULL_JDK
// KOTLIN_CONFIGURATION_FLAGS: +JVM.PARAMETERS_METADATA

enum class A(val OK: String) {

}

fun box1(): String {
    val a = (::A)!!.call("OK")
    return a.result
}