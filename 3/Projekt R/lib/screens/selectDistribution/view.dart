import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:simulacija/abstract_mvvm/view.abs.dart';
import 'package:simulacija/screens/selectDistribution/model.dart';
import 'package:simulacija/screens/selectDistribution/viewmodel.dart';
import 'package:simulacija/style/app_style.dart';

class SelectDistributionView extends View<SelectDistributionViewModel> {
  const SelectDistributionView({
    required SelectDistributionViewModel viewModel,
    Key? key,
  }) : super.model(viewModel, key: key);

  @override
  _SelectDistributionViewState createState() {
    return _SelectDistributionViewState(viewModel);
  }
}

class _SelectDistributionViewState
    extends ViewState<SelectDistributionView, SelectDistributionViewModel> {
  final controller = TextEditingController();

  _SelectDistributionViewState(SelectDistributionViewModel viewModel)
      : super(viewModel);

  @override
  void initState() {
    super.initState();
    listenToRoutesSpecs(viewModel.routes);
  }

  @override
  Widget build(BuildContext context) {
    return StreamBuilder<SelectDistributionModel>(
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
              child: SizedBox(
                height: 200,
                width: 350,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    Container(
                      height: 50,
                      decoration: BoxDecoration(
                        color: AppStyle.grey,
                        border: Border.all(color: AppStyle.primary),
                        borderRadius:
                            const BorderRadius.all(Radius.circular(5)),
                      ),
                      alignment: Alignment.centerLeft,
                      child: Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Text(
                          'select distribution density f(x):',
                          style: TextStyle(
                            fontSize: 20,
                            color: AppStyle.primary,
                          ),
                        ),
                      ),
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Container(
                          decoration: BoxDecoration(
                            color: AppStyle.grey,
                            border: Border.all(color: AppStyle.primary),
                            borderRadius: const BorderRadius.all(
                              Radius.circular(5),
                            ),
                          ),
                          width: 200,
                          height: 50,
                          child: Center(
                            child: Text(
                              'exponential distribution',
                              style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 14,
                                color: AppStyle.primary,
                              ),
                            ),
                          ),
                        ),
                        Container(
                          decoration: BoxDecoration(
                            color: AppStyle.grey,
                            border: Border.all(color: AppStyle.primary),
                            borderRadius: const BorderRadius.all(
                              Radius.circular(5),
                            ),
                          ),
                          width: 50,
                          height: 50,
                          child: TextField(
                            textAlign: TextAlign.center,
                            controller: controller..text = 'λ',
                            onTap: () => controller..text = '',
                          ),
                        ),
                        IconButton(
                          onPressed: (() =>
                              viewModel.inputValid(controller.text)
                                  ? viewModel.showExponentialGraph()
                                  : AwesomeDialog(
                                      context: context,
                                      headerAnimationLoop: true,
                                      dialogType: DialogType.error,
                                      title: 'Invalid parameter',
                                      desc:
                                          'Parameter needs to be integer greater than 0',
                                      btnOkOnPress: () {},
                                      btnOkIcon: Icons.check_circle,
                                      btnOkColor: AppStyle.primary,
                                    ).show()),
                          icon: Icon(
                            Icons.show_chart,
                            size: 30,
                            color: AppStyle.primary,
                          ),
                        ),
                      ],
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Container(
                          decoration: BoxDecoration(
                            color: AppStyle.grey,
                            border: Border.all(color: AppStyle.primary),
                            borderRadius: const BorderRadius.all(
                              Radius.circular(5),
                            ),
                          ),
                          width: 200,
                          height: 50,
                          child: Center(
                            child: Text(
                              'set custom distribution',
                              style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 14,
                                color: AppStyle.primary,
                              ),
                            ),
                          ),
                        ),
                        IconButton(
                          onPressed:
                              viewModel.showPageForDefiningCustomFunction,
                          icon: Icon(
                            Icons.edit,
                            size: 30,
                            color: AppStyle.primary,
                          ),
                        ),
                        const SizedBox(
                          height: 50,
                          width: 50,
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
          ),
        );
      },
    );
  }
}
