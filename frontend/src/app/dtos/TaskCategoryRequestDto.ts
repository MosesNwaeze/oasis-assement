import {TaskRequestDto} from "./TaskRequestDto";

export type TaskCategoryRequestDto = {

    cateName: string;
    tasks: TaskRequestDto[];
}
