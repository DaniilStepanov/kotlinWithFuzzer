// Bug happens on JVM , JVM -Xuse-ir
// !LANGUAGE: +NewInference, +FunctionalInterfaceConversion, +SamConversionPerArgument, +SamConversionForKotlinFunctions
// IGNORE_BACKEND_FIR: JVM_IR
// FILE: tmp0.kt

fun interface KRunnable {
    fun invoke()
}

fun test(a: Any?) {
    a as () -> Unit
    KRunnable(a).invoke()
}

fun box(): String {
    var result = "Fail"
    test {
        result = "OK"
    }
    return result
}
