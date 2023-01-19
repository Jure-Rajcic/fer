import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:simulacija/abstract_mvvm/view.abs.dart';
import 'package:simulacija/screens/distribution/model.dart';
import 'package:simulacija/screens/distribution/viewmodel.dart';
import 'package:simulacija/style/app_style.dart';
import 'package:simulacija/util/distribution_function_validator.dart';
import 'package:simulacija/widgets/manual.dart';
import 'package:simulacija/widgets/distribution_button_frame.dart';

class DistributionView extends View<DistributionViewModel> {
  const DistributionView({
    required DistributionViewModel viewModel,
    Key? key,
  }) : super.model(viewModel, key: key);

  @override
  _DistributionViewState createState() {
    return _DistributionViewState(viewModel);
  }
}

class _DistributionViewState
    extends ViewState<DistributionView, DistributionViewModel> {
  _DistributionViewState(DistributionViewModel viewModel) : super(viewModel);

  @override
  void initState() {
    super.initState();
    listenToRoutesSpecs(viewModel.routes);
  }

  @override
  Widget build(BuildContext context) {
    return StreamBuilder<DistributionModel>(
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
          floatingActionButton: FloatingActionButton(
            onPressed: () =>
                DistributionFunctionValidator.valid(state.functions)
                    ? viewModel.showChart(state.functions)
                    : AwesomeDialog(
                        context: context,
                        headerAnimationLoop: true,
                        dialogType: DialogType.error,
                        title: 'Invalid distribution function',
                        desc:
                            'Distribution function needs to satisfy following condition:\nf(x) cant be negative for any x in interval\narea under f(x) needs to be equal to 1',
                        btnOkOnPress: () {},
                        btnOkColor: AppStyle.primary,
                        btnOkIcon: Icons.check_circle,
                      ).show(),
            child: ClipRRect(
              borderRadius: BorderRadius.circular(12),
              child: Container(
                height: 60,
                color: AppStyle.primary,
                child: Image.asset(
                  'lib/icons/graph.png',
                ),
              ),
            ),
          ),
          body: SafeArea(
            child: Stack(
              children: [
                Align(
                  alignment: Alignment.topCenter,
                  child: SizedBox(
                    width: 600,
                    height: viewModel.visible ? 180 : 280,
                    child: Column(
                      children: [
                        Container(
                          height: 50,
                          padding: const EdgeInsets.all(10),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              IconButton(
                                onPressed: viewModel.add,
                                icon: Icon(
                                  Icons.add,
                                  color: AppStyle.grey,
                                ),
                              ),
                              IconButton(
                                onPressed: viewModel.remove,
                                icon: Icon(
                                  Icons.remove,
                                  color: AppStyle.grey,
                                ),
                              ),
                            ],
                          ),
                        ),
                        SizedBox(
                          height: viewModel.visible ? 100 : 200,
                          child: ListView.builder(
                            scrollDirection: Axis.vertical,
                            shrinkWrap: true,
                            controller: viewModel.controller,
                            itemCount: state.functions.length,
                            itemBuilder: ((context, index) => Padding(
                                  padding: const EdgeInsets.all(10),
                                  child: DistributionButtonFrame(
                                    vm: viewModel,
                                    interval: state.functions[index].interval,
                                    fx: state.functions[index].fx,
                                  ),
                                )),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
                Visibility(
                  visible: viewModel.visible,
                  child: Align(
                    alignment: Alignment.bottomCenter,
                    child: Padding(
                      padding: const EdgeInsets.all(10),
                      child: Manual(vm: viewModel),
                    ),
                  ),
                )
              ],
            ),
          ),
        );
      },
    );
  }
}
