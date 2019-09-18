import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { MomentModule } from 'ngx-moment';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { RoutingRoutes } from './routing.routing';
import { WorkoutComponent } from './workout/workout.component';
import { CategoryComponent } from './category/category.component';
import { WorkoutPlusComponent } from './workout-plus/workout-plus.component';
import { ChartComponent } from './chart/chart.component';

@NgModule({
   declarations: [
      AppComponent,
      WorkoutComponent,
      CategoryComponent,
      WorkoutPlusComponent,
      ChartComponent
   ],
   entryComponents: [
      WorkoutPlusComponent
   ],
   imports: [
      BrowserModule,
      FormsModule,
      HttpClientModule,
      BsDatepickerModule.forRoot(),
      TimepickerModule.forRoot(),
      MomentModule,
      NgbModule.forRoot(),
      ReactiveFormsModule,
      RoutingRoutes
   ],
   providers: [
   ],
   bootstrap: [
      AppComponent
   ]
})
export class AppModule { }
