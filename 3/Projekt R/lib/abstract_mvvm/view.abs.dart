import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:simulacija/abstract_mvvm/app_routes.dart';
import 'package:simulacija/abstract_mvvm/view_model.abs.dart';

final RouteObserver<ModalRoute<void>> routeObserver =
    RouteObserver<ModalRoute<void>>();

abstract class View<VM extends ViewModel> extends StatefulWidget {
  final VM viewModel;
  const View.model(this.viewModel, {Key? key}) : super(key: key);
}

abstract class ViewState<V extends View, VM extends ViewModel> extends State<V>
    with RouteAware {
  late final VM _viewModel;

  VM get viewModel => _viewModel;

  @mustCallSuper
  ViewState(this._viewModel);

  @mustCallSuper
  @override
  void initState() {
    super.initState();
    viewModel.init();
  }

  StreamSubscription<AppRouteSpec> listenToRoutesSpecs(
    Stream<AppRouteSpec> routes,
  ) {
    return routes.listen((spec) async {
      switch (spec.action) {
        case AppRouteAction.pushTo:
          await Navigator.of(context).pushNamed(
            spec.name,
            arguments: spec.arguments,
          );
          if (spec.then != null) spec.then!.call();
          break;
        case AppRouteAction.replaceWith:
          await Navigator.of(context).pushReplacementNamed(
            spec.name,
            arguments: spec.arguments,
          );
          break;
        case AppRouteAction.replaceAllWith:
          await Navigator.of(context).pushNamedAndRemoveUntil(
            spec.name,
            (route) => false,
            arguments: spec.arguments,
          );
          break;
        case AppRouteAction.pop:
          Navigator.of(context).pop();
          break;
        case AppRouteAction.popUntil:
          Navigator.of(context)
              .popUntil((route) => route.settings.name == spec.name);
          break;
        case AppRouteAction.popUntilRoot:
          Navigator.of(context).popUntil((route) => route.isFirst);
          break;
      }
    });
  }

  @mustCallSuper
  @override
  void didChangeDependencies() {
    super.didChangeDependencies();

    // subscribe for the change of route
    if (ModalRoute.of(context) != null) {
      routeObserver.subscribe(this, ModalRoute.of(context)! as PageRoute);
    }
  }

  /// Called when the top route has been popped off, and the current route
  /// shows up.
  @mustCallSuper
  @override
  void didPopNext() {
    viewModel.routingDidPopNext();
  }

  /// Called when the current route has been pushed.
  @mustCallSuper
  @override
  void didPush() {
    viewModel.routingDidPush();
  }

  /// Called when the current route has been popped off.
  @mustCallSuper
  @override
  void didPop() {
    viewModel.routingDidPop();
  }

  /// Called when a new route has been pushed, and the current route is no
  /// longer visible.
  @mustCallSuper
  @override
  void didPushNext() {
    viewModel.routingDidPushNext();
  }

  @mustCallSuper
  @override
  void dispose() {
    routeObserver.unsubscribe(this);
    viewModel.dispose();
    super.dispose();
  }
}
