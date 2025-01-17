// Bug happens on JVM , JVM -Xuse-ir
// FILE: tmp0.kt

open class A
class B : A() {
    fun foo() = 1
}

class Test {
    val a : A = B()
    private val b : B get() = a as B //'private' is important here

    fun outer() : Int {
        fun inner() : Int = b.foo() //'no such field error' here
        return inner()
    }
}

fun box() = if (Test().outer() == 1) "OK" else "fail"
