import 'package:flutter/material.dart';
import 'package:simulacija/style/app_style.dart';

DropdownButton CustomDropdownButton({
  required String value,
  required void Function(dynamic)? fun,
  required List<String> items,
}) {
  return DropdownButton(
    dropdownColor: AppStyle.grey,
    style: TextStyle(
      color: AppStyle.primary,
      fontSize: 25,
      fontWeight: FontWeight.bold,
    ),
    underline: Container(),
    value: value,
    icon: Icon(
      Icons.bar_chart,
      color: AppStyle.secondary,
    ),
    items: items.map((String items) {
      return DropdownMenuItem(
        value: items,
        child: Text(items),
      );
    }).toList(),
    onChanged: fun,
  );
}
