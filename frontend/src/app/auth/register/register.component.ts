import {Component, Inject} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {DOCUMENT, NgIf} from "@angular/common";
import {AppServiceService} from "../../services/app-service.service";
import {UserRequestDto} from "../../dtos/UserRequestDto";
import {UserResponseDto} from "../../dtos/UserResponseDto";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  user: UserResponseDto = {} as UserResponseDto;

  constructor(
    private appService: AppServiceService,
    private authService: AuthService,
    private router: Router
  ) {
  }

  registerForm = new FormGroup({
    firstName: new FormControl<string>("", [Validators.required]),
    lastName: new FormControl<string>("", [Validators.required]),
    username: new FormControl<string>("", [Validators.required]),
    email: new FormControl<string>("", [Validators.required]),
    password: new FormControl<string>("", [Validators.required]),
    roles: new FormControl<string>("", [Validators.required])
  })


  handleRegister(): void {
    if (this.registerForm.valid) {
      const data: UserRequestDto = this.registerForm?.value as UserRequestDto;
      this.appService.createUser(data)
        .subscribe({
          next:({payload,token})=>{
            this.authService.userData = payload;
            this.appService.userToken = token;
            this.authService.isUserLogin = true;
            localStorage.setItem('token',token)
            localStorage.setItem('isUserLogin',JSON.stringify(true));
            this.router.navigate([`/task-management/dashboard/${payload.userId}`])
                .then(r => console.log(r));
            },
          error: (e)=>{
           console.log(e)
          }
        })
    }
  }
}

