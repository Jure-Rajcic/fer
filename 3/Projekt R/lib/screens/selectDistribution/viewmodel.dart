import 'dart:async';

import 'package:math_expressions/math_expressions.dart';
import 'package:simulacija/abstract_mvvm/app_routes.dart';
import 'package:rxdart/subjects.dart';
import 'package:simulacija/abstract_mvvm/view_model.abs.dart';
import 'package:simulacija/constants/distribution_function_type.dart';
import 'package:simulacija/constants/parameter_type.dart';
import 'package:simulacija/screens/distribution/viewmodel.dart';
import 'package:simulacija/screens/selectDistribution/model.dart';
import 'package:simulacija/util/distribution_function_validator.dart';
import 'package:simulacija/util/point.dart';
import 'package:simulacija/widgets/distribution_button_frame.dart';

class SelectDistributionViewModel extends ViewModel {
  final _routes = PublishSubject<AppRouteSpec>();
  final _stateModel = BehaviorSubject<SelectDistributionModel>.seeded(
    SelectDistributionModel(),
  );

  final ParameterType parameterType;
  SelectDistributionViewModel({required this.parameterType});

  late int parameter;
  bool inputValid(String input) {
    int? parameter = int.tryParse(input);
    if (parameter == null || parameter <= 0) return false;
    this.parameter = parameter;
    return true;
  }

  void showExponentialGraph() {
    final dis = DistributionButtonFrame(
      fx: '$parameter * (e^(-$parameter * x))',
      interval: '0 < x <= 100',
      vm: DistributionViewModel(
        parameterType: parameterType,
      ),
    );
    dis.expression = Parser().parse(dis.fx);
    print(dis.expression);
    List<DistributionButtonFrame> functions = [dis];
    double maxX = DistributionFunctionValidator.findMaxX(functions);
    double minX = DistributionFunctionValidator.findMinX(functions);
    List<Point> points = [];
    int n = ((maxX - minX) * 100).round();
    double increment = (maxX - minX) / n;
    for (double d = minX; d <= maxX; d += increment) {
      double y = DistributionFunctionValidator.fx(d + increment / 2, functions);
      points.add(Point(x: d, y: y));
    }
    _routes.add(
      AppRouteSpec(
        name: '/chart',
        arguments: {
          'parameterType': parameterType,
          'distributionFunctionType': DistributionFunctionType.M,
          'parameter': parameter,
          'points': points,
          'minX': minX,
          'maxX': 0.0,
        },
        action: AppRouteAction.pushTo,
      ),
    );
  }

  void showPageForDefiningCustomFunction() {
    _routes.add(
      AppRouteSpec(
        name: '/distribution_calculator',
        arguments: {
          'parameterType': parameterType,
        },
        action: AppRouteAction.pushTo,
      ),
    );
  }

  Stream<AppRouteSpec> get routes => _routes;
  Stream<SelectDistributionModel> get state => _stateModel;

  @override
  void dispose() {
    _routes.close();
    _stateModel.close();
  }
}
