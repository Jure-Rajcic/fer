import 'package:simulacija/abstract_mvvm/model.abs.dart';
import 'package:simulacija/constants/parameter_type.dart';

class StartModel extends Model {
  IconModel lambda;
  IconModel mu;
  IconModel capacity;
  IconModel server;

  StartModel({
    required this.lambda,
    required this.mu,
    required this.capacity,
    required this.server,
  });

  List<IconModel> get models => [lambda, mu, server, capacity];

  @override
  StartModel copyWith({
    IconModel? lambda,
    IconModel? mu,
    IconModel? capacity,
    IconModel? server,
  }) {
    return StartModel(
      lambda: lambda ?? this.lambda,
      mu: mu ?? this.mu,
      capacity: capacity ?? this.capacity,
      server: server ?? this.server,
    );
  }
}

class IconModel {
  final String name;
  bool initialized;
  final ParameterType parameterType;

  IconModel({
    required this.name,
    required this.initialized,
    required this.parameterType,
  });
}
