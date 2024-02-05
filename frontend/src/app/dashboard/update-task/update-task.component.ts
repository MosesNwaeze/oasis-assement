import {Component, inject} from '@angular/core';
import {FooterComponent} from "../../components/footer/footer.component";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {HeaderComponent} from "../../components/header/header.component";
import {NgIf} from "@angular/common";
import {NgbCalendar, NgbDatepicker, NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {AppServiceService} from "../../services/app-service.service";
import {TaskService} from "../../services/task.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {CompletionStatusEnum, PriorityEnum, TaskRequestDto} from "../../dtos/TaskRequestDto";
import moment from "moment/moment";
import {TaskResponseDto} from "../../dtos/TaskResponseDto";

@Component({
    selector: 'app-update-task',
    standalone: true,
    imports: [
        FooterComponent,
        FormsModule,
        HeaderComponent,
        NgIf,
        NgbDatepicker,
        ReactiveFormsModule
    ],
    templateUrl: './update-task.component.html',
    styleUrl: './update-task.component.css'
})
export class UpdateTaskComponent {

    task: TaskResponseDto = {} as TaskResponseDto;
    userId = 0;

    taskUpdateForm = new FormGroup({
        title: new FormControl<string>('', [
            Validators.required,
            Validators.maxLength(300),
            Validators.minLength(5),
            Validators.pattern(/^[A-Za-z]+(?: [A-Za-z]+)?$/)
        ]),
        description: new FormControl<string>('',
            [
                Validators.required,
                Validators.maxLength(400),
                Validators.minLength(5),
                Validators.pattern(/^[A-Za-z]+(?: [A-Za-z]+)?$/)
            ]),
        priority: new FormControl<PriorityEnum>({} as PriorityEnum, [Validators.required]),
        completionStatus: new FormControl<CompletionStatusEnum>({} as CompletionStatusEnum, [Validators.required]),
        dueDate: new FormControl<Date | string>({} as Date, [Validators.required])
    })

    today = inject(NgbCalendar).getToday();

    model: NgbDateStruct = {} as NgbDateStruct;
    date: { year: number; month: number } = {year: 0, month: 0};

    ngOnInit(): void {

        this.fetchTaskTobeUpdated();

        this.task = this.taskService.userTask;
        this.userId = Number(this.route.snapshot.paramMap.get("userId"));


    }

    constructor(
        private appService: AppServiceService,
        private taskService: TaskService,
        private router: Router,
        private authService: AuthService,
        private route: ActivatedRoute
    ) {
    }

    handleFormUpdate(): void {
        const data = {...this.taskUpdateForm.value, appUser: this.authService.userData} as TaskRequestDto;
        const taskId = Number(this.route.snapshot.paramMap.get("taskId"));
        const userId = this.authService.userData.userId;

        console.log(data)

        this.appService.updateTask(Number(taskId), userId, data)
            .subscribe({
                next: (task) => {
                    this.taskService.userTask = task;
                    console.log("updated task===>",task)
                    this.router.navigate([`/task-management/dashboard/${this.authService.userData.userId}`])
                        .then((r) => console.log(r))
                },
                error: (err) => {
                    console.log(err, "====>");
                }
            })

    }

    captureDate() {
        const dueDate = new Date(this.model.year, this.model.month - 1, this.model.day);
        const formattedDate = moment(dueDate).format("yyyy-MM-DDTHH:mm:ss")

        this.taskUpdateForm
            .patchValue({
                dueDate: formattedDate
            })

    }

    fetchTaskTobeUpdated(): void {
        const taskId = Number(this.route.snapshot.paramMap.get("taskId"));
        const userId = Number(this.route.snapshot.paramMap.get("userId"));

        this.appService
            .getTaskByIdAndUserId(taskId, userId)
            .subscribe({
                next: (value) => {
                    console.log(value,"task value")
                    this.taskUpdateForm
                        .setValue({
                            title: value.title,
                            completionStatus: value.completionStatus as CompletionStatusEnum,
                            description: value.description,
                            dueDate: value.dueDate,
                            priority: value.priority as PriorityEnum,
                        })
                    this.taskService.userTask = value;

                },
                error: (err) => {
                    console.log(err,"err===>");
                }
            })
    }


}
