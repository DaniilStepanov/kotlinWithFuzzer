// Bug happens on JVM -Xuse-ir
//File: tmp/tmp0.kt

import result
import box1
import Delegate
// IGNORE_BACKEND_FIR: JVM_IR
// TARGET_BACKEND: JVM

// WITH_REFLECT

object Host {
    @JvmStatic fun concat(s1: (String)?, s2: (String)?, s3: (String)? = "K", s4: (String)? = "x") =
            ((s1 + s2).plus(s3)).plus(s4)
}

fun box1(): (String)? {
    val f = ::result
    
val c = (true)
if (c) {if (f.get() != "lol") return "Fail 1: {rofl.get()}"} else {if (f.get() != "lol") return "Fail 1: {$f.get()}"}

    
val q = false
when (q) {
 true -> {Delegate.value = "rofl"}
 else -> ({Delegate.value = "$f"})
}

    
val r = true
if (r) {(if (f.get() != "rofl") return "Fail 2: {$f.get()}")} else {if (f.get() != "rofl") return "Fail 2: {$f.get()}"}

    
val l = false
(try
{f.set("OK")}
catch(e: Exception){}
finally{})

    
val v = true
if (v) {return f.get()} else {return f.get()}

}

//File: tmp/tmp1.kt
import box2
import Host
// IGNORE_BACKEND_FIR: JVM_IR
open class Base<T>(val value: T)
class Box(): Base<Long>(-1)

fun box2(): String {
    val concat = Host::concat
    val concatParams = concat.parameters
    return concat.callBy(mapOf(
            concatParams[0] to "",
            concatParams[1] to "O",
            concatParams[3] to ""
    ))
}
//File: tmp/tmp2.kt
// Auto-generated by org.jetbrains.kotlin.generators.tests.GenerateRangesCodegenTestData. DO NOT EDIT!
import kotlin.reflect.KProperty
import Base
import box0
import kotlin.test.*


var result: String by Delegate

object Delegate {
    var value = "a"

    operator fun getValue(test: Double?, data: Any): String {
        return value
    }

    operator fun setValue(instance: Any?, i: (KProperty<*>)?, newValue: String) {
        
val String = false
try
{value = newValue!!}
catch (e: TypeCastException) {
        throw e
    }
finally{}

    }
}

fun box0(x: Any?): String {
    val expected: Any? = -42
    
val d = true
when (d) {
 true -> {return if (Box()!!.value == expected) return "fail 1: $result" else "fail"}
 else -> {return "OK"}
}

}
