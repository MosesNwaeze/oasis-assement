import { Component } from '@angular/core';
import {RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-dashboard-wrapper',
  standalone: true,
  imports: [
    RouterOutlet
  ],
  templateUrl: './dashboard-wrapper.component.html',
  styleUrl: './dashboard-wrapper.component.css'
})
export class DashboardWrapperComponent {

}
