// FILE: BoxFunKt.java

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;

class BoxFunKt {
   @NotNull
   public static final String box() {
      String s = (String)null;

      try {
         Intrinsics.checkNotNull(s);
         return "Fail: NPE should have been thrown";
      } catch (Throwable var2) {
         return Intrinsics.areEqual(Reflection.getOrCreateKotlinClass(var2.getClass()), Reflection.getOrCreateKotlinClass(NullPointerException.class)) ^ true ? "Fail: exception class should be NPE: " + Reflection.getOrCreateKotlinClass(var2.getClass()) : "OK";
      }
   }
}

// FILE: stepOneThenStepOne6.kt



// For "step" progressions in JVM IR, when the step is constant and == 1, and "step" is called on a literal progression which we already
// know to have a step whose absolute value is 1, we can essentially ignore the "step" call.
//
// Expected lowered form of loop:
//
//   // Standard form of loop over progression
//   var inductionVar = 1
//   val last = 4
//   val step = 1
//   if (inductionVar <= last) {
//     // Loop is not empty
//     do {
//       val i = inductionVar
//       inductionVar += step
//       // Loop body
//     } while (inductionVar <= last)
//   }

// 0 iterator
// 0 getStart
// 0 getEnd
// 0 getFirst
// 0 getLast
// 0 getStep
// 0 INVOKESTATIC kotlin/internal/ProgressionUtilKt.getProgressionLastElement
// 0 NEW java/lang/IllegalArgumentException
// 0 ATHROW
// 1 IF_ICMPLE
// 1 IF