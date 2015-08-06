package cc.kevinlee.modernjava.e01;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Kevin Lee
 * @since 2015-08-02
 */
public class CalculatorServiceTest {

  @Test
  public void testCalculateAddition() throws Exception {
    final Calculation calculation = (i1, i2) -> i1 + i2;

    final int actual = calculation.calculate(1, 1);

    assertThat(actual).isEqualTo(2);
  }

  @Test
  public void testCalculateSubtraction() throws Exception {
    final Calculation calculation = new Subtraction();

    final int actual = calculation.calculate(1, 1);

    assertThat(actual).isEqualTo(0);
  }

  @Test
  public void testCalculateMultiplication() throws Exception {
    final Calculation calculation = new Multiplication();

    final int actual = calculation.calculate(1, 1);

    assertThat(actual).isEqualTo(1);
  }

  @Test
  public void testCalculateDivision() throws Exception {
    final Calculation calculation = new Division();

    final int actual = calculation.calculate(8, 0);

    assertThat(actual).isEqualTo(2);
  }

}
