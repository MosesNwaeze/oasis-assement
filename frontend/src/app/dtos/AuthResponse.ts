import {UserResponseDto} from "./UserResponseDto";

export type AuthResponse = {
    token:string;
    appUser: UserResponseDto;
}

