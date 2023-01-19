import 'package:simulacija/abstract_mvvm/model.abs.dart';

abstract class SimulationResultsModel extends Model {
  List<Equation> get equatios;

  // List<Equation> data = [
  //   Equation(
  //     equation: r"<p> $$E(Y) = {1 \over λ}.$$ </p>",
  //     solution: 0.2,
  //     // solution: 1.0 / QueningSimulation.lamda.parameter!,
  //     description:
  //         'Y predstavlja slučajno vrijeme između dva uzastopna zahtjeva te ova jednadzba predstavlja intenzitet dolazaka',
  //   ),
  //   Equation(
  //     equation: r"<p> $$E(Z) = {1 \over µ}.$$ </p>",
  //     solution: 0.2,
  //     // solution: 1.0 / QueningSimulation.mu.parameter!,
  //     description:
  //         'Z označava vrijeme obrade jednog zahtjeva te ova jednadzba predstavlja intenzitet obrade',
  //   ),
  //   Equation(
  //     equation: r"<p> $$ρ = {λ \over µ}.$$ </p>",
  //     solution: 0.2,
  //     // solution: 1.0 *
  //     //     QueningSimulation.lamda.parameter! /
  //     //     QueningSimulation.mu.parameter!,
  //     description: 'definicija intenziteta prometa',
  //   ),
  //   Equation(
  //     equation: r"<p> $$η = min(1, {λ \over µ}).$$ </p>",
  //     solution: 0.2,
  //     // solution: math.min(
  //     //   1.0 *
  //     //       QueningSimulation.lamda.parameter! /
  //     //       QueningSimulation.mu.parameter!,
  //     //   1.0,
  //     // ),
  //     description: 'definicija faktora opterećenja poslužitelja',
  //   ),
  // ];

  @override
  SimulationResultsModel copyWith() => this;
}

class Equation {
  final String equation;
  final double solution;
  final String description;

  Equation({
    required this.equation,
    required this.solution,
    required this.description,
  });
}
