import 'package:simulacija/screens/simulation%20results/model.dart';

class MMmK extends SimulationResultsModel {
  // M M m inf
  @override
  // TODO: implement equatios
  List<Equation> get equatios => [
        Equation(
          equation:
              r"<p> $$ p_{0} =  ( \sum_{\mathclap{n=0}}^{m-1} (\substack{{λ \over mµ}})^{n} {m^{n} \over n!} + {m^{m} \over m!} { (\substack{{λ \over mµ}})^{m} (\substack{{λ \over mµ}})^{K+1} \over 1 - (\substack{{λ \over mµ}})})^{-1} $$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'vjerojatnost da je sustav pun',
        ),
        // TODO B na n
        Equation(
          equation:
              r"<p> $$\overline{N_{q}} = \sum_{\mathclap{n=0}}^{m-1} np_{0}B^{n} {m^{n} \over n!} + p_{0}{m^{m} \over m!} ... itd $$ </p>",
          solution: 0.2, // TODO
          // solution: 1.0 / QueningSimulation.lamda.parameter!,
          description: 'vjerojatnost da je sustav pun',
        ),
        // TODO napravit lambda ef Ns i Nq ako se lenki svida
      ];
}
