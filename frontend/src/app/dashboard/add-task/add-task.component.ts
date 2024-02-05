import {Component, inject, OnInit} from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {FooterComponent} from "../../components/footer/footer.component";
import {JsonPipe, NgIf} from "@angular/common";
import {FormControl, FormGroup, FormsModule, NgForm, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgbCalendar, NgbDatepickerModule, NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import moment from "moment";
import {TaskRequestDto} from "../../dtos/TaskRequestDto";
import {AppServiceService} from "../../services/app-service.service";
import {TaskService} from "../../services/task.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {switchMap} from "rxjs/operators";
import {ObservableInput} from "rxjs";

@Component({
    selector: 'app-add-task',
    standalone: true,
    imports: [
        HeaderComponent,
        FooterComponent,
        NgbDatepickerModule, FormsModule, JsonPipe, ReactiveFormsModule, NgIf
    ],
    templateUrl: './add-task.component.html',
    styleUrl: './add-task.component.css'
})
export class AddTaskComponent implements OnInit {

    taskForm = new FormGroup({
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
        priority: new FormControl<string>("", [Validators.required]),
        completionStatus: new FormControl<string>("", [Validators.required]),
        dueDate: new FormControl<string>("",[Validators.required])
    })

    today = inject(NgbCalendar).getToday();

    model: NgbDateStruct = {} as NgbDateStruct;
    date: { year: number; month: number } = {year: 0, month: 0};

    ngOnInit(): void {

        console.log("meee")
        console.log(this.route
            .snapshot.paramMap.get("userId"));
    }

    constructor(
        private appService:AppServiceService,
        private taskService:TaskService,
        private router:Router,
        private authService:AuthService,
        private route:ActivatedRoute,
    ) {
    }

    handleFormSubmit(): void {
        const data = {...this.taskForm.value,appUser:this.authService.userData} as TaskRequestDto;
        this.appService.createTask(data)
            .subscribe({
                next:(task)=>{
                    this.taskService.userTask = task;
                    console.log(task)
                    this.router.navigate([`/task-management/dashboard/${this.authService.userData.userId}`])
                        .then((r)=>console.log(r))

                },
                error: (err)=>{
                    console.log(err,"====>");
                }
            })

    }

    captureDate() {
        const dueDate = new Date(this.model.year, this.model.month - 1, this.model.day);
        const formattedDate = moment(dueDate).format("yyyy-MM-DDTHH:mm:ss")

        this.taskForm
            .patchValue({
                dueDate: formattedDate
            })

    }


}

