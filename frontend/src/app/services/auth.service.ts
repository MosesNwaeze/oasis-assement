import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {UserResponseDto} from "../dtos/UserResponseDto";
import {AppServiceService} from "./app-service.service";
import {Router} from "@angular/router";
import {AuthRequest} from "../dtos/AuthRequest";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private user: BehaviorSubject<UserResponseDto> = new BehaviorSubject<UserResponseDto>({} as UserResponseDto);
  private isLogin:BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  constructor(
      private appService: AppServiceService,
      private router: Router,
  ) {

  }

  logout() {
    this.userData = {} as UserResponseDto;
    this.appService.userToken = "";
    this.isUserLogin = false;
    localStorage.removeItem('token')
    localStorage.removeItem('isUserLogin');
  }

  set userData(data: UserResponseDto){
    this.user.next(data);
  }

  get userData(): UserResponseDto {
    return this.user.getValue();
  }

  set isUserLogin(login: boolean){
    this.isLogin.next(login);
  }

  get isUserLogin():boolean{
    return  this.isLogin.getValue();
  }

  login(data: AuthRequest): void {
    this.appService.authenticate(data)
        .subscribe({
          next: ({appUser,token}) => {
            this.userData = appUser;
            this.appService.userToken = token;
            this.isUserLogin = true;
            localStorage.setItem('token',token)
            localStorage.setItem('isUserLogin',JSON.stringify(true));
            this.router.navigate([`/task-management/dashboard/` + appUser.userId])
                .then(r => console.log(r))
          },
          error: (err) => {
            console.log("error from server=====>", err)
          }
        })

  }


}
