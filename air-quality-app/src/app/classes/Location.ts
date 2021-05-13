import { Coordinates } from "./Coordinates";

export class Location {
    coordinates!: Coordinates;

    address!: string;

    public getCoordinates() {
        return this.coordinates;
    }

    public getAddress() {
        return this.address;
    }

}