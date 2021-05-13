import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SearchService } from '../services/search.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  type: string = "current";
  address: string = "";
  startDate: string = "";
  endDate: string = "";
  searchForm: any;

  constructor(private searchService: SearchService, private router: Router ) {
    this.searchForm = new FormGroup({
      inputAddress: new FormControl('', [Validators.required]),
      inputStartDay: new FormControl('01', [Validators.required, Validators.minLength(2), Validators.maxLength(2)]),
      inputStartMonth: new FormControl('01', [Validators.required, Validators.minLength(2), Validators.maxLength(2)]),
      inputStartYear: new FormControl('2020', [Validators.required, Validators.minLength(4), Validators.maxLength(4)]),
      inputStartHour: new FormControl('00', [Validators.required, Validators.minLength(2), Validators.maxLength(2)]),
      inputStartMinutes: new FormControl('00', [Validators.required, Validators.minLength(2), Validators.maxLength(2)]),
      inputEndDay: new FormControl('01', [Validators.required, Validators.minLength(2), Validators.maxLength(2)]),
      inputEndMonth: new FormControl('02', [Validators.required, Validators.minLength(2), Validators.maxLength(2)]),
      inputEndYear: new FormControl('2020', [Validators.required, Validators.minLength(4), Validators.maxLength(4)]),
      inputEndHour: new FormControl('00', [Validators.required, Validators.minLength(2), Validators.maxLength(2)]),
      inputEndMinutes: new FormControl('00', [Validators.required, Validators.minLength(2), Validators.maxLength(2)]),
    })

    this.searchForm.controls["inputStartDay"].disable();
    this.searchForm.controls["inputStartMonth"].disable();
    this.searchForm.controls["inputStartYear"].disable();
    this.searchForm.controls["inputStartHour"].disable();
    this.searchForm.controls["inputStartMinutes"].disable();
    this.searchForm.controls["inputEndDay"].disable();
    this.searchForm.controls["inputEndMonth"].disable();
    this.searchForm.controls["inputEndYear"].disable();
    this.searchForm.controls["inputEndHour"].disable();
    this.searchForm.controls["inputEndMinutes"].disable();
  }

  ngOnInit(): void {}
  

  

  changeType(type: string) {
    this.type = type;

    var element = <HTMLElement> document.getElementById("defineInterval");

    if (type !== "history") {
      element.style.display = 'none';
      this.searchForm.controls["inputStartDay"].disable();
      this.searchForm.controls["inputStartMonth"].disable();
      this.searchForm.controls["inputStartYear"].disable();
      this.searchForm.controls["inputStartHour"].disable();
      this.searchForm.controls["inputStartMinutes"].disable();
      this.searchForm.controls["inputEndDay"].disable();
      this.searchForm.controls["inputEndMonth"].disable();
      this.searchForm.controls["inputEndYear"].disable();
      this.searchForm.controls["inputEndHour"].disable();
      this.searchForm.controls["inputEndMinutes"].disable();
    } else {
      element.style.display = 'inline';
      this.searchForm.controls["inputStartDay"].enable();
      this.searchForm.controls["inputStartMonth"].enable();
      this.searchForm.controls["inputStartYear"].enable();
      this.searchForm.controls["inputStartHour"].enable();
      this.searchForm.controls["inputStartMinutes"].enable();
      this.searchForm.controls["inputEndDay"].enable();
      this.searchForm.controls["inputEndMonth"].enable();
      this.searchForm.controls["inputEndYear"].enable();
      this.searchForm.controls["inputEndHour"].enable();
      this.searchForm.controls["inputEndMinutes"].enable();

    }
  }

  search() {
    if (this.searchForm.invalid) {
      alert("Invalid search parameters. Make sure you filled all necessary inputs.")
      return;
    }
    var select = <HTMLSelectElement> document.getElementById("typeSelect");
    var type = select.options[select.selectedIndex].value
    if (type==="history") {
      this.startDate = this.searchForm.controls['inputStartYear'].value + "-"
                    + this.searchForm.controls['inputStartMonth'].value + "-"
                    + this.searchForm.controls['inputStartDay'].value + "T"
                    + this.searchForm.controls['inputStartHour'].value + ":"
                    + this.searchForm.controls['inputStartMinutes'].value + ":"
                    + "00";
      this.endDate = this.searchForm.controls['inputEndYear'].value + "-"
                    + this.searchForm.controls['inputEndMonth'].value + "-"
                    + this.searchForm.controls['inputEndDay'].value + "T"
                    + this.searchForm.controls['inputEndHour'].value + ":"
                    + this.searchForm.controls['inputEndMinutes'].value + ":"
                    + "00";
    }

    this.searchService.setSearch(this.type, this.address, this.startDate, this.endDate);
    
    this.router.navigate(['/results']);
  }
}
