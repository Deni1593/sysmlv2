action def ChargeBattery {

    loop action charging {
        action monitor : MonitorBattery {
            out charge;
        }

        then if monitor.charge < 100 then {
            action addCharge : AddCharge {
                in charge = monitor.charge;
            }
        }
    } until charging.monitor.charge >= 100;

    then action endCharging : EndCharging;
    then done;
}
