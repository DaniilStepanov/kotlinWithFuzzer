// FILE: BoxFunKt.java

import kotlin.Metadata;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

class BoxFunKt {
   @NotNull
   public static final String box() {
      byte var3 = 1;
      IntProgression var10000 = RangesKt.step(RangesKt.step((IntProgression)(new IntRange(var3, 8)), 2), 2);
      int i = var10000.getFirst();
      int var1 = var10000.getLast();
      int var2 = var10000.getStep();
      if (var2 >= 0) {
         if (i > var1) {
            return "OK";
         }
      } else if (i < var1) {
         return "OK";
      }

      while(i != var1) {
         i += var2;
      }

      return "OK";
   }
}

// FILE: negateConstantCompare0.kt

