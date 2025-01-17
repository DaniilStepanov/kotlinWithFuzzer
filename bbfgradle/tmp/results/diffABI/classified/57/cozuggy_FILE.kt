// Bug happens on JVM , JVM -Xuse-ir
// !LANGUAGE: +InlineClasses
// WITH_RUNTIME
// FILE: tmp0.kt

inline class Z(val x: Int) {
    inner class Inner(val y: Int) {
        val xx = x
    }
}

fun box(): String {
    val zi = Z(42).Inner(100)
    if (zi.xx != 42) throw AssertionError()
    if (zi.y != 100) throw AssertionError()

    return "OK"
}
