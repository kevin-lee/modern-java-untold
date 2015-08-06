package cc.kevinlee.modernjava.e01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Kevin Lee
 * @since 2015-08-01
 */
public class LambdaExample {

  public static void main(final String[] args) {

    filteringTest();

    final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

    final int repeat = 5;
    System.out.println("\nLambdaExample.raceCondition");
    /* 5번 반복 */
    Stream.iterate(0, i -> i + 1)  // <= 이게 뭐냐구요? 5번 반복인데, 자세한건 Stream API 관련 영상을 기다려주세요!
          .limit(repeat)
          .forEach(i -> raceCondition(numbers));

    System.out.println("\nLambdaExample.noRaceCondition");
    Stream.iterate(0, i -> i + 1)    // <= 이게 뭐냐구요? 5번 반복인데, 자세한건 Stream API 관련 영상을 기다려주세요!
          .limit(repeat)
          .forEach(i -> noRaceCondition(numbers));

    parallelProgramming();
  }

  private static void filteringTest() {
    System.out.println("\nLambdaExample.filteringTest");
    final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    /* 예전 방법으로 2보다 큰 정수 찾기 */
    final List<Integer> result = new ArrayList<>(); // boilerplate code
    for (final Integer number : list) { // boilerplate code
      if (number > 2) { // 이부분만 다름
        result.add(number); // boilerplate code
      }
    }
    System.out.println("n > 2 = " + result);

    /* 예전 방법으로 7보다 작은 정수 찾기 */
    final List<Integer> result2 = new ArrayList<>(); // boilerplate code
    for (final Integer number : list) { // boilerplate code
      if (number < 7) { // 이부분만 다름
        result2.add(number); // boilerplate code
      }
    }
    System.out.println("n < 7 = " + result2);

    /* 람다를 이용해서 2보다 큰 정수 찾기 */
    final Predicate<Integer> greaterThan2 = n -> n > 2;
    final List<Integer> result3 = filter(list, greaterThan2);
    System.out.println("n > 2 = " + result3);

    /* 람다를 이용해서 7보다 작은 정수 찾기 (기존 filter 메소드 재사용) */
    final Predicate<Integer> lessThan7 = n -> n < 7;
    final List<Integer> result4 = filter(list, lessThan7);
    System.out.println("n < 7 = " + result4);

    /* Function composition:
     * 2개의 함수를 합쳐서 쉽게
     * 2보다 크고 7보다 작은 정수 찾기
     */
    final List<Integer> result5 = filter(list, greaterThan2.and(lessThan7));
    System.out.println("2 < n < 7 = " + result5);

    /* Closure: 람다 바디에서 람다 바깥에 있는 factor (free variable) 접근
     * Note: 엄밀히 따지면 자바의 Closure는 variable이 아니라 거기 들은 값(value)에
     * 접근 하는겁니다 (capturing value).
     */
    int factor = 10; // effectively final
    final Comparator<Integer> comparator = (o1, o2) -> o1 > factor ? o1 : o1.compareTo(o2);

  }

  private static <T> List<T> filter(final List<T> list, final Predicate<T> predicate) {
    final List<T> result = new ArrayList<>();
    for (final T value : list) {
      if (predicate.test(value)) { // predicate의 test를 통과한 경우만 저장
        result.add(value);
      }
    }
    return result;
  }


  private static void raceCondition(final List<Integer> numbers) {

    /* Race condition */
    final int[] sum = new int[1];
    numbers.parallelStream()
           .forEach(i -> sum[0] = sum[0] + i); // mutation!

    System.out.println("race condition: " + sum[0]);
  }

  private static void noRaceCondition(final List<Integer> numbers) {
    /* No race condition */
    final int total = numbers.parallelStream()
                             .reduce(0, (i1, i2) -> i1 + i2);
    System.out.println("no race condition: " + total);
  }

  private static void parallelProgramming() {
    System.out.println("\nLambdaExample.parallelProgramming");
    final long start = System.currentTimeMillis();
    /*
     * peek에 넘기는 function은 3초가 걸리는
     * 연산을 시뮬레이션한 것입니다.
     *
     * 코어 4개짜리 CPU에서 3초정도 걸립니다.
     * 저장된 숫자를 하나 더 늘리면 (1, 2, 3, 4, 5),
     * 처리하는데 8초정도 걸립니다.
     *
     * 저장된 숫자의 갯수를 CPU코어 만큼 늘려서 테스트 해보시고,
     * 코어보다 하나 많게 해서 테스트 해보세요.
     * 예) 코어가 8개면
     * (1, 2, 3, 4, 5, 6, 7, 8) => 3초정도 걸립니다.
     * (1, 2, 3, 4, 5, 6, 7, 8, 9) => 6초정도 걸립니다.
     */
    final int total = Arrays.asList(1, 2, 3, 4)
        .parallelStream()
        .peek(i -> {
          // 처리 시간 오래 걸리는 함수 시뮬레이션
          try {
            TimeUnit.SECONDS.sleep(3);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        })
        .reduce(0, (i1, i2) -> i1 + i2);
    System.out.println("It took " + ((System.currentTimeMillis() - start) / 1000) + " seconds.");
    System.out.println("total: " + total);
  }

}
