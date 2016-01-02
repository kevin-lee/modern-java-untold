package cc.kevinlee.modernjava.ep11_method_reference;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Kevin Lee
 * @since 2015-11-01
 */
public class MethodReferenceExamples {
  public static void main(String[] args) {
    Arrays.asList(1, 2, 3, 4, 5)
        .forEach(System.out::println);
//          .forEach(i -> System.out.println(i));

    System.out.println(
        Arrays.asList(new BigDecimal("10.0"), new BigDecimal("23"), new BigDecimal("5"))
            .stream()
            .sorted(BigDecimalUtil::compare)
//            .sorted((bd1, bd2) -> bd1.compareTo(bd2))
            .collect(toList())
    );

    System.out.println(
        Arrays.asList(new BigDecimal("10.0"), new BigDecimal("23"), new BigDecimal("5"))
            .stream()
            .sorted(BigDecimal::compareTo)
//            .sorted((bd1, bd2) -> bd1.compareTo(bd2))
            .collect(toList())
    );


    System.out.println("\nThe following three cases have the same result.");
    System.out.println("----------------------------------------------------");
    final List<String> abcdList = Arrays.asList("a", "b", "c", "d");
    final String targetString = "c";
    System.out.println("list: " + abcdList);
    System.out.println("targetString: \"c\"");
    System.out.println("\nanyMatch(targetString::equals)\n" +
        abcdList
            .stream()
            .anyMatch(targetString::equals)
    );
    System.out.println("\nanyMatch(\"c\"::equals)\n" +
        abcdList
            .stream()
            .anyMatch("c"::equals)
    );
    System.out.println("\nanyMatch(x -> x.equals(\"c\"))\n" +
        abcdList
            .stream()
            .anyMatch(x -> x.equals("c"))
    );

  }
}

class BigDecimalUtil {
  public static int compare(BigDecimal bd1, BigDecimal bd2) {
    return bd1.compareTo(bd2);
  }
}
