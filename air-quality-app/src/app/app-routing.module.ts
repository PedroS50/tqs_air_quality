import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AnalysisResultsComponent } from './analysis-results/analysis-results.component';


const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'results', component: AnalysisResultsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
