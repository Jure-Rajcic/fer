import 'dart:async';

import 'package:simulacija/abstract_mvvm/app_routes.dart';
import 'package:simulacija/abstract_mvvm/view_model.abs.dart';
import 'package:rxdart/subjects.dart';
import 'package:simulacija/constants/parameter_type.dart';
import 'package:simulacija/screens/chart/model.dart';
import 'package:simulacija/util/simulation.dart';

class ChartViewModel extends ViewModel {
  final _routes = PublishSubject<AppRouteSpec>();
  final BehaviorSubject<ChartModel> _stateModel = BehaviorSubject<ChartModel>();

  ChartViewModel({required ChartModel cm}) {
    _stateModel.add(cm);
    // Timer(
    //   const Duration(milliseconds: 500),
    //   () => {
    //     _stateModel.add(
    //       cm.copyWith(
    //         valid: cm.isFunctionValidDistributionFunction(),
    //       ),
    //     ),
    //   },
    // );
  }

  Stream<AppRouteSpec> get routes => _routes;
  Stream<ChartModel> get state => _stateModel;

  void returnToMainScreen() {
    switch (_stateModel.value.parameterType) {
      case ParameterType.LAMBDA:
        QueningSimulation.lamda = _stateModel.value;
        QueningSimulation.lamdaPoints = _stateModel.value.points;
        break;
      case ParameterType.MU:
        QueningSimulation.mu = _stateModel.value;
        QueningSimulation.muPoints = _stateModel.value.points;
        break;
      default:
        throw UnimplementedError(
          "this function type shoudnt be in dstrunution part of system",
        );
    }
    _routes.add(
      AppRouteSpec(
        name: '/',
        action: AppRouteAction.popUntil,
      ),
    );
  }

  void returnOnPageForDefiningCustomFunction() {
    _routes.add(
      AppRouteSpec(
        name: '/',
        action: AppRouteAction.pop,
      ),
    );
  }

  @override
  void dispose() {
    _stateModel.close();
    _routes.close();
  }
}
