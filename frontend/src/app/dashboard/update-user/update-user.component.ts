import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {FooterComponent} from "../../components/footer/footer.component";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";
import {UserResponseDto} from "../../dtos/UserResponseDto";
import {AppServiceService} from "../../services/app-service.service";
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UserRequestDto} from "../../dtos/UserRequestDto";

@Component({
    selector: 'app-update-user',
    standalone: true,
    imports: [
        HeaderComponent,
        FooterComponent,
        FormsModule,
        NgIf,
        ReactiveFormsModule
    ],
    templateUrl: './update-user.component.html',
    styleUrl: './update-user.component.css'
})
export class UpdateUserComponent implements OnInit {

    user: UserResponseDto = {} as UserResponseDto;

    constructor(
        private appService: AppServiceService,
        private authService: AuthService,
        private router: Router,
        private route:ActivatedRoute,
    ) {
    }

    updateForm = new FormGroup({
        firstName: new FormControl<string>("", [Validators.required]),
        lastName: new FormControl<string>("", [Validators.required]),
        email: new FormControl<string>("", [Validators.required]),
        // password: new FormControl<string>("", [Validators.required]),
        roles: new FormControl<string>("", [Validators.required])
    })


    handleUpdate(): void {
        const userId = Number(this.route.snapshot.paramMap.get("userId"));
        if (this.updateForm.valid) {
            const data: UserRequestDto = this.updateForm?.value as UserRequestDto;
            this.appService.updateUser(userId, data)
                .subscribe({
                    next: (user: UserResponseDto) => {
                        this.router.navigate([`/task-management/dashboard/${user.userId}`])
                            .then(r => console.log(r));
                    },
                    error: (e) => {
                        console.log(e)
                    }
                })
        }
    }

    fetchUserToBeUpdated(): void {
        const userId = Number(this.route.snapshot.paramMap.get("userId"));
        this.appService
            .getUserById(userId)
            .subscribe(({
                next: (value) => {

                    this.updateForm
                        .setValue({
                            email: value.email,
                            firstName: value.firstName,
                            lastName: value.lastName,
                            roles: value.roles
                        })

                },
                error: (err) => {
                    console.log(err);
                }
            }))
    }

    ngOnInit(): void {
        this.fetchUserToBeUpdated();
    }

}
