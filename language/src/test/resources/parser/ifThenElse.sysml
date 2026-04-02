package IfThenThenElseExample {

  if threat.level == high then {
    perform soundAlarm {in cause = threat;}
  } else if threat.level == medium then {
    action sendNotification {in msg = threat;}
  } else {
    action beginMonitoring {in target = threat;}
  }

}
