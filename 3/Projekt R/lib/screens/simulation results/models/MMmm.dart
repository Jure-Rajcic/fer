import 'package:simulacija/screens/simulation%20results/model.dart';

class MMmm extends SimulationResultsModel {
  // M M m inf
  @override
  // TODO: implement equatios
  List<Equation> get equatios => [
        Equation(
          equation:
              r"<p> $$ p_{m} = { A^{m} {1 \over m!} \over \sum_{\substack{n=0}}^{m} A^{\substack{n}} {1 \over n!} } $$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'vjerojatnost da je sustav pun',
        ),
        Equation(
          equation: r"<p> $$λ_{ef} = λ(1-p_{m})$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan broj jedinuca u redu cekanja',
        ),
        Equation(
          equation: r"<p> $$\overline{N} = λ_{ef} \overline{x}$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan broj jedinuca u redu cekanja',
        ),
      ];
}
