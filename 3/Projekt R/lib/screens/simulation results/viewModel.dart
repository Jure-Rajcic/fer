import 'dart:math';

import 'package:simulacija/abstract_mvvm/app_routes.dart';
import 'package:simulacija/abstract_mvvm/view_model.abs.dart';
import 'package:rxdart/subjects.dart';
import 'package:simulacija/constants/distribution_function_type.dart';
import 'package:simulacija/screens/numeric/viewmodel.dart';
import 'package:simulacija/screens/simulation%20results/model.dart';
import 'package:simulacija/screens/simulation%20results/models/MM1.dart';
import 'package:simulacija/screens/simulation%20results/models/MM1K.dart';
import 'package:simulacija/screens/simulation%20results/models/MMm.dart';
import 'package:simulacija/screens/simulation%20results/models/MMmK.dart';
import 'package:simulacija/screens/simulation%20results/models/MMmm.dart';
import 'package:simulacija/util/simulation.dart';

class SimulationResultsViewModel extends ViewModel {
  final _routes = PublishSubject<AppRouteSpec>();
  final _stateModel = BehaviorSubject<SimulationResultsModel>();

  SimulationResultsViewModel() {
    final lambda = QueningSimulation.lamda.distributionFunctionType;
    final mu = QueningSimulation.mu.distributionFunctionType;
    final m = QueningSimulation.m;
    final k = QueningSimulation.k;
    final inf = pow(10, NumericViewModel.MAX_NUMBER_OF_DIGITS).toInt();
    SimulationResultsModel model;
    if (lambda == DistributionFunctionType.M &&
        mu == DistributionFunctionType.M) {
      if (m == 1) {
        if (k == inf) {
          model = MM1();
        } else {
          model = MM1K();
        }
      } else {
        // m = m
        if (k == inf) {
          model = MMm();
        } else if (k == m) {
          model = MMmm();
        } else {
          model = MMmK();
        }
      }
    } else {
      throw UnimplementedError('Currently only suporting M distribution');
    }
    _stateModel.add(model);
  }

  Stream<AppRouteSpec> get routes => _routes;
  Stream<SimulationResultsModel> get state => _stateModel;

  void returnToMainScreen() {
    _routes.add(
      AppRouteSpec(
        name: '/',
        action: AppRouteAction.popUntilRoot,
      ),
    );
  }

  @override
  void dispose() {
    _stateModel.close();
    _routes.close();
  }
}
