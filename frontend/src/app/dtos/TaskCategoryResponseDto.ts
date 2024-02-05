import {TaskResponseDto} from "./TaskResponseDto";

export type TaskCategoryResponseDto = {
    cateName: string;
    tasks: TaskResponseDto[];
    categoryId:number;
}
