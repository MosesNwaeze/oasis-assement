import {Inject, Injectable} from '@angular/core';
import {CanActivate, Router, UrlTree} from '@angular/router';
import {AppServiceService} from "../../services/app-service.service";
import {AuthService} from "../../services/auth.service";
import {DOCUMENT} from "@angular/common";

@Injectable({
  providedIn: 'root',
})
export class checkLoginGuard implements CanActivate {
  constructor(
      private authService: AuthService,
      private router:Router,
      private appService:AppServiceService,
      @Inject(DOCUMENT) private document:Document
  ) {}

  window:Window = this.document.defaultView as Window;

  canActivate(): boolean | UrlTree{

    const _isUserLogin = this.window.localStorage ?
        Boolean(this.window.localStorage.getItem("isUserLogin")) : false;
    const _token = this.window.localStorage ?
        this.window.localStorage.getItem("token") : "";

    const isUserLogin = this.authService.isUserLogin || _isUserLogin;
    const token = this.appService.userToken || _token;

    if(!isUserLogin || !token){

        return true;
    }

    return  this.router.parseUrl("/task-management/dashboard");
  }
}
