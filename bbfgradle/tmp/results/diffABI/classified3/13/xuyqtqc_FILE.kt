// Bug happens on JVM , JVM -Xuse-ir
// IGNORE_BACKEND_FIR: JVM_IR
// FILE: tmp0.kt

package foo

import kotlin.reflect.KProperty

class Delegate {
    var inner = 1
    inline operator fun getValue(t: Any?, p: KProperty<*>): Int = inner
    inline  operator fun setValue(t: Any?, p: KProperty<*>, i: Int) {
        inner = i
    }
}

fun box(): String {
    var prop: Int by Delegate()
    if (prop != 1) return "fail get"
    prop = 2
    if (prop != 2) return "fail set"
    return "OK"
}
