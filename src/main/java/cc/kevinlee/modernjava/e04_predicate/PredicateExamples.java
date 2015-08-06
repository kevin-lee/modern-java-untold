package cc.kevinlee.modernjava.e04_predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Kevin Lee
 * @since 2015-08-01
 */
public class PredicateExamples {

  private static void predicateExamples() {

    final Predicate<Integer> isPositive = i -> i > 0;

    System.out.println(isPositive.test(1));
    System.out.println(isPositive.test(0));
    System.out.println(isPositive.test(-1));

    final List<Integer> numbers = Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5);

    final List<Integer> positiveNumbers = new ArrayList<>();
    for (final Integer num : numbers) {
      if (isPositive.test(num)) {
        positiveNumbers.add(num);
      }
    }
    System.out.println("positive integers: " + positiveNumbers);

    final Predicate<Integer> lessThan3 = i -> i < 3;
    final List<Integer> numbersLessThan3 = new ArrayList<>();
    for (final Integer num : numbers) {
      if (lessThan3.test(num)) {
        numbersLessThan3.add(num);
      }
    }
    System.out.println("less than 3: " + numbersLessThan3);

    System.out.println("positive integers: " + filter(numbers, isPositive));
    System.out.println("less than 3: " + filter(numbers, lessThan3));

  }

  private static <T> List<T> filter(final List<T> list, final Predicate<T> filter) {
    final List<T> result = new ArrayList<>();
    for (final T input : list) {
      if (filter.test(input)) {
        result.add(input);
      }
    }
    return result;
  }

  public static void main(final String[] args) {
    predicateExamples();
  }

}
