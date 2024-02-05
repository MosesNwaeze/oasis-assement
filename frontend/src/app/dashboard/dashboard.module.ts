import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardRoutingModule } from './dashboard-routing.module';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {HeaderComponent} from "../components/header/header.component";


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    NgbModule,

  ],
  providers: []
})
export class DashboardModule { }
