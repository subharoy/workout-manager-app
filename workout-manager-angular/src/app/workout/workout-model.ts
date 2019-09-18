import { CategoryModel } from '../category/category-model';
import { WorkoutService } from '../_services/workout.service';
import { Time } from '@angular/common';

export class WorkoutModel {

    constructor(
        public title: string,
        public category: CategoryModel,
        public notes: string,
        public burnrate: number,
        public editing: boolean = false,
        public startdate?: Date,
        public starttime?: Date,
        public enddate?: Date,
        public endtime?: Date,
        public comment?: string,
        public status: boolean = false,
        public id?: number,
    ) {}

    toString(): string {
        return JSON.stringify(this);
    }
}
