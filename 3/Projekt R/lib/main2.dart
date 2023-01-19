// import 'package:math_expressions/math_expressions.dart';

// /// This file contains the following examples:
// ///  - Example 1: Expression creation and evaluation
// ///               (through the Parser and programmatically)
// ///  - Example 2: Expression simplification and differentiation
// ///  - Example 3: Custom function definition and use (function bound to expression)
// ///  - Example 4: Generic function definition and use (function bound to Dart handler)
// void main() {
//   Parser p = Parser();
//   Expression exp = p.parse('e^x');

//   // (1b) Build expression: (x^2 + cos(y)) / 3
//   Variable x = Variable('x');
//   ContextModel cm = ContextModel()..bindVariable(x, Number(0.0));
//   double eval = exp.evaluate(EvaluationType.REAL, cm);

//   print(eval);
// }

// import 'package:flutter/material.dart';
// import 'package:flutter/services.dart';
// import 'package:flutter_tex/flutter_tex.dart';

// void main() async {
//   WidgetsFlutterBinding.ensureInitialized();
//   await SystemChrome.setPreferredOrientations(
//     [DeviceOrientation.landscapeLeft],
//   );
//   runApp(
//     const MaterialApp(
//       home: Scaffold(
//         body: TeXView(
//           child: TeXViewColumn(children: [
//             TeXViewDocument(
//               r"""<p>
//                        When \(a \ne 0 \), there are two solutions to \(ax^2 + bx + c = 0\) and they are
//                        $$x = {-b \pm \sqrt{b^2-4ac} \over 2a}.$$
//                   </p>""",
//               style: TeXViewStyle.fromCSS(
//                 'padding: 15px; color: white; background: green',
//               ),
//             ),
//           ]),
//         ),
//       ),
//     ),
//   );
// }

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:syncfusion_flutter_charts/charts.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await SystemChrome.setPreferredOrientations(
    [DeviceOrientation.landscapeLeft],
  );
  runApp(
    const MaterialApp(
      home: Scaffold(
        body: SafeArea(
          child: C(),
        ),
      ),
    ),
  );
}

class C extends StatelessWidget {
  const C({super.key});

  @override
  Widget build(BuildContext context) {
    List<SalesData> chartData = [
      SalesData(1, 10),
      SalesData(2, 3),
      SalesData(3, 4),
      SalesData(4, 7),
      SalesData(5, 2),
    ];
    return Scaffold(
      body: Center(
        child: SizedBox(
          height: 400,
          width: 400,
          child: SfCartesianChart(
            series: <ChartSeries>[
              HistogramSeries<SalesData, num>(
                dataSource: chartData,
                yValueMapper: (SalesData sales, _) => sales.sales,
              ),
            ],
          ),
        ),
      ),
    );
  }

  // Widget build(BuildContext context) {
  //   List<SalesData> chartData = [
  //     SalesData(1, 10),
  //     SalesData(2, 3),
  //     SalesData(3, 4),
  //     SalesData(4, 7),
  //     SalesData(5, 2),
  //   ];
  //   return SizedBox(
  //     width: 500,
  //     height: 350,
  //     child: SfCartesianChart(
  //       margin: const EdgeInsets.all(10),
  //       borderWidth: 0,
  //       primaryXAxis: NumericAxis(
  //         name: 'x',
  //       ),
  //       primaryYAxis: NumericAxis(
  //         name: 'f(x)',
  //       ),
  //       series: <ChartSeries<SalesData, int>>[
  //         SplineSeries(
  //           dataSource: chartData,
  //           xValueMapper: (SalesData sales, _) => sales.year,
  //           yValueMapper: (SalesData sales, _) => sales.sales,
  //         )
  //       ],
  //     ),
  //   );
  // }
}

class SalesData {
  final int year;
  final int sales;

  SalesData(this.year, this.sales);
}
