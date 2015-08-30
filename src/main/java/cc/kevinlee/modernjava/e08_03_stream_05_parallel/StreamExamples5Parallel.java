package cc.kevinlee.modernjava.e08_03_stream_05_parallel;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

/**
 * @author Kevin Lee
 * @since 2015-08-23
 */
public class StreamExamples5Parallel {


  public static void main(String[] args) {

    raceConditionTest();

    /*
     * 사용중인 컴퓨터의 코어 개수를 찾는 코드입니다.
     */
    int numOfCores = Runtime.getRuntime().availableProcessors();

    streamTest(numOfCores);

    parallelStreamTest(numOfCores);
    parallelStreamTest(numOfCores + 1);

    /**
     * System.setProperty로 parallelism을 설정할 경우 몇 가지 문제가 있을 수 있습니다.
     * 1. JVM레벨 설정이므로 예기치 못한곳에서 원치 않는 코어 사용/비사용 문제가 발생할수 있습니다.
     * 2. System.setProperty로 parallelism을 설정하기 전에 Parallel Stream 을 사용할 경우
     *    setProperty로 설정한 값의 영향을 받지 않는것을 확인 했습니다. 아무래도 이미 생성된 ForkJoinPool을 내부적으로
     *    그대로 사용하기 때문인것 같습니다.
     * 3. 결국 JVM 실행시 옵션으로 설정하시는것을 권장합니다.
     *   e.g.) java -D java.util.concurrent.ForkJoinPool.common.parallelism=3 some.package.MainClassName
     */
//    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(numOfCores - 1));
    parallelStreamTest(numOfCores);
  }


  /**
   * Race Condition을 보여주는 코드입니다.
   * Parallel Programming을 할경우 side-effect, 이 예제의 경우, variable의 값을
   * 계속 re-assign하는 방법을 이용하게 되면 Race Condition이 발생하기 쉽습니다.
   */
  private static void raceConditionTest() {
    System.out.println("\n=========================================");
    System.out.println("StreamExamples5Parallel.raceConditionTest");

    final int[] sum = {0};
    IntStream.range(0, 100)
        .forEach(i -> sum[0] += i);

    System.out.println("            stream sum (side-effect): " + sum[0]);
    final int[] sum2 = {0};
    IntStream.range(0, 100)
        .parallel()
        .forEach(i -> sum2[0] += i);

    System.out.println("          parallel sum (side-effect): " + sum2[0]);

    System.out.println("         stream sum (no side-effect): " +
        IntStream.range(0, 100)
            .sum());

    System.out.println("parallel stream sum (no side-effect): " +
        IntStream.range(0, 100)
            .parallel()
            .sum());
  }


  private static void streamTest(int numOfCores) {

    System.out.println("\n==================================");
    System.out.println("StreamExamples5Parallel.streamTest");
    final List<Integer> numbers = getNumbers(numOfCores);
    System.out.println("Stream (" + numbers.size() + " elements)");
    final long start = System.currentTimeMillis();
    numbers
        .stream()
        .map(i -> {
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          return i;
        })
        .forEach(i -> System.out.println(i));
    System.out.println(System.currentTimeMillis() - start);

  }

  public void test(int num) {
    System.out.println("System.setProperty(\"java.util.concurrent.ForkJoinPool.common.parallelism\", \"" + num +
        "\"): " + System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(num)));

    parallelStreamTest(8);
  }

  private static void parallelStreamTest(int numOfCores) {

    System.out.println("\n==========================================");
    System.out.println("StreamExamples5Parallel.parallelStreamTest");
    final List<Integer> numbers = getNumbers(numOfCores);

    System.out.println("Parallel Stream (" + numbers.size() + " elements)");
    final long start2 = System.currentTimeMillis();
    numbers.parallelStream()
        .map(i -> {
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          return i;
        })
        .forEach(i -> System.out.println(i));
    System.out.println(System.currentTimeMillis() - start2);
  }

  private static List<Integer> getNumbers(final int howMany) {
    return IntStream.rangeClosed(1, howMany)
        .mapToObj(i -> i) // auto-boxing을 통해서 int 가 Integer로 자동변환 됩니다.
        .collect(toList());
  }
}
