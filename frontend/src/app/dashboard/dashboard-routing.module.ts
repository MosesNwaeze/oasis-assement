import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardWrapperComponent} from "./dashboard-wrapper/dashboard-wrapper.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {AddTaskComponent} from "./add-task/add-task.component";
import {checkLoginGuard} from "../auth/guards/check-login.guard";
import {DashboardGuard} from "../dashboard.guard";
import {UpdateTaskComponent} from "./update-task/update-task.component";
import {UpdateUserComponent} from "./update-user/update-user.component";


const routes: Routes = [
  {
    canActivate: [DashboardGuard],
    path: "",
    children:[
      {
        path : "",
        children: [
          {
            path: 'dashboard/:userId',
            component: DashboardComponent,

          },

          {
            path: 'add-task/:userId',
            component: AddTaskComponent
          },

          {
            path:'update/:userId/:taskId',
            component: UpdateTaskComponent
          },

          {
            path:'update-info/:userId',
            component:UpdateUserComponent,
          },

          {
            path: "", redirectTo: "/task-management/dashboard", pathMatch: 'full'
          }
        ]
      }


    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
