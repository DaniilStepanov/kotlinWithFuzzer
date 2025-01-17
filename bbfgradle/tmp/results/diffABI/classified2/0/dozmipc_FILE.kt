// Bug happens on JVM , JVM -Xuse-ir
// !LANGUAGE: +InlineClasses
// WITH_RUNTIME
// IGNORE_BACKEND: JS, JS_IR, NATIVE
// FILE: tmp0.kt

class CharacterLiteral(private val prefix: NamelessString, private val s: NamelessString) {
    override fun toString(): String = "$prefix'$s'"
}

inline class NamelessString(val b: ByteArray) {
    override fun toString(): String = String(b)
}

fun box(): String {
    val ns1 = NamelessString("abc".toByteArray())
    val ns2 = NamelessString("def".toByteArray())
    val cl = CharacterLiteral(ns1, ns2)
    if (cl.toString() != "abc'def'") return throw AssertionError(cl.toString())
    return "OK"
}
