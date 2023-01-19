import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:simulacija/abstract_mvvm/view.abs.dart';
import 'package:simulacija/screens/chart/model.dart';
import 'package:simulacija/screens/chart/viewmodel.dart';
import 'package:simulacija/style/app_style.dart';
import 'package:simulacija/widgets/chart.dart';

class ChartView extends View<ChartViewModel> {
  const ChartView({
    required ChartViewModel viewModel,
    Key? key,
  }) : super.model(viewModel, key: key);

  @override
  _ChartViewState createState() {
    return _ChartViewState(viewModel);
  }
}

class _ChartViewState extends ViewState<ChartView, ChartViewModel> {
  _ChartViewState(ChartViewModel viewModel) : super(viewModel);

  @override
  void initState() {
    super.initState();
    listenToRoutesSpecs(viewModel.routes);
  }

  bool show = true;

  @override
  Widget build(BuildContext context) {
    return StreamBuilder<ChartModel>(
      stream: viewModel.state,
      builder: (context, snapshot) {
        if (!snapshot.hasData) {
          return Scaffold(
            backgroundColor: AppStyle.bgColor,
            body: Center(
              child: SpinKitCubeGrid(
                size: 140,
                color: AppStyle.primary,
              ),
            ),
          );
        }
        final state = snapshot.data!;

        return Scaffold(
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
                    Icons.reply,
                    color: AppStyle.primary,
                  ),
                ),
              ),
            ),
          ),
          body: SafeArea(
            child: Center(
              child: Chart(
                data: state.points,
                minX: state.minX,
                maxX: state.maxX,
              ),
            ),
          ),
        );
      },
    );
  }
}
