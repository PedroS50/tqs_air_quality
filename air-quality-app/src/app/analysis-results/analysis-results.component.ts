import { Component, OnInit, Input } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { AirPollutionAnalysis } from '../classes/AirPollutionAnalysis';
import { SearchService } from '../services/search.service';

@Component({
  selector: 'app-analysis-results',
  templateUrl: './analysis-results.component.html',
  styleUrls: ['./analysis-results.component.css']
})
export class AnalysisResultsComponent implements OnInit {

  @Input('data') meals: string[] = [];
  page: number = 1;
  isEmpty: boolean = false;
  error: boolean = false;
  errorCode: string = "";

  analysis!: AirPollutionAnalysis;

  constructor(public searchService: SearchService, private titleService: Title) {
    this.titleService.setTitle("APResults")
  }

  ngOnInit(): void {
    this.searchService.getAQAnalysis()?.subscribe((value: AirPollutionAnalysis) => {
      this.analysis = value;
      console.log(value)
      if (value.airPollution.length == 0)
        this.isEmpty = true;

    }, error => {
      this.error = true;
      this.errorCode = error.status;
    });
  }

  getAnalysis() {
    return this.analysis;
  }

}
