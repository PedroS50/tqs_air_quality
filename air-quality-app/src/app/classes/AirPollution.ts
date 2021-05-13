import { Components } from "./Components";

export class AirPollution {
    aqi!: number;
    dtTimestamp!: Date;
    components!: Components;

    public getAqi() {
        return this.aqi;
    }

    public getDtTimestamp() {
        return this.dtTimestamp;
    }

    public getComponents() {
        return this.components;
    }

}