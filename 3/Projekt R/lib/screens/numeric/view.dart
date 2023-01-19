import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:simulacija/abstract_mvvm/view.abs.dart';
import 'package:simulacija/screens/numeric/model.dart';
import 'package:simulacija/screens/numeric/viewmodel.dart';
import 'package:simulacija/style/app_style.dart';
import 'package:simulacija/widgets/simple_calculator.dart';

class NumericView extends View<NumericViewModel> {
  const NumericView({
    required NumericViewModel viewModel,
    Key? key,
  }) : super.model(viewModel, key: key);

  @override
  _NumericViewState createState() {
    return _NumericViewState(viewModel);
  }
}

class _NumericViewState extends ViewState<NumericView, NumericViewModel> {
  _NumericViewState(NumericViewModel viewModel) : super(viewModel);

  @override
  void initState() {
    super.initState();
    listenToRoutesSpecs(viewModel.routes);
  }

  @override
  Widget build(BuildContext context) {
    return StreamBuilder<NumericModel>(
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
                onTap: viewModel.locked
                    ? viewModel.unlock
                    : viewModel.returnToMainScreen,
                child: Padding(
                  padding: const EdgeInsets.all(20.0),
                  child: Icon(
                    viewModel.locked ? Icons.lock : Icons.reply,
                    color: AppStyle.primary,
                  ),
                ),
              ),
            ),
          ),
          body: SafeArea(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Center(
                  child: ConstrainedBox(
                    constraints: BoxConstraints(
                      minWidth: 50,
                      maxWidth: min((state.numbers.length + 1) * 25 + 30, 500),
                    ),
                    child: Container(
                      height: 50,
                      decoration: BoxDecoration(
                        borderRadius: const BorderRadius.all(
                          Radius.circular(10),
                        ),
                        color: AppStyle.grey,
                      ),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: state.numbers
                              .map(
                                (string) => Container(
                                  decoration: const BoxDecoration(
                                    borderRadius: BorderRadius.all(
                                      Radius.circular(3),
                                    ),
                                    // color: AppStyle.secondary,
                                  ),
                                  margin: const EdgeInsets.all(1),
                                  height: 20,
                                  child: ClipRRect(
                                    borderRadius: BorderRadius.circular(10),
                                    child: Image.asset(
                                      'lib/icons/keys/$string.png',
                                      // color: AppStyle.primary,
                                    ),
                                  ),
                                ),
                              )
                              .toList()),
                    ),
                  ),
                ),
                SimpleCalculator(
                  vm: viewModel,
                ),
              ],
            ),
          ),
        );
      },
    );
  }
}
