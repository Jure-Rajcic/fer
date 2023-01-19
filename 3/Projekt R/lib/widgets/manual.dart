import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:flutter/material.dart';
import 'package:simulacija/screens/distribution/viewmodel.dart';
import 'package:simulacija/style/app_style.dart';

class Manual extends Container {
  final DistributionViewModel vm;
  final controller = TextEditingController();
  Manual({required this.vm, super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10),
        color: AppStyle.grey,
      ),
      margin: const EdgeInsets.only(right: 80, bottom: 10),
      width: 500,
      height: 180,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          Padding(
            padding: const EdgeInsets.all(10),
            child: SizedBox(
              height: 40,
              child: Row(
                children: [
                  Expanded(
                    flex: 8,
                    child: TextField(
                      textAlign: TextAlign.center,
                      controller: controller,
                    ),
                  ),
                  Expanded(
                    flex: 1,
                    child: IconButton(
                      onPressed: () => vm.updateCurrentText(controller.text),
                      icon: Icon(
                        Icons.done,
                        color: AppStyle.primary,
                      ),
                    ),
                  ),
                  Expanded(
                    flex: 1,
                    child: IconButton(
                      onPressed: vm.closeCalculator,
                      icon: Icon(
                        Icons.close,
                        color: AppStyle.secondary,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
          infoButtons(
            list: ['+', '-', '*', '/', '%'],
            context: context,
          ),
          infoButtons(
            list: ['^', '!', 'nrt', 'log', 'e'],
            context: context,
          ),
          infoButtons(
            list: ['cos', 'sin', 'tan', 'ceil', 'floor'],
            context: context,
          ),
        ],
      ),
    );
  }

  static Row infoButtons(
      {required List<String> list, required BuildContext context}) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: list
          .map(
            (name) => Container(
              height: 35,
              width: 55,
              decoration: BoxDecoration(
                border: Border.all(color: AppStyle.primary),
                borderRadius: BorderRadius.circular(8),
              ),
              child: TextButton(
                child: Text(
                  name,
                  textAlign: TextAlign.center,
                ),
                onPressed: () => showInfo(context: context, name: name),
              ),
            ),
          )
          .toList(),
    );
  }

  static showInfo({required BuildContext context, required String name}) {
    final l = Information.getInfo(name);
    String title = l[0];
    String desc = 'Syntax: ${l[1]}';
    if (l.length == 3) {
      desc += '\nExample: ${l[2]}';
    }
    AwesomeDialog(
      context: context,
      headerAnimationLoop: true,
      dialogType: DialogType.infoReverse,
      title: title,
      desc: desc,
      btnOkOnPress: () {},
      btnOkIcon: Icons.check_circle,
    ).show();
  }
}

class Information {
  static List<String> getInfo(String function) {
    switch (function) {
      case '+':
        return ['PLUS', '(a) + (b)'];
      case '-':
        return ['MINUS', '(a) - (b)'];
      case '*':
        return ['TIMES', '(a) * (b)'];
      case '/':
        return ['DIV', '(a) / (b)'];
      case '%':
        return ['MOD', '(a) % (b)'];
      case '^':
        return ['POW', '(a) ^ (b)'];
      case '!':
        return ['FACTORIAL', '(a)!'];
      case 'nrt':
        return ['ROOT', 'nrt(a, {b})', ' nrt(2, {16}) = 3'];
      case 'log':
        return ['LOG', 'log({a},{b})', 'log({2},{8}) = 3'];
      case 'e':
        return ['EXP', 'e^(a)'];
      case 'cos':
        return ['COS', 'cos(a)'];
      case 'sin':
        return ['SIN', 'sin(a)'];
      case 'tan':
        return ['TAN', 'tan(a)'];
      case 'ceil':
        return ['CEIL', 'ceil(a)'];
      case 'floor':
        return ['FLOOR', 'floor(a)'];
      default:
        throw UnimplementedError();
    }
  }
}
