import 'package:simulacija/screens/simulation%20results/model.dart';

class MMm extends SimulationResultsModel {
  // M M m inf
  @override
  // TODO: implement equatios
  List<Equation> get equatios => [
        Equation(
          equation:
              r"<p> $$\overline{N} = mρ + ρ_{0} {(mρ)^{m} \over m!} {ρ \over (1-ρ)^{2} } $$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan broj jedinica u sustavu',
        ),
        Equation(
          equation:
              r"<p> $$\overline{N_{q}} = ρ_{0} {(mρ)^{m} \over m!} {ρ \over (1-ρ)^{2} }$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan broj jedinuca u redu cekanja',
        ),
        Equation(
          equation: r"<p> $$\overline{N_{s}} = mρ$$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'prosjecan broj jedinuca u redu cekanja',
        ),
        Equation(
          equation:
              r"<p> $$ P_{m} = { {(ρm)^{m} \over m!(1 - ρ)}  \over \sum_{\substack{n=0}}^{m-1} {(mρ)^{n} \over n!} + {(mρ)^{m} \over (1-ρ)m!}}  $$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'vjerojatnost da je sustav pun',
        ),
      ];
}
