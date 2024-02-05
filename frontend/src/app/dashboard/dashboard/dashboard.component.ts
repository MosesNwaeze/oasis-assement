import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {FooterComponent} from "../../components/footer/footer.component";
import {ToggleSelectDirective} from "../../toggle-select.directive";
import {NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {AppServiceService} from "../../services/app-service.service";
import {UserResponseDto} from "../../dtos/UserResponseDto";
import {TaskResponseDto} from "../../dtos/TaskResponseDto";
import {AuthService} from "../../services/auth.service";
import {TaskService} from "../../services/task.service";
import {TaskCategoryResponseDto} from "../../dtos/TaskCategoryResponseDto";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [
        HeaderComponent,
        FooterComponent,
        ToggleSelectDirective,
        NgIf,
        RouterLink,
        NgForOf,
        ReactiveFormsModule,
        FormsModule,

    ],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.css',
    providers: []
})
export class DashboardComponent implements OnInit {

    tasks: TaskResponseDto[] = [];
    userId = 0;


    filterForm = new FormGroup({
        title: new FormControl<string>(""),
        description: new FormControl<string>(""),
        priority: new FormControl<string>(""),
        completionStatus: new FormControl<string>(""),
        // dueDate: new FormControl<string>("",[Validators.required])
    })

    constructor(
        private appService: AppServiceService,
        private authService: AuthService,
        private route: ActivatedRoute,
        private router: Router,
        private taskService: TaskService,
    ) {
    }

    ngOnInit(): void {
        this.userId = Number(this.route.snapshot.paramMap.get("userId"));


        this.fetchTask();


    }


    update(taskId: number): void {
        this.router.navigate([`/task-management/update/${this.userId}/${taskId}`])
            .then((r) => console.log(r));

    }

    delete(taskId: number): void {
        const userid = this.userId;
        this.appService.deleteTask(taskId, userid)
            .subscribe({
                next: (value) => {
                    if (value) {
                        window.location.reload();
                    }
                },
                error: (e) => {
                    console.log(e)
                }
            })

    }

    fetchTask(): void {
        this.appService.getTasksByUser(this.userId)
            .subscribe({
                next: (value) => {

                    this.tasks = value;
                    this.taskService.taskCategory = value[0]?.taskCategory as TaskCategoryResponseDto
                    this.authService.userData = value[0]?.appUser as UserResponseDto;

                },
                error: (err) => {
                    console.log("fetch all task error=====>", err)
                },
            })

    }

    handleTaskFilter() {
      console.log(this.filterForm.value, "filter forms=====>")
        this.appService
            .searchTask(
                this.filterForm.controls.title.value as string,
                this.filterForm.controls.description.value as string,
                this.filterForm.controls.priority.value as string,
                this.filterForm.controls.completionStatus.value as string
            )
            .subscribe({
                next: value => {
                    this.tasks = value;
                },
                error: err => {
                    console.log(err);
                }
            })

    }
}
