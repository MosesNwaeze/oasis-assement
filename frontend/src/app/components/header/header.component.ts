import {Component, Inject, OnInit} from '@angular/core';
import {CommonModule, DOCUMENT} from '@angular/common';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {UserResponseDto} from "../../dtos/UserResponseDto";
import {AuthService} from "../../services/auth.service";
import {AppServiceService} from "../../services/app-service.service";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {

  show = true;
  label = "Sign Out";
  private user: UserResponseDto = {} as UserResponseDto;

  constructor(
      @Inject(DOCUMENT) private document: Document,
      private authService: AuthService,
      private router: Router,
      private appService: AppServiceService,
      private route: ActivatedRoute,
  ) {
  }


  ngOnInit(): void {

    const url = new URL(this.document.URL);
    const pathname = url.pathname;
    this.show = !(pathname === '/auth/login' || pathname === '/auth/register');

    const userId = Number(this.route.snapshot.paramMap.get("userId")) || -1;

    this.fetchUser(userId);
  }


  get userData(): UserResponseDto {
    return this.user;
  }

  set userData(data: UserResponseDto) {
    this.user = data;
  }

  auth() {

    if (this.label === 'Sign Out') {
      this.authService.logout();
      this.router.navigate(["/auth/login"]).then(r => console.log(r))
    }

  }

  fetchUser(userId: number): void {
    if(userId !== -1){
      this.appService
          .getUserById(userId)
          .subscribe({
            next: (value) => {
              this.userData = value;
              this.authService.userData = value;
            }
          })
    }

  }
}
