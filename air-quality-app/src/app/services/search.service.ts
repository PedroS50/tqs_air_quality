import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AirPollutionAnalysis } from '../classes/AirPollutionAnalysis';
import { Search } from '../classes/Search';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private searchCriteria!: Search;
  private currentAQApi: string;
  private forecastAQApi: string;
  private historicalAQApi: string;

  constructor(private http: HttpClient) {
    this.currentAQApi = 'http://localhost:8080/api/current';
    this.forecastAQApi = 'http://localhost:8080/api/forecast';
    this.historicalAQApi = 'http://localhost:8080/api/history';
  }

  getAQAnalysis(): Observable<AirPollutionAnalysis> {
  
    if (this.searchCriteria.type == "current")
      return this.http.get<AirPollutionAnalysis>( this.currentAQApi + "?address=" + this.searchCriteria.address );
  
    if (this.searchCriteria.type == "forecast")
      return this.http.get<AirPollutionAnalysis>( this.forecastAQApi + "?address=" + this.searchCriteria.address );
  
    if (this.searchCriteria.type == "history")
      return this.http.get<AirPollutionAnalysis>( this.historicalAQApi + "?address=" + this.searchCriteria.address 
                                                  + "&start=" + this.searchCriteria.startDate 
                                                  + "&end=" + this.searchCriteria.endDate);
    return new Observable;
  
  }

  setSearch(type: string, address: string, start: string, end: string) {
    this.searchCriteria = new Search(type, address, start, end);
  }

  getStart() {
    return this.searchCriteria.startDate;
  }

  getEnd() {
    return this.searchCriteria.endDate;
  }

  getAddress() {
    return this.searchCriteria.address;
  }

  getType() {
    return this.searchCriteria.type;
  }

}
