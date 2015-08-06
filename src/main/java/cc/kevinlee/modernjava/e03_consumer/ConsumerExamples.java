package cc.kevinlee.modernjava.e03_consumer;

import java.util.function.Consumer;

/**
 * @author Kevin Lee
 * @since 2015-08-01
 */
public class ConsumerExamples {

  private static void consumerExamples() {

    final Consumer<String> print = value -> System.out.println(value);
    print.accept("Hello");

    final Consumer<String> greetings = value -> System.out.println("Hello " + value);
    greetings.accept("World");
    greetings.accept("Kevin");

  }

  public static void main(final String[] args) {
    consumerExamples();
  }

}
