import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {checkLoginGuard} from "./guards/check-login.guard";

const routes: Routes = [
  {
    path: "",
    children: [
      {
        path: "",
        children: [
          {
            path: 'login',
            component: LoginComponent,
            title: 'Login Page',
            canActivate: [checkLoginGuard],
          },
          {
            path: 'register',
            component: RegisterComponent
          },

          {
            path: "", redirectTo: '/auth/login', pathMatch: 'full'
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
export class AuthRoutingModule {
}
