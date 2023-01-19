// chatgpt generated this <3
import 'dart:math';

import 'package:simulacija/util/point.dart';
import 'package:simulacija/util/simulation.dart';

class QueueSystem {
  int _numCustomersServed = 0;
  double _currentTime = 0.0;
  final int queueCapacity;
  int numServers;
  int _numCustomersInQueue = 0;

  late double _timeOfNextArrival;
  late double _timeOfNextDeparture;
  late double _serviceTime;

  QueueSystem({required this.queueCapacity, required this.numServers}) {
    _timeOfNextArrival = _randomArivalTime();
  }

  void simulate() {
    _serviceTime = _randomServiceTime();
    // print('timeOfNextArrival: $_timeOfNextArrival');
    // print('service time: $_serviceTime');
    _timeOfNextDeparture = _currentTime +
        (_numCustomersInQueue > 0 ? _serviceTime : double.infinity);
    double nextEventTime;
    if (_timeOfNextArrival < _timeOfNextDeparture) {
      nextEventTime = _timeOfNextArrival;
    } else {
      nextEventTime = _timeOfNextDeparture;
    }
    _currentTime = nextEventTime;

    if (_timeOfNextArrival < _timeOfNextDeparture) {
      handleArrival();
    } else {
      handleDeparture();
    }
  }

  void handleArrival() {
    // print("Customer arrived at time: $_currentTime");
    if (_numCustomersInQueue < queueCapacity) {
      if (numServers > 0) {
        numServers -= 1;
        // print("Customer entered server at time: $_currentTime");
        _timeOfNextDeparture = _currentTime + _serviceTime;
      } else {
        _numCustomersInQueue += 1;
        // print("Customer entered queue at time: $_currentTime");
      }
    } else {
      _numCustomersServed += 1;
      // print("Customer left system at time: $_currentTime");
    }
    _timeOfNextArrival = _currentTime + _randomArivalTime();
  }

  void handleDeparture() {
    _numCustomersServed += 1;
    // print("Customer left server at time: $_currentTime");
    if (_numCustomersInQueue > 0) {
      _numCustomersInQueue -= 1;
      // print("Customer entered server at time: $_currentTime");
      _timeOfNextDeparture = _currentTime + _serviceTime;
    } else {
      numServers += 1;
      _timeOfNextDeparture = double.infinity;
    }
  }
//   The process of inverse transform sampling can be broken down into three steps:
// Define the cumulative distribution function (CDF) of the desired distribution.
// Generate a random number between 0 and 1 using a uniform random number generator.
// Find the value x on the CDF such that F(x) = random number

  double _randomFromCDF(List<Point> list) {
    list = list.sublist(0, list.length - 10);
    bool changeHappened = false;
    double koef = list.reduce((Point a, Point b) => a.y > b.y ? a : b).y;
    double random = Random().nextDouble() * koef;
    Point curr = list.first;
    for (int i = 1; i < list.length; i++) {
      if ((list[i].y - random).abs() < (curr.y - random)) {
        curr = list[i];
        changeHappened = true;
      }
    }
    if (!changeHappened) {
      // uniform distribution
      int randomIndex = Random().nextInt(list.length);
      curr = list.elementAt(randomIndex);
    }
    return curr.x * 10; // scaling solution
  }

  double _randomArivalTime() => _randomFromCDF(QueningSimulation.lamdaPoints);
  double _randomServiceTime() => _randomFromCDF(QueningSimulation.muPoints);

  // get time of next arrival
  double get currentTime => _currentTime;
  int get numCustomersServed => _numCustomersServed;
  int get numCustomersInQueue => _numCustomersInQueue;

  void reset() {
    _currentTime = 0.0;
    _numCustomersServed = 0;
    _numCustomersInQueue = 0;
    _timeOfNextArrival = _randomArivalTime();
    _timeOfNextDeparture = double.infinity;
    _serviceTime = _randomServiceTime();
  }
}
