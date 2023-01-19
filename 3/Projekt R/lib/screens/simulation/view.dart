import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:simulacija/abstract_mvvm/view.abs.dart';
import 'package:simulacija/screens/simulation/model.dart';
import 'package:simulacija/screens/simulation/viewmodel.dart';
import 'package:simulacija/style/app_style.dart';
import 'package:simulacija/util/simulation.dart';
import 'package:simulacija/widgets/histogram.dart';

class SimulationView extends View<SimulationViewModel> {
  const SimulationView({
    required SimulationViewModel viewModel,
    Key? key,
  }) : super.model(viewModel, key: key);

  @override
  _SimulationViewState createState() {
    return _SimulationViewState(viewModel);
  }
}

class _SimulationViewState
    extends ViewState<SimulationView, SimulationViewModel> {
  _SimulationViewState(SimulationViewModel viewModel) : super(viewModel);

  @override
  void initState() {
    super.initState();
    listenToRoutesSpecs(viewModel.routes);
  }

  @override
  Widget build(BuildContext context) {
    return StreamBuilder<SimulationModel>(
      stream: viewModel.state,
      builder: (context, snapshot) {
        if (!snapshot.hasData) {
          return Center(
            child: SpinKitCubeGrid(
              size: 140,
              color: AppStyle.primary,
            ),
          );
        }
        final state = snapshot.data!;
        return Scaffold(
          backgroundColor: AppStyle.bgColor,
          body: SafeArea(
            child: Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  SizedBox(
                    width: 700,
                    height: 320,
                    child: Histogram(
                      N: state.N,
                      m: QueningSimulation.m,
                      // k: QueningSimulation.k,
                    ),
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: [
                      Container(
                        width: 40,
                        height: 40,
                        decoration: BoxDecoration(
                          color: AppStyle.grey,
                          border: Border.all(
                            color: AppStyle.primary,
                          ),
                          borderRadius: BorderRadius.circular(10),
                        ),
                        child: InkWell(
                          onTap: viewModel.simulationIsRuningLive
                              ? viewModel.pauseLiveSimulation
                              : viewModel.playLiveSimulation,
                          onLongPress: viewModel.restartLiveSimulation,
                          child: Icon(
                            viewModel.simulationIsRuningLive
                                ? Icons.pause
                                : Icons.play_circle_outline,
                            size: 20,
                            color: viewModel.simulationIsRuningLive
                                ? AppStyle.orange
                                : AppStyle.bgColor,
                          ),
                        ),
                      ),
                      TextButton(
                        onPressed: viewModel.showSimulationResults,
                        child: Text(
                          'RESULTS',
                          style: TextStyle(
                            color: viewModel.simulationIsRuningLive
                                ? AppStyle.grey
                                : AppStyle.orange,
                          ),
                        ),
                      ),
                    ],
                  )
                ],
              ),
            ),
          ),
        );
      },
    );
  }
}
