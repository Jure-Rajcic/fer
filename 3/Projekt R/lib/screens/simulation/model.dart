import 'package:simulacija/abstract_mvvm/model.abs.dart';
import 'package:simulacija/widgets/histogram.dart';

class SimulationModel extends Model {
  // final List<String> parameters = ['N', 'Ns'];
  // late String parameterValue;

  final List<SimulationData> N = [];
  // final List<SimulationData> Nq = [];
  // final List<SimulationData> Ns = [];

  // double min = 0.0;
  // double max = 0.0;

  // SimulationModel() {
  //   parameterValue = parameters[0];
  // }

  @override
  SimulationModel copyWith() {
    if (N.length > 10) {
      N.removeAt(0);
    }
    return this;
  }
}
