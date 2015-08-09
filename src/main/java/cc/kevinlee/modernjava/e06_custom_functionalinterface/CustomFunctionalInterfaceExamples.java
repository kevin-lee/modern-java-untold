package cc.kevinlee.modernjava.e06_custom_functionalinterface;

import java.math.BigDecimal;

/**
 * @author Kevin Lee
 * @since 2015-08-01
 */
public class CustomFunctionalInterfaceExamples {

  public static void main(final String[] args) {
    println(1, 2, 3,
            (i1, i2, i3) -> String.valueOf(i1 + i2 + i3));
    println("Area is ", 12, 20,
            (message, length, width) -> message + (length * width));
    println(1L, "Kevin", "test@email.com",
            (id, name, email) -> "User info: ID=" + id + ", name=" + name + ", email=" + email);

    final Function3<Integer, Integer, Integer, String> f3 = (i1, i2, i3) -> String.valueOf(i1 + i2 + i3);

    final BigDecimalToCurrency bigDecimalToCurrency = bd -> "$" + bd.toString();
    System.out.println(bigDecimalToCurrency.toCurrency(new BigDecimal("120.00")));

    final InvalidFunctionalInterface anonymousClass = new InvalidFunctionalInterface() {
      @Override
      public <T> String mkString(final T value) {
        return value.toString();
      }
    };
    System.out.println("anonymous class: " + anonymousClass.mkString(123));

//    value -> value.toString() 부분에서 에러! Target method is generic.
//    final InvalidFunctionalInterface invalidFunctionalInterface = value -> value.toString();
//    System.out.println(invalidFunctionalInterface.mkString(123));
  }

  private static <T1, T2, T3> void println(T1 t1, T2 t2, T3 t3, Function3<T1, T2, T3, String> function) {
    System.out.println(function.apply(t1, t2, t3));
  }
}

@FunctionalInterface
interface Function3<T1, T2, T3, R> {
  R apply(T1 t1, T2 t2, T3 t3);

//  void print(int i);
}

@FunctionalInterface
interface BigDecimalToCurrency {
  String toCurrency(BigDecimal value);
}

/**
 * Generic method를 가지는 FunctionalInterface는
 * Lambda Expression을 사용할수 없습니다.
 */
@FunctionalInterface
interface InvalidFunctionalInterface {
  <T> String mkString(T value);
}
