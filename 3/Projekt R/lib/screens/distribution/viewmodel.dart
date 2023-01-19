import 'dart:async';

import 'package:flutter/material.dart';
import 'package:math_expressions/math_expressions.dart';
import 'package:simulacija/abstract_mvvm/app_routes.dart';
import 'package:simulacija/abstract_mvvm/view_model.abs.dart';
import 'package:rxdart/subjects.dart';
import 'package:simulacija/constants/distribution_function_type.dart';
import 'package:simulacija/constants/parameter_type.dart';
import 'package:simulacija/screens/distribution/model.dart';
import 'package:simulacija/util/distribution_function_validator.dart';
import 'package:simulacija/util/interval_parser.dart';
import 'package:simulacija/util/point.dart';
import 'package:simulacija/widgets/distribution_button_frame.dart';

class DistributionViewModel extends ViewModel {
  final _routes = PublishSubject<AppRouteSpec>();
  final _stateModel = BehaviorSubject<DistributionModel>.seeded(
    DistributionModel(
      functions: [],
    ),
  );

  final ParameterType parameterType;
  DistributionViewModel({required this.parameterType});

  final ScrollController controller = ScrollController();
  void scrollDown() {
    controller.animateTo(
      controller.position.maxScrollExtent,
      duration: const Duration(seconds: 1),
      curve: Curves.fastOutSlowIn,
    );
  }

  void add() {
    _stateModel.value.functions.add(
      DistributionButtonFrame(
        vm: this,
        interval: 'a < x < b',
        fx: 'f(x)',
      ),
    );
    _refreshModel();
    Timer(const Duration(milliseconds: 50), scrollDown);
  }

  void remove() {
    final model = _stateModel.value;
    if (model.functions.isEmpty) return;
    model.functions.removeLast();
    _refreshModel();
  }

  void _refreshModel() {
    final model = _stateModel.value;
    _stateModel.add(
      model.copyWith(),
    );
  }

  bool visible = false;
  late FunctionButtonType currentType;
  void displayCalculator(FunctionButtonType type) {
    currentType = type;
    if (visible) return;
    visible = true;
    _refreshModel();
    Timer(const Duration(milliseconds: 50), scrollDown);
  }

  void closeCalculator() {
    if (!visible) return;
    visible = false;
    _refreshModel();
    Timer(const Duration(milliseconds: 50), scrollDown);
  }

  void updateCurrentText(String input) {
    DistributionButtonFrame frame = _stateModel.value.functions.last;
    switch (currentType) {
      case FunctionButtonType.INTERVAL:
        IntervalParser p = IntervalParser();
        frame.interval = p.parse(input);
        break;
      case FunctionButtonType.FX:
        Parser p = Parser();
        Expression exp = p.parse(input);
        _stateModel.value.functions.last.expression = exp;
        frame.fx = exp.toString();
        break;
      default:
        throw UnimplementedError("unknown function button type");
    }
    closeCalculator();
  }

  void showChart(List<DistributionButtonFrame> functions) {
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
          'points': points,
          'parameterType': parameterType,
          'distributionFunctionType': DistributionFunctionType.G,
          'minX': minX,
          'maxX': maxX,
        },
        action: AppRouteAction.pushTo,
      ),
    );
  }

  Stream<AppRouteSpec> get routes => _routes;
  Stream<DistributionModel> get state => _stateModel;

  @override
  void dispose() {
    _stateModel.close();
    _routes.close();
  }
}
