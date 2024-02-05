import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {TaskResponseDto} from "../dtos/TaskResponseDto";
import {TaskCategoryResponseDto} from "../dtos/TaskCategoryResponseDto";

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private task:BehaviorSubject<TaskResponseDto> = new BehaviorSubject<TaskResponseDto>({} as TaskResponseDto);

  private category:BehaviorSubject<TaskCategoryResponseDto> = new BehaviorSubject<TaskCategoryResponseDto>({} as TaskCategoryResponseDto);
  constructor() { }


  get userTask(): TaskResponseDto{

    return this.task.getValue();
  }

  set userTask(task:TaskResponseDto){
    this.task.next(task);
  }

  set taskCategory(category:TaskCategoryResponseDto){
    this.category.next(category);
  }

  get taskCategory():TaskCategoryResponseDto{
    return this.category.getValue();
  }

}
