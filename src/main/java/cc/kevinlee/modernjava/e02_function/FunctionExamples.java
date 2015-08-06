package cc.kevinlee.modernjava.e02_function;

import java.util.function.Function;

/**
 * @author Kevin Lee
 * @since 2015-08-01
 */
public class FunctionExamples {

  private static void functionExamples() {

    final Function<String, Integer> toInt = value -> Integer.parseInt(value);

    final Integer number = toInt.apply("100");
    System.out.println(number);

    final Function<Integer, Integer> identity = Function.identity();
    final Function<Integer, Integer> identity2 = t -> t;

    System.out.println(identity.apply(999));
    System.out.println(identity2.apply(999));
  }


  public static void main(final String[] args) {
    functionExamples();
  }

}
