import 'package:simulacija/abstract_mvvm/app_routes.dart';
import 'package:simulacija/abstract_mvvm/view_model.abs.dart';
import 'package:rxdart/subjects.dart';
import 'package:simulacija/screens/system_characteristics/model.dart';

class SystemCharacteristicsViewModel extends ViewModel {
  final _routes = PublishSubject<AppRouteSpec>();
  final _stateModel = BehaviorSubject<SystemCharacteristicsModel>.seeded(
    SystemCharacteristicsModel(),
  );

  Stream<AppRouteSpec> get routes => _routes;
  Stream<SystemCharacteristicsModel> get state => _stateModel;

  void returnToMainScreen() {
    _routes.add(
      AppRouteSpec(name: '/', action: AppRouteAction.replaceAllWith),
    );
  }

  @override
  void dispose() {
    _stateModel.close();
    _routes.close();
  }
}

// TODO passanje nekakve mape kroz start rutu da se moze vise puta inicjalizirat i da se na kraju mogu vratiti na ne inicjalizirani usustav
// maknit quening simulation staticku kalsu
// postavit da su lambda i mi double tipovi vrijednosti
