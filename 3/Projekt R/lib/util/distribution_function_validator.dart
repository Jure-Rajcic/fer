import 'package:simulacija/widgets/distribution_button_frame.dart';
import 'dart:math' as math;
import 'package:math_expressions/math_expressions.dart';
import 'package:eval_ex/expression.dart' as boolean_logic;

class DistributionFunctionValidator {
  static bool valid(List<DistributionButtonFrame> functions) {
    double maxX = DistributionFunctionValidator.findMaxX(functions);
    double minX = DistributionFunctionValidator.findMinX(functions);
    int n = ((maxX - minX) * 100).round();
    double increment = (maxX - minX) / n;
    double sum = 0.0;
    for (double d = minX; d <= maxX; d += increment) {
      double y = DistributionFunctionValidator.fx(d + increment / 2, functions);
      if (y < 0) return false;
      sum += increment * y;
    }
    print('treshold = ${(sum - 1).abs()}');
    print((sum - 1).abs() < 0.005);
    return (sum - 1).abs() < 0.005;
  }

  static double findMinX(List<DistributionButtonFrame> functions) {
    double minX = double.infinity;
    for (final def in functions) {
      minX = math.min(minX, double.parse(def.interval.split(' ')[0]));
    }
    return minX;
  }

  static double findMaxX(List<DistributionButtonFrame> functions) {
    double maxX = 0.0;
    for (final def in functions) {
      maxX = math.max(maxX, double.parse(def.interval.split(' ')[4]));
    }
    return maxX;
  }

  static double fx(double xi, List<DistributionButtonFrame> functions) {
    for (final def in functions) {
      List<String> l = def.interval.split(' ');
      String boolExpresion = '${l[0]} ${l[1]} $xi && $xi ${l[3]} ${l[4]}';
      if (boolean_logic.Expression(boolExpresion).eval().toString() == '1') {
        Variable x = Variable('x');
        ContextModel cm = ContextModel()..bindVariable(x, Number(xi));
        double fx = def.expression.evaluate(EvaluationType.REAL, cm);
        return fx;
      }
    }
    return 0.0;
  }
}
