import { Component, ViewChild, OnDestroy } from '@angular/core';
import * as moment from 'moment';
import { BsDaterangepickerDirective, BsDaterangepickerConfig } from 'ngx-bootstrap/datepicker';
import { api, Chart } from 'taucharts';
import 'taucharts/dist/plugins/crosshair';
import 'taucharts/dist/plugins/legend';
import 'taucharts/dist/plugins/tooltip';

import { DateTimeService } from '../_services/date-time.service';
import { WorkoutService } from '../_services/workout.service';
import { WorkoutModel } from '../workout/workout-model';
import { ChartModel } from './chart-model';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnDestroy {

  public daterange: Date[] = [new Date(), new Date()];

  @ViewChild(BsDaterangepickerDirective)
  public daterangePicker: BsDaterangepickerDirective;

  // Date Picker Custom Configuration
  public daterangePickerConfig: Partial<BsDaterangepickerConfig> = {
    containerClass: 'theme-dark-blue',
    displayMonths: 2,
    showWeekNumbers: false
  };

  private chartArea: Chart;

  constructor(
    private restService: WorkoutService
  ) { }

  ngOnDestroy() { this.destroyChart(); }

  private createChart(data: ChartModel[], chartType: string) {
    // Setup a plotting area for chart
    let weekdays: string[] = ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'];
    api.tickFormat.add('byWeekday', function(x: Date): string {return weekdays[x.getDay().toString()];});
    let tformat: string = chartType;
    if (chartType === 'day') {
      tformat='byWeekday';
    }
    this.chartArea = new Chart({
      data: data,
      label: 'Calorie Consumption Chart',
      type: 'stacked-bar',
      x: 'date',
      y: 'burntCalories',
      color: 'category',
      size: 'burntCalories',
      guide: {
        showGridLines: 'y',
        x: { nice: false, label: { padding: 0, text: 'Date Range' }, "tickFormat": tformat, 
          "tickPeriod": chartType },
        y: { nice: false, label: { padding: 0, text: 'Total Calories Burnt' } },
        
      },
      dimensions: {
        date: {type: 'order', scale: 'period'},
        burntCalories: {type: 'measure', scale: 'linear'}
      },
      settings: {
        asyncRendering: true,
        autoRatio: true,
        excludeNull: true,
      },
      plugins: [
        api.plugins.get('crosshair')({ xAxis: false, yAxis: true }),
        api.plugins.get('legend')   ({ position: 'left' }),
        api.plugins.get('tooltip')({
          fields: ['date', 'category', 'title', 'burntCalories'],
          formatters: {
                     date: {label: 'Date',           format: (value: Date) => value.toString()},
                 category: {label: 'Category',       format: (value: string) => value.valueOf()},
                    title: {label: 'Workout',        format: (value: string) => value.valueOf()},
            burntCalories: {label: 'Calories Burnt', format: (value: number) => value.toFixed(1)},
          }
        }),
      ]
    });
    this.chartArea.renderTo('#chart-area');
    this.chartArea.refresh();
  }

  private destroyChart() {
    if (this.chartArea) { this.chartArea.destroy(); }
  }

  /* @HostListener('window: scroll')
  onscrollEvent() { this.daterangePicker.hide(); } */
  public refreshChart(daterange: Date[], chartType: string) {

    this.destroyChart();
    if (!daterange) {
      daterange = [new Date(), new Date()];
    }

    const from = moment(moment(daterange[0]).format(DateTimeService.getISODateFormat())).toDate();
    const to = moment(moment(daterange[1]).format(DateTimeService.getISODateFormat())).toDate();

    this.restService.search(from, to).subscribe(
      res => {
        if (res.status === 200 && res.body) {
          const workouts = WorkoutService.cloneAll(res.body);
          const chartData = this.prepareChartData(workouts);
          if (chartData.length > 0) {
            this.createChart(chartData, chartType);
            this.daterange.push(from);
            this.daterange.push(to);
          }
        } else if (res.status === 204) {
          console.log('No workouts found');
        }
      },
      err => { },
       () => { }
    );
  }

  public refreshForCurrentWeek() {
    const from = moment().startOf('week').toDate();
    const to = moment().endOf('week').toDate();
    this.refreshChart([from, to], 'day');
  }

  public refreshForCurrentMonth() {
    const from = moment().startOf('month').toDate();
    const to = moment().endOf('month').toDate();
    this.refreshChart([from, to], 'week');
  }

  public refreshForCurrentYear() {
    const from = moment().startOf('year').toDate();
    const to = moment().endOf('year').toDate();
    this.refreshChart([from, to], 'month');
  }

  private prepareChartData(workouts: WorkoutModel[]) {
    const chartData: ChartModel[] = [];
    workouts.forEach(workout =>
      chartData.push(new ChartModel(workout))
    );
    return chartData;
  }

}
