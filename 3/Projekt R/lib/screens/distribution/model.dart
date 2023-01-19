import 'package:simulacija/abstract_mvvm/model.abs.dart';
import 'package:simulacija/widgets/distribution_button_frame.dart';

class DistributionModel extends Model {
  final List<DistributionButtonFrame> functions;

  DistributionModel({
    required this.functions,
  });

  @override
  DistributionModel copyWith({
    List<DistributionButtonFrame>? functions,
  }) {
    return DistributionModel(
      functions: functions ?? this.functions,
    );
  }
}
