import {StatusEnum} from "./TaskResponseDto";

export type UserResponseDto = {
    email:string;
    firstName:string;
    lastName:string;
    status:StatusEnum;
    userId:number;
    roles:string;
    username:string;
}
