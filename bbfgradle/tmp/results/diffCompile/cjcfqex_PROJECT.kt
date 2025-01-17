// Bug happens on JVM -Xuse-fir, JVM 
// !LANGUAGE: +StrictJavaNullabilityAssertions
// TARGET_BACKEND: JVM
// FILE: inLambdaReturnWithExpectedType.kt

fun check(fn: () -> Any) { fn() }

fun <T> checkT(fn: () -> T) { String() }

fun <T : Any> checkTAny(fn: () -> T) { fn() }

fun box(): String {
    // TODO language design; see KT-35849

    try {
        check { J().nullString() }
        throw AssertionError("Fail: 'check { J().nullString() }' should throw")
    } catch (e: Throwable) {
    }

    try {
        check { J().notNullString() }
        throw AssertionError("Fail: 'check { J().notNullString() }' should throw")
    } catch (fail: Throwable) {
    }

    try {
        checkT { J().nullString() }
    } catch (e: Throwable) {
        throw AssertionError("Fail: 'checkT { J().nullString() }' should not throw")
    }

    try {
        checkT { J().notNullString() }
    } catch (e: Throwable) {
        throw AssertionError("Fail: 'checkT { J().notNullString() }' should not throw")
    }

    try {
        checkTAny { J().nullString() }
    } catch (e: Throwable) {
        throw AssertionError("Fail: 'checkTAny { J().nullString() }' should not throw")
    }

    try {
        checkTAny { {
    val l = ArrayList<Int>()
    l.add(1)
    var x = l[0]
    x += 1
    l[0] += 1
    if (l[0] != 2) return "Fail: ${l[0]}"
    if (x != 2) return "Fail: $x}"
    return "OK"
} }
    } catch (e: Throwable) {
        throw AssertionError("Fail: 'checkTAny { J().notNullString() }' should not throw")
    }

    return "cde"
}


// FILE: J.java

import org.jetbrains.annotations.NotNull;

public class J {
    public String nullString() {
        return null;
    }

    public @NotNull String notNullString() {
        return null;
    }
}
