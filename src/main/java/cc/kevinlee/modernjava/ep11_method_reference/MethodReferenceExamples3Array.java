package cc.kevinlee.modernjava.ep11_method_reference;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author Kevin Lee
 * @since 2016-01-17
 */
public class MethodReferenceExamples3Array {
  public static void main(String[] args) {
    final String[] array = new String[5];
    System.out.println(array.length);

    final Function<Integer, String[]> arrayFactoryLe = i -> new String[i];
    final String[] arrayCreatedByLambdaExpression = arrayFactoryLe.apply(7);
    System.out.println(arrayCreatedByLambdaExpression.length);

    final Function<Integer, String[]> arrayFactoryMr = String[]::new;
    final String[] arrayCreatedByMethodReference = arrayFactoryMr.apply(10);
    System.out.println(arrayCreatedByMethodReference.length);

    final IntFunction<String[]> arrayFactoryMr2 = String[]::new;
    final String[] arrayCreatedByMethodReference2 = arrayFactoryMr2.apply(11);
    System.out.println(arrayCreatedByMethodReference2.length);

    final Integer[] integers = Stream.of(1, 2, 3, 4, 5)
                                     .map(i -> i * 2)
                                     .toArray(Integer[]::new);
    System.out.println(Arrays.toString(integers));

    final String[] strings = Stream.of("A", "B", "C")
                                   .toArray(String[]::new);
//                                   .toArray(i -> new String[i]);
    System.out.println(Arrays.toString(strings));

    final List<Integer> integers2 = Arrays.asList(1, 2, 3, 4, 5);

    final Integer[] integerArray2 = integers2.toArray(new Integer[0]);
    System.out.println(Arrays.toString(integerArray2));

//    final String[] stringArray2 = integers2.toArray(new String[0]); // no compile-time error
//    System.out.println(Arrays.toString(stringArray2)); // runtime error

//    final List<String> ss = integers2.stream().collect(toList()); // compile-time error

  }
}
