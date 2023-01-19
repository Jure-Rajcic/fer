import 'package:simulacija/screens/simulation%20results/model.dart';

class MM1 extends SimulationResultsModel {
  // M M 1 inf
  @override
  // TODO: implement equatios
  List<Equation> get equatios => [
        Equation(
          equation: r"<p> $$\overline{N} = {ρ \over 1 - ρ}.$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan broj jedinica u sustavu',
        ),
        Equation(
          equation: r"<p> $$T = {1 \over µ(1 - ρ)}.$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecno vrijeme koje jedinice provode u sustavu',
        ),
        Equation(
          equation: r"<p> $$\overline{x} = {1 \over µ}.$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan vrijeme posluzivanja jedinice',
        ),
        Equation(
          equation: r"<p> $$W = {ρ \over µ(1 - ρ)}.$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan vrijeme cekanja jedinice',
        ),
        Equation(
          equation: r"<p> $$\overline{N_{q}} = {ρ^{2} \over 1 - ρ}.$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan broj jedinuca u redu cekanja',
        ),
        Equation(
          equation: r"<p> $$\overline{N_{s}} = {ρ}.$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan broj jedinuca u redu cekanja',
        ),
      ];
}
