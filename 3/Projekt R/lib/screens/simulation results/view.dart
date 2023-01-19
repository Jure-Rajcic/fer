import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:flutter_tex/flutter_tex.dart';
import 'package:simulacija/abstract_mvvm/view.abs.dart';
import 'package:simulacija/screens/simulation%20results/model.dart';
import 'package:simulacija/screens/simulation%20results/viewModel.dart';
import 'package:simulacija/style/app_style.dart';

class SimulationResultsView extends View<SimulationResultsViewModel> {
  const SimulationResultsView({
    required SimulationResultsViewModel viewModel,
    Key? key,
  }) : super.model(viewModel, key: key);

  @override
  _SimulationResultsViewState createState() {
    return _SimulationResultsViewState(viewModel);
  }
}

class _SimulationResultsViewState
    extends ViewState<SimulationResultsView, SimulationResultsViewModel> {
  _SimulationResultsViewState(SimulationResultsViewModel viewModel)
      : super(viewModel);

  @override
  void initState() {
    super.initState();
    listenToRoutesSpecs(viewModel.routes);
  }

  @override
  Widget build(BuildContext context) {
    return StreamBuilder<SimulationResultsModel>(
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
          // TODO dodat neki botun za opce formule
          backgroundColor: AppStyle.bgColor,
          floatingActionButton: Material(
            type: MaterialType.transparency,
            child: Ink(
              decoration: BoxDecoration(
                border: Border.all(
                  color: AppStyle.secondary,
                  width: 5.0,
                ),
                color: AppStyle.grey,
                shape: BoxShape.circle,
              ),
              child: InkWell(
                borderRadius: BorderRadius.circular(500.0),
                onTap: viewModel.returnToMainScreen,
                child: Padding(
                  padding: const EdgeInsets.all(20.0),
                  child: Icon(
                    Icons.replay_circle_filled,
                    color: AppStyle.primary,
                  ),
                ),
              ),
            ),
          ),
          body: SafeArea(
            child: Center(
              child: SizedBox(
                height: 300,
                child: ListView.builder(
                    itemCount: 1,
                    itemBuilder: (BuildContext context, int index) {
                      return Column(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        children: state.equatios
                            .map(
                              (equation) => SizedBox(
                                width: 600,
                                child: Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceEvenly,
                                  children: [
                                    SizedBox(
                                      width: 380,
                                      height: 100,
                                      child: Center(
                                        child: TeXView(
                                          child: TeXViewColumn(
                                            style: TeXViewStyle(
                                              backgroundColor: AppStyle.bgColor,
                                            ),
                                            children: [
                                              TeXViewDocument(
                                                equation.equation,
                                                style: TeXViewStyle.fromCSS(
                                                  '''color: #${AppStyle.primary.value.toRadixString(16)}; ''',
                                                ),
                                              ),
                                            ],
                                          ),
                                        ),
                                      ),
                                      // child: Text(QueningSimulation.lamda.parameter.toString()),
                                    ),
                                    IconButton(
                                      onPressed: AwesomeDialog(
                                        context: context,
                                        headerAnimationLoop: true,
                                        dialogType: DialogType.question,
                                        title: 'Equation info',
                                        desc: equation.description,
                                        btnOkOnPress: () {},
                                        btnOkIcon: Icons.check_circle,
                                        btnOkColor: AppStyle.primary,
                                      ).show,
                                      icon: Icon(
                                        Icons.arrow_right_alt,
                                        size: 35,
                                        color: AppStyle.grey,
                                      ),
                                    ),
                                    Container(
                                      width: 100,
                                      height: 50,
                                      decoration: BoxDecoration(
                                        border: Border.all(
                                          color: AppStyle.secondary,
                                          width: 5.0,
                                        ),
                                        color: AppStyle.grey,
                                        borderRadius: BorderRadius.circular(10),
                                      ),
                                      child: Center(
                                        child: Text(
                                          equation.solution.toStringAsFixed(3),
                                          style: const TextStyle(
                                            fontSize: 20,
                                          ),
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            )
                            .toList(),
                      );
                    }),
              ),
            ),
          ),
        );
      },
    );
  }
}
