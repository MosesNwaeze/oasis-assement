import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AuthRoutingModule,
    NgbModule,
    CommonModule
  ],
  providers: []
})
export class AuthModule { }
