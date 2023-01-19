import 'package:flutter/material.dart';
import 'package:simulacija/screens/numeric/viewmodel.dart';
import 'package:simulacija/style/app_style.dart';

class SimpleCalculator extends Container {
  final NumericViewModel vm;

  SimpleCalculator({required this.vm});

  @override
  Widget build(BuildContext context) {
    List<List<String>> rows = [
      ['1', '2', '3'],
      ['4', '5', '6'],
      ['7', '8', '9'],
      ['inf', '0', 'del'],
    ];
    return Center(
      child: ClipRRect(
        borderRadius: BorderRadius.circular(20),
        child: Container(
          width: 200,
          height: 250,
          color: AppStyle.secondary,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: rows
                .map((row) => Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: row
                          .map((key) => Material(
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(20),
                                ),
                                color: AppStyle.grey,
                                clipBehavior: Clip.antiAliasWithSaveLayer,
                                child: InkWell(
                                  onTap: () => vm.keyPressed(key),
                                  splashColor: AppStyle.primary,
                                  child: SizedBox(
                                    height: 50,
                                    width: 50,
                                    child: Center(
                                      child: SizedBox(
                                        height: 25,
                                        child: Image.asset(
                                          'lib/icons/keys/$key.png',
                                          color: AppStyle.primary,
                                        ),
                                      ),
                                    ),
                                  ),
                                ),
                              ))
                          .toList(),
                    ))
                .toList(),
          ),
        ),
      ),
    );
  }
}
