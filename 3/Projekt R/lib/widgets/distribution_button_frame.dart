import 'package:flutter/material.dart';
import 'package:math_expressions/math_expressions.dart';
import 'package:simulacija/screens/distribution/viewmodel.dart';
import 'package:simulacija/style/app_style.dart';

class DistributionButtonFrame extends Container {
  final DistributionViewModel vm;
  String interval;
  String fx;
  DistributionButtonFrame({
    required this.vm,
    required this.interval,
    required this.fx,
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 80,
      width: 100,
      child: Row(
        children: [
          FunctionButton(
            text: interval,
            flex: 3,
            vm: vm,
            type: FunctionButtonType.INTERVAL,
          ),
          Container(color: AppStyle.bgColor, width: 2),
          FunctionButton(
            text: fx,
            flex: 7,
            vm: vm,
            type: FunctionButtonType.FX,
          ),
        ],
      ),
    );
  }

  late Expression expression;
}

class FunctionButton extends StatelessWidget {
  String text;
  final int flex;
  final FunctionButtonType type;
  final DistributionViewModel vm;

  FunctionButton({
    super.key,
    required this.text,
    required this.flex,
    required this.vm,
    required this.type,
  });

  @override
  Widget build(BuildContext context) {
    return Expanded(
      flex: flex,
      child: Material(
        color: AppStyle.bgColor,
        child: InkWell(
          onTap: () => vm.displayCalculator(type),
          splashColor: AppStyle.secondary,
          child: Container(
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(20),
              color: AppStyle.primary,
            ),
            height: double.maxFinite,
            child: Center(
              child: Text(
                text,
                style: TextStyle(
                  color: AppStyle.grey,
                  fontSize: 14,
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}

enum FunctionButtonType {
  INTERVAL,
  FX,
}
