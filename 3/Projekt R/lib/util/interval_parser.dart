class IntervalParser {
  String parse(String input) {
    if (!input.contains('x')) {
      throw UnimplementedError(' Interval doesnt contain x');
    }

    List<String> l = input.split('x');
    for (int i = 0; i < l.length; i++) {
      l[i] = l[i].trim();
    }

    List<String> left;
    String leftRelation = '<';

    if (l[0].contains("<=")) {
      left = l[0].split('<=');
      leftRelation = '<=';
    } else if (l[0].contains("<")) {
      left = l[0].split('<');
    } else {
      throw UnimplementedError('left side missing < or <=');
    }
    if (double.tryParse(left[0]) == null) {
      throw UnimplementedError('expresion on left side is not number');
    }

    List<String> right;
    String rightRelation = '<';

    if (l[1].contains("<=")) {
      right = l[1].split('<=');
      rightRelation = '<=';
    } else if (l[1].contains("<")) {
      right = l[1].split('<');
    } else {
      throw UnimplementedError('right side missing < or <=');
    }
    if (double.tryParse(right[1]) == null) {
      throw UnimplementedError('expresion on left side is not number');
    }

    return '${left[0].trim()} $leftRelation x $rightRelation ${right[1].trim()}';
  }
}
