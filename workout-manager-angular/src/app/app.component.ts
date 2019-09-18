import { Component } from '@angular/core';
import { ComponentBase } from './_base/base-component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent extends ComponentBase {

  title = 'Workout Manager';
  constructor(private router: Router) {
    super();
  }

}
