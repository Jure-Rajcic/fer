import 'dart:math';

import 'package:simulacija/abstract_mvvm/model.abs.dart';
import 'package:simulacija/constants/parameter_type.dart';
import 'package:simulacija/screens/numeric/viewmodel.dart';

class NumericModel extends Model {
  final List<String> numbers;
  final ParameterType type;

  NumericModel({
    required this.numbers,
    required this.type,
  });

  @override
  NumericModel copyWith() {
    return this;
  }

  static int maxNumber() {
    return pow(10, NumericViewModel.MAX_NUMBER_OF_DIGITS).toInt();
  }

  int number() {
    if (numbers.contains("inf")) {
      return maxNumber();
    }
    int result = 0;
    for (String num in numbers) {
      result = result * 10 + int.tryParse(num)!;
    }
    return result;
  }
}

// class Pair<T, V> {
//   final T first;
//   final V second;

//   Pair({required this.first, required this.second});
// }
