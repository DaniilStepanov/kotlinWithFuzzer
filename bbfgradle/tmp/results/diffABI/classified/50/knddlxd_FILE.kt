// Bug happens on JVM , JVM -Xuse-ir
// FILE: tmp0.kt

fun box(): String {
    infix fun Int.foo(a: Int): Int = a + 2

    val s = object {
        fun test(): Int {
            return 1 foo 1
        }
    }

    fun local(): Int {
        return 1 foo 1
    }

    if (s.test() != 3) return "Fail"

    if (local() != 3) return "Fail"

    return "OK"
}
