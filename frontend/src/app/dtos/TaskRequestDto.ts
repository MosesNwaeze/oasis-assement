import {UserResponseDto} from "./UserResponseDto";

export type TaskRequestDto = {
    title?: string;
    description?:string;
    priority?: PriorityEnum;
    dueDate?: Date;
    completionStatus?: CompletionStatusEnum;
    appUser?: UserResponseDto;
}

export type PriorityEnum ={
  priority: 'HIGH' |'MEDIUM'|'LOW';
}

export type CompletionStatusEnum = {
   completionStatus: 'DONE' | 'IN_PROGRESS' | 'NOT_STARTED'
}
