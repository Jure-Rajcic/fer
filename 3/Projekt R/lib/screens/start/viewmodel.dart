import 'package:simulacija/abstract_mvvm/app_routes.dart';
import 'package:simulacija/abstract_mvvm/view_model.abs.dart';
import 'package:rxdart/subjects.dart';
import 'package:simulacija/constants/distribution_function_type.dart';
import 'package:simulacija/constants/parameter_type.dart';
import 'package:simulacija/screens/start/model.dart';
import 'package:simulacija/util/simulation.dart';

class StartViewModel extends ViewModel {
  final _routes = PublishSubject<AppRouteSpec>();
  final _stateModel = BehaviorSubject<StartModel>.seeded(
    StartModel(
      lambda: IconModel(
        name: "lambda",
        initialized: false,
        parameterType: ParameterType.LAMBDA,
      ),
      mu: IconModel(
        name: "mu",
        initialized: false,
        parameterType: ParameterType.MU,
      ),
      capacity: IconModel(
        name: "capacity",
        initialized: false,
        parameterType: ParameterType.K,
      ),
      server: IconModel(
        name: "server",
        initialized: false,
        parameterType: ParameterType.M,
      ),
    ),
  );

  Stream<AppRouteSpec> get routes => _routes;
  Stream<StartModel> get state => _stateModel;

  void executeSetUp(IconModel iconModel) {
    if (iconModel.initialized) return;
    String route;
    switch (iconModel.parameterType) {
      case ParameterType.LAMBDA:
      case ParameterType.MU:
        route = "/select_distribution";
        break;
      case ParameterType.K:
      case ParameterType.M:
        route = "/numeric_calculator";
        break;
      default:
        throw UnimplementedError('Unimplemented route type: ');
    }
    _routes.add(
      AppRouteSpec(
        name: route,
        arguments: {
          'parameterType': iconModel.parameterType,
        },
        action: AppRouteAction.pushTo,
      ),
    );
    updateModel(iconModel);
  }

  void updateModel(IconModel iconModel) {
    iconModel.initialized = true;
    _stateModel.add(
      _stateModel.value.copyWith(),
    );
  }

  bool allParametersInitialized() {
    final models = _stateModel.value.models;
    bool allInitialized = true;
    // bool doesntNeedToCheckCapacity =
    //     QueningSimulation.m == NumericModel.maxNumber();
    for (IconModel model in models) {
      // if (doesntNeedToCheckCapacity && model.parameterType == ParameterType.K) {
      //   continue;
      // } // problem late variable K is not defined
      allInitialized = allInitialized && model.initialized;
    }
    return allInitialized;
  }

  bool isMM() =>
      QueningSimulation.lamda.distributionFunctionType ==
          DistributionFunctionType.M &&
      QueningSimulation.mu.distributionFunctionType ==
          DistributionFunctionType.M;

  void showSystemCharacteristics() {
    _routes.add(
      AppRouteSpec(
        name: '/system_characteristics',
        action: AppRouteAction.pushTo,
      ),
    );
  }

  void startSimulation() {
    _routes.add(
      AppRouteSpec(
        name: '/simulation',
        action: AppRouteAction.pushTo,
      ),
    );
  }

  @override
  void dispose() {
    _stateModel.close();
    _routes.close();
  }
}
