import 'dart:async';

import 'package:simulacija/abstract_mvvm/app_routes.dart';
import 'package:rxdart/subjects.dart';
import 'package:simulacija/abstract_mvvm/view_model.abs.dart';
import 'package:simulacija/rjesenje.dart';
import 'package:simulacija/screens/simulation/model.dart';
import 'package:simulacija/util/simulation.dart';
import 'package:simulacija/widgets/histogram.dart';

class SimulationViewModel extends ViewModel {
  final _routes = PublishSubject<AppRouteSpec>();
  final _stateModel =
      BehaviorSubject<SimulationModel>.seeded(SimulationModel());
  int dataSize = 0;
  final queueSystem = QueueSystem(
    queueCapacity: QueningSimulation.k,
    numServers: QueningSimulation.m,
  );
  int counter = 0;
  bool simulationIsRuningLive = false; // REFACTOR NAME

  Stream<AppRouteSpec> get routes => _routes;
  Stream<SimulationModel> get state => _stateModel;

  void refreshModel() => _stateModel.add(_stateModel.value.copyWith());

  void playLiveSimulation() {
    if (simulationIsRuningLive) return;
    simulationIsRuningLive = true;

    Timer.periodic(const Duration(milliseconds: 1000), (timer) {
      if (!simulationIsRuningLive) {
        timer.cancel();
        return;
      }
      queueSystem.simulate();
      counter++;
      _stateModel.value.N.add(SimulationData(
        queueSystem.currentTime,
        queueSystem.numCustomersInQueue,
      ));
      refreshModel();
    });
  }

  void pauseLiveSimulation() {
    simulationIsRuningLive = false;
    refreshModel();
  }

  void restartLiveSimulation() {
    counter = 0;
    queueSystem.reset();
    _stateModel.value.N.clear();
    pauseLiveSimulation();
  }

  void showSimulationResults() {
    if (simulationIsRuningLive) return;
    _routes.add(
      AppRouteSpec(
        // name: '/simulation_results',
        name: '/system_characteristics',
        action: AppRouteAction.pushTo,
      ),
    );
  }

  @override
  void dispose() {
    _routes.close();
    _stateModel.close();
  }
}

class Pair<T, U> {
  final T first;
  U second;

  Pair(this.first, this.second);

  @override
  bool operator ==(Object other) {
    if (other is! Pair<T, U>) {
      return false;
    }
    if (first is double && other.first is double) {
      return ((first as double) - (other.first as double)).abs() < 0.00001;
    }
    return other.first == first && other.second == second;
  }

  @override
  int get hashCode => first.hashCode + second.hashCode;

  @override
  String toString() => '$first: $second';
}
