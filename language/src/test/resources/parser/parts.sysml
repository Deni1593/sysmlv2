/* (c) https://github.com/MontiCore/monticore */
part Driver;

part def Vehicle {
  part driver: Driver;
}

attribute def Volume;

part def Engine {
  timing delayed;

  attribute v: Volume;
}

enum def MODE {
  enum WORKING;
  enum BROKEN;
}

attribute def Status {
  attribute m: MODE;
}

port def Fluid {
  attribute fluidFlow: int;
}

part tank {
  port fluidIn: Fluid;
  port fluidOut: ~Fluid;

}
