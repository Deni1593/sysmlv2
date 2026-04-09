action performSelfTest { in vehicle : Vehicle; }

state def VehicleStates {
    in operatingVehicle : Vehicle;
    in controller : VehicleController;
}

state vehicleStates : VehicleStates {
    in operatingVehicle : Vehicle;
    in controller : VehicleController;

    entry; then off;

    state off;
    accept VehicleStartSignal
        if operatingVehicle.brakePedalDepressed
        do send new ControllerStartSignal() to controller
        then starting;

    state starting;
    accept VehicleOnSignal
        then on;

    state on { ... }
    accept VehicleOffSignal
        then off;
}
