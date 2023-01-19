import 'package:flutter/material.dart';
import 'package:simulacija/style/app_style.dart';
import 'package:syncfusion_flutter_charts/charts.dart';

// void main() async {
//   WidgetsFlutterBinding.ensureInitialized();
//   await SystemChrome.setPreferredOrientations(
//     [DeviceOrientation.landscapeLeft],
//   );
//   runApp(
//     MaterialApp(
//       home: Scaffold(
//         body: SafeArea(
//           child: Histogram(
//             N: [
//               SimulationData(1.0, 10),
//               SimulationData(1.5, 5),
//               SimulationData(1.8, 4),
//               SimulationData(2.1, 6),
//               SimulationData(3.0, 2),
//             ],
//             m: 4,
//           ),
//         ),
//       ),
//     ),
//   );
// }

class Histogram extends StatelessWidget {
  final List<SimulationData> N; // Ns;
  final int m;

  const Histogram({
    required this.N,
    // required this.Ns,
    required this.m,
    // required this.k,
    super.key,
  });
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: SfCartesianChart(
          borderColor: AppStyle.grey,
          legend: Legend(
            isVisible: true,
            textStyle: TextStyle(
              color: AppStyle.grey,
            ),
          ),
          tooltipBehavior: TooltipBehavior(
            enable: true,
          ),
          backgroundColor: AppStyle.bgColor,
          plotAreaBorderWidth: 0.0,
          primaryXAxis: NumericAxis(
            title: AxisTitle(
              text: 't',
              textStyle: TextStyle(
                color: AppStyle.grey,
              ),
              alignment: ChartAlignment.far,
            ),
          ),
          primaryYAxis: NumericAxis(
            title: AxisTitle(
              text: 'N(t)',
              textStyle: TextStyle(
                color: AppStyle.grey,
              ),
              alignment: ChartAlignment.far,
            ),
          ),
          series: <ChartSeries>[
            // SplineAreaSeries<SimulationData, int>(
            //   dataSource: Ns,
            //   xValueMapper: (SimulationData data, _) => data.t.round(),
            //   yValueMapper: (SimulationData data, _) => data.n,
            // ),
            StepAreaSeries<SimulationData, num>(
              name: 'N(t)',
              dataSource: N,
              xValueMapper: (SimulationData sd, _) => sd.t,
              yValueMapper: (SimulationData sd, _) => sd.n,
              color: AppStyle.secondary,
              borderColor: Colors.white,
              borderWidth: 2,
              animationDuration: 0,
              // gradient: LinearGradient(
              // colors: [AppStyle.grey, AppStyle.secondary],
              //   begin: Alignment.bottomCenter,
              //   end: Alignment.topCenter,
              // ),
              // pointColorMapper: (SimulationData data, _) => Colors.blue,
            ),
            LineSeries<SimulationData, num>(
              name: 'servers',
              width: 2,
              dataSource: N,
              xValueMapper: (SimulationData data, _) => data.t,
              yValueMapper: (SimulationData data, _) => m,
              color: AppStyle.primary,
              animationDuration: 0,
            ),
            // LineSeries<SimulationData, num>(
            //   name: 'limit',
            //   dashArray: <double>[10, 10],
            //   width: 2,
            //   dataSource: N,
            //   xValueMapper: (SimulationData data, _) => data.t,
            //   yValueMapper: (SimulationData data, _) => m + k,
            //   color: Colors.red,
            //   animationDuration: 0,
            // )

            // StepAreaSeries<SimulationData, num>(
            //   name: 'Ns(t)',
            //   dataSource: Ns,
            //   xValueMapper: (SimulationData sd, _) => sd.t,
            //   yValueMapper: (SimulationData sd, _) => sd.n,
            //   // pointColorMapper: (sd, i) => sd.color,
            //   // gradient: LinearGradient(
            //   //   colors: [AppStyle.secondary, AppStyle.primary],
            //   //   begin: Alignment.bottomCenter,
            //   //   end: Alignment.topCenter,
            //   // ),

            //   color: AppStyle.primary,
            // ),
          ],
        ),
      ),
    );
  }
}

class SimulationData {
  final double t;
  final int n;

  SimulationData(this.t, this.n);

  @override
  String toString() => '[$t : $n]';
}
