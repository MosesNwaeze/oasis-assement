import { Routes } from '@angular/router';
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {AuthComponent} from "./auth/auth/auth.component";
import {DashboardWrapperComponent} from "./dashboard/dashboard-wrapper/dashboard-wrapper.component";
import {HomeComponent} from "./home/home.component";

export const routes: Routes = [
  // {
  //   path: '',
  //   component: HomeComponent,
  //
  // },
  {
    path: 'auth',
    component: AuthComponent,
    loadChildren: () => import("./auth/auth.module").then(cmp=>cmp.AuthModule)
  },
  {
    path: 'task-management',
    component: DashboardWrapperComponent,
    loadChildren: () => import("./dashboard/dashboard.module").then(cmp=>cmp.DashboardModule)
  },

  {
    path: "", redirectTo: "/auth", pathMatch: "full"
  },

  {
  path: "**", component: PageNotFoundComponent
  }
];
