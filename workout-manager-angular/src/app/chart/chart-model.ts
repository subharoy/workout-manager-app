import * as moment from 'moment';

import { WorkoutModel } from '../workout/workout-model';
import { DateTimeService } from '../_services/date-time.service';

export class ChartModel {
    public burntCalories = 0.0;
    public category: string;
    public date: Date;
    public duration = 0.0;
    public title: string;
    constructor(public workout: WorkoutModel) {
        if (workout) {
            let actualstartdate: Date = moment(workout.startdate + " " + workout.starttime).toDate();
            let actualenddate: Date = moment(workout.enddate + " " + workout.endtime).toDate();
            this.duration = DateTimeService.getDiffInMins(actualstartdate, actualenddate);
            this.burntCalories = workout.burnrate * this.duration;
            this.category = workout.category.category;
            //this.date = DateTimeService.format(actualstartdate, DateTimeService.getISODateFormat());
            this.date = actualstartdate;
            this.title = workout.title;
        }
    }
}
