import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:simulacija/abstract_mvvm/view.abs.dart';
import 'package:simulacija/screens/start/model.dart';
import 'package:simulacija/screens/start/viewmodel.dart';
import 'package:simulacija/style/app_style.dart';

class StartView extends View<StartViewModel> {
  const StartView({
    required StartViewModel viewModel,
    Key? key,
  }) : super.model(viewModel, key: key);

  @override
  _StartViewState createState() {
    return _StartViewState(viewModel);
  }
}

class _StartViewState extends ViewState<StartView, StartViewModel> {
  _StartViewState(StartViewModel viewModel) : super(viewModel);

  @override
  void initState() {
    super.initState();
    listenToRoutesSpecs(viewModel.routes);
  }

  @override
  Widget build(BuildContext context) {
    // TODO pri vracanju na min screen treba postaviti varijable static quening modela
    return StreamBuilder<StartModel>(
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
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: state.models
                      .map((iconModel) => Material(
                            shape: const CircleBorder(),
                            clipBehavior: Clip.antiAliasWithSaveLayer,
                            child: InkWell(
                              onTap: () => viewModel.executeSetUp(iconModel),
                              splashColor: AppStyle.primary,
                              child: Container(
                                width: 100,
                                height: 100,
                                decoration: BoxDecoration(
                                  border: Border.all(
                                    color: AppStyle.secondary,
                                    width: 3,
                                  ),
                                  shape: BoxShape.circle,
                                ),
                                child: Ink.image(
                                  image: AssetImage(
                                    'lib/icons/${iconModel.name}.png',
                                  ),
                                  colorFilter: ColorFilter.mode(
                                    iconModel.initialized
                                        ? AppStyle.primary
                                        : AppStyle.grey,
                                    BlendMode.color,
                                  ),
                                ),
                              ),
                            ),
                          ))
                      .toList(),
                ),
                SizedBox(
                  height: 60,
                  width: 400,
                  child: ElevatedButton.icon(
                    onPressed: (() => viewModel.allParametersInitialized()
                        ?
                        // (viewModel.isMM()
                        //     ? AwesomeDialog(
                        //         context: context,
                        //         headerAnimationLoop: true,
                        //         dialogType: DialogType.infoReverse,
                        //         title: 'M/M system recognized',
                        //         desc:
                        //             'press OK to see system characteristics\npress CANCLE to start the simoulation',
                        //         btnOkOnPress:
                        //             viewModel.showSystemCharacteristics,
                        //         btnOkIcon: Icons.check_circle,
                        //         btnOkColor: AppStyle.primary,
                        //         btnCancelOnPress: viewModel.startSimulation,
                        //         btnCancelIcon: Icons.cancel,
                        //         btnCancelColor: AppStyle.primary,
                        //       ).show()
                        //     :
                        viewModel.startSimulation()
                        //)
                        : AwesomeDialog(
                            context: context,
                            headerAnimationLoop: true,
                            dialogType: DialogType.error,
                            title: 'Invalid simulation parameters',
                            btnOkOnPress: () {},
                            btnOkIcon: Icons.check_circle,
                            btnOkColor: AppStyle.primary,
                          ).show()),
                    icon: Icon(
                      Icons.play_circle_outline,
                      color: AppStyle.secondary,
                      size: 50,
                    ),
                    label: const Text(
                      "Start simulation",
                      style: TextStyle(
                        fontSize: 30,
                      ),
                    ),
                    style: ButtonStyle(
                      backgroundColor: MaterialStatePropertyAll<Color>(
                        AppStyle.primary,
                      ),
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
