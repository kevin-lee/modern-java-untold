package cc.kevinlee.modernjava.e08_03_stream_05_parallel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Kevin Lee
 * @since 2015-08-23
 */
public class StreamExamples5ParallelPerformancePractical {
  private static final String[] priceStrings = {"1.0", "100.99", "35.75", "21.30", "88.00"};
  private static final BigDecimal[] targetPrices = {new BigDecimal("30"), new BigDecimal("20"), new BigDecimal("31")};
  private static final Random random = new Random(123);
  private static final Random targetPriceRandom = new Random(111);

  private static final List<Product> products;

  static {
    final int length = 8_000_000;
    final Product[] list = new Product[length];

    for (int i = 1; i <= length; i++) {
      list[i - 1] = new Product((long) i, "Product" + i, new BigDecimal(priceStrings[random.nextInt(5)]));
    }
    products = Collections.unmodifiableList(Arrays.asList(list));
  }

  private static BigDecimal imperativeSum(final List<Product> products, final Predicate<Product> predicate) {
    BigDecimal sum = BigDecimal.ZERO;
    for (final Product product : products) {
      if (predicate.test(product)) {
        sum = sum.add(product.getPrice());
      }
    }
    return sum;
  }

  private static BigDecimal streamSum(final Stream<Product> stream, final Predicate<Product> predicate) {
    return stream.filter(predicate).map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private static void imperativeTest(final BigDecimal targetPrice, final boolean printResult) {
    /**
     * Benchmark 코드라서 결코 일반 앱 개발등에 쓰기 좋은 코딩 스타일로 작성된것이 아닙니다.
     * (Stream사용 이외의) 이런 코드 작성을 절대 권장하지 않습니다.
     */
    if (printResult) {
      System.out.println("============================================");
      System.out.println("\nImperative Sum\n--------------------------------------------");
    }
    final long start = System.currentTimeMillis();
    final BigDecimal result = imperativeSum(products, product -> product.getPrice().compareTo(targetPrice) >= 0);
    final long howLong = System.currentTimeMillis() - start;
    if (printResult) {
      System.out.println("Sum: " + result);
      System.out.println("It took " + howLong + " ms.");
      System.out.println("============================================");
    }
  }

  private static void streamTest(final BigDecimal targetPrice, final boolean printResult) {
    /**
     * Benchmark 코드라서 결코 일반 앱 개발등에 쓰기 좋은 코딩 스타일로 작성된것이 아닙니다.
     * (Stream사용 이외의) 이런 코드 작성을 절대 권장하지 않습니다.
     */
    if (printResult) {
      System.out.println("============================================");
      System.out.println("\nStream Sum\n--------------------------------------------");
    }
    final long start = System.currentTimeMillis();
    final BigDecimal result = streamSum(products.stream(), product -> product.getPrice().compareTo(targetPrice) >= 0);
    final long howLong = System.currentTimeMillis() - start;
    if (printResult) {
      System.out.println("Sum: " + result);
      System.out.println("It took " + howLong + " ms.");
      System.out.println("============================================");
    }
  }

  private static void parallelStreamTest(final BigDecimal targetPrice, final boolean printResult) {
    /**
     * Benchmark 코드라서 결코 일반 앱 개발등에 쓰기 좋은 코딩 스타일로 작성된것이 아닙니다.
     * (Stream사용 이외의) 이런 코드 작성을 절대 권장하지 않습니다.
     */
    if (printResult) {
      System.out.println("============================================");
      System.out.println("\nParallel Stream Sum\n--------------------------------------------");
    }
    final long start = System.currentTimeMillis();
    final BigDecimal result = streamSum(products.parallelStream(), product -> product.getPrice().compareTo(targetPrice) >= 0);
    final long howLong = System.currentTimeMillis() - start;
    if (printResult) {
      System.out.println("Sum: " + result);
      System.out.println("It took " + howLong + " ms.");
      System.out.println("============================================");
    }
  }

  public static void main(String[] args) {
    /**
     * Benchmark 코드라서 결코 일반 앱 개발등에 쓰기 좋은 코딩 스타일로 작성된것이 아닙니다.
     * (Stream사용 이외의) 이런 코드 작성을 절대 권장하지 않습니다.
     */
    test1();
    test2();
    test3();
  }

  private static void test1() {

    final BigDecimal targetPrice = new BigDecimal("40");

    imperativeTest(targetPrice, false);
    streamTest(targetPrice, false);
    parallelStreamTest(targetPrice, false);

    System.out.println("\n\n================================================================\nTest1 Starts!");
    for (int i = 0; i < 5; i++) {
      BigDecimal price = targetPrices[targetPriceRandom.nextInt(3)];

      imperativeTest(price, true);
      streamTest(price, true);
      parallelStreamTest(price, true);
    }
  }

  private static void test2() {

    final BigDecimal targetPrice = new BigDecimal("40");

    parallelStreamTest(targetPrice, false);
    imperativeTest(targetPrice, false);
    streamTest(targetPrice, false);

    System.out.println("\n\n================================================================\nTest2 Starts!");
    for (int i = 0; i < 5; i++) {
      BigDecimal price = targetPrices[targetPriceRandom.nextInt(3)];

      parallelStreamTest(price, true);
      imperativeTest(price, true);
      streamTest(price, true);
    }
  }

  private static void test3() {

    final BigDecimal targetPrice = new BigDecimal("40");

    streamTest(targetPrice, false);
    parallelStreamTest(targetPrice, false);
    imperativeTest(targetPrice, false);

    System.out.println("\n\n================================================================\nTest3 Starts!");
    for (int i = 0; i < 5; i++) {
      BigDecimal price = targetPrices[targetPriceRandom.nextInt(3)];

      streamTest(price, true);
      parallelStreamTest(price, true);
      imperativeTest(price, true);
    }
  }

}

@AllArgsConstructor
@Data
class Product {
  private Long id;
  private String name;
  private BigDecimal price;
}
