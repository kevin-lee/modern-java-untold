package cc.kevinlee.modernjava.e09_closure;

/**
 * @author Kevin Lee
 * @since 2015-09-13
 */
public class ClosureExamples {
  private int number = 999;

  public static void main(String[] args) {
    new ClosureExamples().test3();
  }


  private void test() {
    int number = 100;

    testClosure("Anonymous Class", new Runnable() {
      @Override
      public void run() {
        System.out.println(number);
      }
    });

    testClosure("Lambda Expression", () -> System.out.println(number));

  }
  private void test1() {
    int number = 100;

    testClosure("Anonymous Class", new Runnable() {
      @Override
      public void run() {
        System.out.println(ClosureExamples.this.number);
      }
    });

    testClosure("Lambda Expression", () -> System.out.println(this.number));

  }

  private void test2() {

    testClosure("Anonymous Class", new Runnable() {
      @Override
      public void run() {
        System.out.println("this.toString(): " + this.toString());
      }
    });

    testClosure("Anonymous Class", new Runnable() {
      @Override
      public void run() {
        System.out.println("ClosureExamples.this.toString(): " + ClosureExamples.this.toString());
      }
    });

    testClosure("Lambda Expression", () -> System.out.println("this.toString(): " + this.toString()));
  }

  private void test3() {

    System.out.println("\"ClosureExamples calling toString()\": " + toString());
    System.out.println("\"ClosureExamples calling toString(int, String)\": " + toString(1, "Hello"));

    testClosure("Anonymous Class", new Runnable() {
      @Override
      public void run() {
//        System.out.println("toString(int, String): " + toString(1, "Test"));
        System.out.println("toString(int, String) causes compile-time error");
        System.out.println("ClosureExamples.this.toString(int, String): " + ClosureExamples.toString(1, "Test"));
      }
    });
    testClosure("Anonymous Class", new Runnable() {
      @Override
      public void run() {
//        System.out.println("toString(int): " + toString(1));
        System.out.println("toString(int) causes compile-time error");
        System.out.println("ClosureExamples.this.toString(int, String): " + ClosureExamples.this.toString(1));
      }
    });

    testClosure("Lambda Expression", () -> System.out.println("this.toString(int, String): " + this.toString(1, "Test")));
    testClosure("Lambda Expression", () -> System.out.println("toString(int, String): " + toString(1, "Test")));
    testClosure("Lambda Expression", () -> System.out.println("this.toString(int): " + this.toString(1)));
    testClosure("Lambda Expression", () -> System.out.println("toString(int): " + toString(1)));
  }

  private void test4() {

    int number = 100;

    testClosure("Anonymous Class", new Runnable() {
      @Override
      public void run() {
        int number = 50; // no compile-time error
        System.out.println(number);
      }
    });

    testClosure("Lambda Expression", () -> {
//      int number = 50; // compile-time error
      System.out.println(number);
    });

  }

  private static void testClosure(final String name, final Runnable runnable) {
    System.out.println("===================================");
    System.out.println(name + ": ");
    runnable.run();
    System.out.println("===================================");
  }


  @Override
  public String toString() {
    return new StringBuilder("ClosureExamples{")
        .append("number=")
        .append(number)
        .append('}')
        .toString();
  }

  public String toString(int number) {
    return "#" + number;
  }

  public static <T> String toString(int number, T value) {
    return "[" + number + "] The value is " + String.valueOf(value) + ".";
  }
}
