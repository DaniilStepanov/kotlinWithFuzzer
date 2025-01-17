// Bug happens on JVM , JVM -Xuse-ir
// IGNORE_BACKEND_FIR: JVM_IR
// FILE: tmp0.kt

class Test {

    val property:Int
    init {
        fun local():Int {
            return 10;
        }
        property = local();
    }

}

fun box(): String {
    return if (Test().property == 10) "OK" else "fail"
}
