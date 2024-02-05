import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {AuthRequest} from "../../dtos/AuthRequest";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(private authService:AuthService) {
  }

  loginForm = new FormGroup({
    username: new FormControl<string>("", [Validators.required]),
    password: new FormControl<string>("", [Validators.required])
  })

  handleLogin(): void{
    if(this.loginForm.valid){
      const data = this.loginForm.value as AuthRequest;
      this.authService.login(data);
    }

  }

}

export type LoginFormType = {
  email:string;
  password:string;
}
