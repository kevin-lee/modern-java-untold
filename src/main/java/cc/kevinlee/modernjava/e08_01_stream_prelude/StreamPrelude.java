package cc.kevinlee.modernjava.e08_01_stream_prelude;

/**
 * @author Kevin Lee
 * @since 2015-08-15
 */
public class StreamPrelude {
  public static void main(String[] args) {
    /**
     * 다른 입력값, 같은 결과값
     * 입력: -1, 1
     * 결과: 1
     */
    final int abs1 = Math.abs(-1);
    final int abs2 = Math.abs(1);

    System.out.println("abs1: " + abs1);
    System.out.println("abs2: " + abs2);
    System.out.println("abs1 == abs2 is " + (abs1 == abs2));

    /**
     * 입력값: -2147483648
     * 결과값: -2147483648
     * WARNING!: 사용하실때 주의하세요!
     */
    System.out.println(Integer.MIN_VALUE);
    System.out.println(Integer.MAX_VALUE);
    final int minInt = Math.abs(Integer.MIN_VALUE);
    System.out.println("minInt: " + minInt);
  }
}
