import 'package:simulacija/constants/distribution_function_type.dart';
import 'package:simulacija/screens/chart/model.dart';
import 'package:simulacija/util/point.dart';

class QueningSimulation {
  static late ChartModel lamda;
  static late int? lamdaExpected;
  static late List<Point> lamdaPoints;
  static late ChartModel mu;
  static late int? muExpected;
  static late List<Point> muPoints;
  static late int k; // kapacitet
  static late int m; // br servera

}

class DistributinPair {
  final DistributionFunctionType distributionFunctionType;
  final bool? parameter;

  DistributinPair({
    required this.distributionFunctionType,
    this.parameter,
  });
}
