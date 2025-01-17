// FILE: BoxFunKt.java

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

class BoxFunKt {
   @NotNull
   public static final String box() {
      return "OK";
   }
}

// FILE: kt245.kt

// KJS_WITH_FULL_RUNTIME
fun foo() {
    val l = ArrayList<Int>(2)
    l.add(1)

    for (el in l) {}

    //verify error "Expecting to find integer on stack"
    val iterator = l.iterator()

    //another verify error "Mismatched stack types"
    while (iterator?.hasNext() ?: false) {
        val i = iterator?.next()
    }

    //the same
    if (iterator != null) {
        while (iterator.hasNext()) {
            val i = iterator?.next()
        }
    }

    //this way it works
    if (iterator != null) {
        while (iterator.hasNext()) {
            iterator.next() //because of the bug KT-244 i can't write "val i = iterator.next()"
        }
    }
}

