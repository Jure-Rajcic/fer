import 'dart:core';

import 'package:simulacija/abstract_mvvm/model.abs.dart';
import 'package:simulacija/constants/distribution_function_type.dart';
import 'package:simulacija/constants/parameter_type.dart';
import 'package:simulacija/util/point.dart';

class ChartModel extends Model {
  // final List<DistributionButtonFrame> functions;
  final DistributionFunctionType distributionFunctionType;
  final ParameterType parameterType;
  final List<Point> points;
  // final bool valid;
  final double minX;
  final double maxX;
  final int? parameter;
  // double minX = 10000.0;
  // double maxX = 0.0;

  ChartModel({
    // required this.functions,
    required this.parameterType,
    required this.distributionFunctionType,
    // required this.valid,
    required this.points,
    required this.minX,
    required this.maxX,
    required this.parameter,
  });
  // {
  //   intMinAndMaxX();
  // }

  // bool isFunctionValidDistributionFunction() {
  //   int n = ((maxX - minX) * 100).round();
  //   double increment = (maxX - minX) / n;
  //   List<Point> points = [];
  //   double sum = 0.0;
  //   for (double d = minX; d <= maxX; d += increment) {
  //     double y = functionValue(d + increment / 2);
  //     if (y < 0) return false;
  //     sum += (increment * y);
  //     points.add(Point(x: d, y: y));
  //   }
  //   this.points = points;
  //   print('treshold = ${(sum - 1).abs()}');
  //   return (sum - 1).abs() < 0.005;
  // }

  // double functionValue(double xi) {
  //   for (final def in functions) {
  //     List<String> l = def.interval.split(' ');
  //     String boolExpresion = '${l[0]} ${l[1]} $xi && $xi ${l[3]} ${l[4]}';
  //     if (boolean_logic.Expression(boolExpresion).eval().toString() == '1') {
  //       Variable x = Variable('x');
  //       ContextModel cm = ContextModel()..bindVariable(x, Number(xi));
  //       double fx = def.expression.evaluate(EvaluationType.REAL, cm);
  //       return fx;
  //     }
  //   }
  //   return 0.0;
  // }

  // void intMinAndMaxX() {
  //   for (final def in functions) {
  //     minX = min(minX, double.parse(def.interval.split(' ')[0]));
  //     maxX = max(maxX, double.parse(def.interval.split(' ')[4]));
  //   }
  // }

  @override
  ChartModel copyWith() => this;
}
