import { AirPollution } from "./AirPollution";
import { Location } from "./Location";

export class AirPollutionAnalysis {
    airPollution!: AirPollution[];
    
    location!: Location;

    public getLocation() {
        return this.location;
    }

    public getAirPollution() {
        return this.airPollution;
    }

}