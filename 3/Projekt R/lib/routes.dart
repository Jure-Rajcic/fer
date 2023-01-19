import 'package:flutter/material.dart';
import 'package:simulacija/constants/distribution_function_type.dart';
import 'package:simulacija/constants/parameter_type.dart';
import 'package:simulacija/screens/chart/model.dart';
import 'package:simulacija/screens/chart/viewmodel.dart';
import 'package:simulacija/screens/chart/view.dart';
import 'package:simulacija/screens/distribution/view.dart';
import 'package:simulacija/screens/distribution/viewmodel.dart';
import 'package:simulacija/screens/numeric/model.dart';
import 'package:simulacija/screens/numeric/view.dart';
import 'package:simulacija/screens/numeric/viewmodel.dart';
import 'package:simulacija/screens/selectDistribution/view.dart';
import 'package:simulacija/screens/selectDistribution/viewmodel.dart';
import 'package:simulacija/screens/simulation%20results/view.dart';
import 'package:simulacija/screens/simulation%20results/viewModel.dart';
import 'package:simulacija/screens/simulation/view.dart';
import 'package:simulacija/screens/simulation/viewmodel.dart';
import 'package:simulacija/screens/start/view.dart';
import 'package:simulacija/screens/start/viewmodel.dart';
import 'package:simulacija/screens/system_characteristics/view.dart';
import 'package:simulacija/screens/system_characteristics/viewModel.dart';
import 'package:simulacija/util/point.dart';

class AppRouter {
  Route<dynamic>? route(RouteSettings settings) {
    final arguments = settings.arguments as Map<String, dynamic>? ?? {};
    switch (settings.name) {
      // TODO dodat neke opcionalne arumente kako i zaminia staticku klasu quening model i omoguia ponovno inicjaliziranje
      case '/':
        return MaterialPageRoute(
          settings: settings,
          builder: (_) => StartView(
            viewModel: StartViewModel(),
          ),
        );
      case '/select_distribution':
        return MaterialPageRoute(
          settings: settings,
          builder: (_) => SelectDistributionView(
            viewModel: SelectDistributionViewModel(
              parameterType: arguments['parameterType'] as ParameterType,
            ),
          ),
        );
      case '/distribution_calculator':
        return MaterialPageRoute(
          settings: settings,
          builder: (_) => DistributionView(
            viewModel: DistributionViewModel(
              parameterType: arguments['parameterType'] as ParameterType,
            ),
          ),
        );
      case '/numeric_calculator':
        return MaterialPageRoute(
          settings: settings,
          builder: (_) => NumericView(
            viewModel: NumericViewModel(
              nm: NumericModel(
                numbers: [],
                type: arguments['parameterType'] as ParameterType,
              ),
            ),
          ),
        );
      case '/simulation':
        return MaterialPageRoute(
          settings: settings,
          builder: (_) => SimulationView(
            viewModel: SimulationViewModel(),
          ),
        );
      case '/chart':
        return MaterialPageRoute(
          settings: settings,
          builder: (_) => ChartView(
            viewModel: ChartViewModel(
              cm: ChartModel(
                parameterType: arguments['parameterType'] as ParameterType,
                distributionFunctionType: arguments['distributionFunctionType']
                    as DistributionFunctionType,
                points: arguments['points'] as List<Point>,
                minX: arguments['minX'] as double,
                maxX: arguments['maxX'] as double,
                parameter: arguments['parameter'] as int?,
              ),
            ),
          ),
        );
      case '/system_characteristics':
        return MaterialPageRoute(
          settings: settings,
          builder: (_) => SystemCharacteristicsView(
            viewModel: SystemCharacteristicsViewModel(),
          ),
        );
      case '/simulation_results':
        return MaterialPageRoute(
          settings: settings,
          builder: (_) => SimulationResultsView(
            viewModel: SimulationResultsViewModel(),
          ),
        );

      default:
        throw Exception('Route ${settings.name} not implemented');
    }
  }
}
