import 'package:flutter/material.dart';
import 'package:simulacija/style/app_style.dart';
import 'package:simulacija/util/point.dart';
import 'package:syncfusion_flutter_charts/charts.dart';

// void main() {
//   return runApp(_ChartApp());
// }

// class _ChartApp extends StatelessWidget {
//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       theme: ThemeData(primarySwatch: Colors.blue),
//       home: Chart(
//         data: [
//           Point(x: 0, y: 0),
//           Point(x: 1, y: 1),
//           Point(x: 2, y: 2),
//           Point(x: 3, y: 3),
//           Point(x: 4, y: 0),
//           Point(x: 5, y: 1),
//           Point(x: 6, y: 2),
//           Point(x: 7, y: 3),
//           Point(x: 8, y: 0),
//           Point(x: 9, y: 1),
//           Point(x: 10, y: 2),
//         ],
//         min_x: 1,
//         max_x: 5,
//       ),
//     );
//   }
// }

class Chart extends StatefulWidget {
  final List<Point> data;
  double minX;
  double maxX;

  Chart({
    required this.data,
    required this.minX,
    required this.maxX,
    Key? key,
  }) : super(key: key);

  @override
  _ChartState createState() => _ChartState();
}

class _ChartState extends State<Chart> {
  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 500,
      height: 350,
      child: SfCartesianChart(
        margin: const EdgeInsets.all(10),
        borderWidth: 0,
        borderColor: AppStyle.grey,
        primaryXAxis: NumericAxis(
          minimum: widget.minX,
          maximum: widget.maxX,
          name: 'x',
        ),
        onPlotAreaSwipe: (direction) => setState(() {
          double offset = 2;
          switch (direction) {
            case ChartSwipeDirection.start:
              offset *= 1;
              break;
            case ChartSwipeDirection.end:
              offset *= -1;
              break;
            default:
              throw UnimplementedError(
                "unrecognized graph swipe operation",
              );
          }
          widget.minX += offset;
          widget.maxX += offset;
        }),
        primaryYAxis: NumericAxis(
          name: 'f(x)',
        ),
        series: <ChartSeries<Point, double>>[
          SplineAreaSeries(
            dataSource: widget.data,
            color: AppStyle.grey,
            xValueMapper: (Point sales, _) => sales.x,
            yValueMapper: (Point sales, _) => sales.y,
          ),
          SplineSeries(
            dataSource: widget.data,
            xValueMapper: (Point sales, _) => sales.x,
            yValueMapper: (Point sales, _) => sales.y,
            color: AppStyle.primary,
            width: 4,
            // markerSettings: const MarkerSettings(
            //   color: Colors.white,
            //   borderWidth: 0.01,
            //   isVisible: true,
            //   borderColor: Colors.white,
            // ),
          )
        ],
      ),
    );
  }
}
