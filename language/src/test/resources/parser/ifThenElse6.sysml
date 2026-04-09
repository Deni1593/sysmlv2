standard library package SpatialItems {
    …

    item def SpatialItem :> SpatialFrame {
        item :>> self : SpatialItem;
        item :>> localClock : Clock[1] default Time::universalClock;
        attribute coordinateFrame : ThreeDCoordinateFrame[1]
            default universalCartesianSpatial3dCoordinateFrame;

        item originPoint : Point[1] :> spaceShots;

        item subSpatialItems : SpatialItem[1..*] :> subitems {
            ref item :>> SpatialItem::localClock, subitems::localClock;
        }
        item componentItems : SpatialItem[0..*] ordered :> subSpatialItems {
            ref item :>> SpatialItem::localClock, subSpatialItems::localClock;
            attribute :>> coordinateFrame {
                attribute :>> transformation[1] default nullTransformation { … }
            }
        }

        private attribute cunionNum : Natural[1] =
            if isEmpty(componentItems) ? 0 else 1;
        private attribute componentUnion[cunionNum] :> unionsOf {
            item :>> elements : SpatialItem[1..*] = componentItems;
        }
        …
    }

    …
}
