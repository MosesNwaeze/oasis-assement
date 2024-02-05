import {CompletionStatusEnum, PriorityEnum} from "./TaskRequestDto";
import {UserResponseDto} from "./UserResponseDto";
import {TaskCategoryResponseDto} from "./TaskCategoryResponseDto";

export type TaskResponseDto = {
    title:string;
    description:string;
    priority:PriorityEnum;
    dueDate:Date;
    completionStatus:CompletionStatusEnum;
    status: StatusEnum;
    taskId:number;
    appUser?: UserResponseDto;
    taskCategory?:TaskCategoryResponseDto;
}

export type StatusEnum = {
   status: 'ACTIVE' | 'NOT_ACTIVE'
}
