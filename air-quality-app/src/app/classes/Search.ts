
export class Search{
    type: string = "";
    address: string = "";
    startDate: string = "";
    endDate: string = "";

    constructor(type: string, address: string, start: string, end: string) {
        this.type = type;
        this.address = address;
        this.startDate = start;
        this.endDate = end;
    }

}
