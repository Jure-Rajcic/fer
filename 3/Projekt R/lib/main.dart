import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:simulacija/abstract_mvvm/view.abs.dart';
import 'package:simulacija/routes.dart';
import 'package:simulacija/screens/simulation/view.dart';
import 'package:simulacija/screens/simulation/viewmodel.dart';
import 'package:simulacija/screens/start/view.dart';
import 'package:simulacija/screens/start/viewmodel.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await SystemChrome.setPreferredOrientations(
    [DeviceOrientation.landscapeLeft],
  );
  final router = AppRouter();
  runApp(
    MaterialApp(
      debugShowCheckedModeBanner: true,
      navigatorObservers: [routeObserver],
      home: false
          ? SimulationView(
              viewModel: SimulationViewModel(),
            )
          : StartView(
              viewModel: StartViewModel(),
            ),
      onGenerateRoute: router.route,
    ),
  );
}
