// Bug happens on JVM , JVM -Xuse-ir
// IGNORE_BACKEND_FIR: JVM_IR
// IGNORE_BACKEND: NATIVE
// FILE: tmp0.kt

open class Map1 : HashMap<String, Any?>()
class Map2 : Map1()
fun box(): String {
    val m = Map2()
    if (m.entries.size != 0) return "fail 1"

    m.put("56", "OK")
    val x = m.entries.iterator().next()

    if (x.key != "56" || x.value != "OK") return "fail 2"

    return "OK"
}
