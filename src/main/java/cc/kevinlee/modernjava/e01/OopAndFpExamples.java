package cc.kevinlee.modernjava.e01;

/**
 * 이 파일 관련된 내용은 아직 영상 업로드가 안 됐습니다. 이번 주말에 업로드 하면서
 * 이 파일도 업데이트 할테니 git pull 해주세요. :)
 *
 * @author Kevin Lee
 * @since 2015-08-02
 */
public class OopAndFpExamples {

  public static void main(final String[] args) {
    final CalculatorService calculatorService = new CalculatorService(new Addition(), new Subtraction());
    final int additionResult = calculatorService.calculate(11, 4);
    System.out.println(additionResult);

    final int subtractionResult = calculatorService.calculate(11, 1);
    System.out.println(subtractionResult);

    final int multiplicationResult = calculatorService.calculate(11, 2);
    System.out.println(multiplicationResult);

    final int divisionResult = calculatorService.calculate(20, 4);
    System.out.println(divisionResult);


    final FpCalculatorService fpCalculatorService = new FpCalculatorService();
    final Calculation addition = (i1, i2) -> i1 + i2;
    System.out.println("       additon: " + fpCalculatorService.calculate(addition, 11, 4));
    System.out.println("   subtraction: " + fpCalculatorService.calculate((i1, i2) -> i1 - i2, 11, 1));
    System.out.println("multiplication: " + fpCalculatorService.calculate((i1, i2) -> i1 * i2, 11, 2));
    System.out.println("      division: " + fpCalculatorService.calculate((i1, i2) -> i1 / i2, 20, 4));
    System.out.println("   custom calc: " + fpCalculatorService.calculate((i1, i2) -> ((i1 + i2) * 2) / i2, 20, 4));
  }
}

interface Calculation {
  int calculate(final int num1, final int num2);
}

class Addition implements Calculation {
  @Override
  public int calculate(final int num1, final int num2) {
    return num1 + num2;
  }
}

class Subtraction implements Calculation {
  @Override
  public int calculate(final int num1, final int num2) {
    return num1 - num2;
  }
}

class Multiplication implements Calculation {
  @Override
  public int calculate(final int num1, final int num2) {
    return num1 * num2;
  }
}

class Division implements Calculation {
  @Override
  public int calculate(final int num1, final int num2) {
    return num1 / num2;
  }
}

class CalculatorService {
  private final Calculation calculation;
  private final Calculation calculation2;

  public CalculatorService(final Calculation calculation, final Calculation calculation2) {
    this.calculation = calculation;
    this.calculation2 = calculation2;
  }

  public int calculate(final int num1, final int num2) {
    if (num1 > 10 && num2 < num1) {
      return calculation.calculate(num1, num2);
    } else {
      throw new IllegalArgumentException("Invalid input num1: " + num1 + ", num2: " + num2);
    }
  }

  public int compute(final int num1, final int num2) {
    if (num1 > 10 && num2 < num1) {
      return calculation2.calculate(num1, num2);
    } else {
      throw new IllegalArgumentException("Invalid input num1: " + num1 + ", num2: " + num2);
    }
  }
}

class FpCalculatorService {
  public int calculate(final Calculation calculation, final int num1, final int num2) {
    if (num1 > 10 && num2 < num1) {
      return calculation.calculate(num1, num2);
    } else {
      throw new IllegalArgumentException("Invalid input num1: " + num1 + ", num2: " + num2);
    }
  }
}
