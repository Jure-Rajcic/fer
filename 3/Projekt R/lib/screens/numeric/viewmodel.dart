import 'dart:async';

import 'package:simulacija/abstract_mvvm/app_routes.dart';
import 'package:simulacija/abstract_mvvm/view_model.abs.dart';
import 'package:rxdart/subjects.dart';
import 'package:simulacija/constants/parameter_type.dart';
import 'package:simulacija/screens/numeric/model.dart';
import 'package:simulacija/util/simulation.dart';

class NumericViewModel extends ViewModel {
  static const int MAX_NUMBER_OF_DIGITS = 4;
  final _routes = PublishSubject<AppRouteSpec>();
  final _stateModel = BehaviorSubject<NumericModel>();

  NumericViewModel({required NumericModel nm}) {
    _stateModel.add(nm);
  }

  bool locked = true;

  void keyPressed(String key) {
    locked = true;
    final model = _stateModel.value;
    if (key == "del") {
      if (model.numbers.isEmpty) return;
      model.numbers.removeLast();
    } else {
      if (model.numbers.contains("inf")) return;
      if (key == "inf" || model.numbers.length == MAX_NUMBER_OF_DIGITS) {
        // if (model.type == ParameterType.M) return;
        model.numbers.clear();
        model.numbers.add("inf");
      } else {
        if (key == '0' && model.numbers.isEmpty) return;
        model.numbers.add(key);
      }
    }
    _refreshModel();
  }

  void unlock() {
    if (_stateModel.value.number() == 0) return;
    locked = false;
    _refreshModel();
  }

  void _refreshModel() {
    final model = _stateModel.value;
    _stateModel.add(
      model.copyWith(),
    );
  }

  void returnToMainScreen() {
    switch (_stateModel.value.type) {
      case ParameterType.K:
        QueningSimulation.k = _stateModel.value.number();

        break;
      case ParameterType.M:
        QueningSimulation.m = _stateModel.value.number();
        break;
      default:
        throw UnimplementedError(
          "this function type shoudnt be in numeric part of system",
        );
    }
    _routes.add(
      AppRouteSpec(
        name: '/',
        action: AppRouteAction.popUntil,
      ),
    );
  }

  Stream<AppRouteSpec> get routes => _routes;
  Stream<NumericModel> get state => _stateModel;

  @override
  void dispose() {
    _stateModel.close();
    _routes.close();
  }
}
