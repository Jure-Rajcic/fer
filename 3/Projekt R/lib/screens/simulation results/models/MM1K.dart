import 'package:simulacija/screens/simulation%20results/model.dart';

class MM1K extends SimulationResultsModel {
  // M M m inf
  @override
  List<Equation> get equatios => [
        Equation(
          equation:
              r"<p> $$p_{0} = {1 - { λ \over µ} \over 1 - ({ λ \over µ})^{K+1} }$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan broj jedinuca u redu cekanja',
        ),
        Equation(
          equation: r"<p> $$A = { λ \over µ} $$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'opterecenje sustava',
        ),
        Equation(
          equation:
              r"<p> $$\overline{N_{q}} = {A(1-(K+1)A^{K} + KA^{K+1}) \over (1-A^{K+1})(1-A)}$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan broj jedinuca u redu cekanja',
        ),
        Equation(
          equation: r"<p> $$λ_{ef} = λ[1-p_{0}A^{K}]$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan broj jedinuca u redu cekanja',
        ),
        Equation(
          equation:
              r"<p> $$W = { A(1-(K+1)A^{K} + KA^{K+1}) \over (1-A^{K+1})(1-A)} + {1 \over µ}$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan broj jedinuca u redu cekanja',
        ),
        Equation(
          equation: r"<p> $$\overline{N_{q}} = \overline{N} - ρ$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan broj jedinuca u redu cekanja',
        ),
      ];
}
