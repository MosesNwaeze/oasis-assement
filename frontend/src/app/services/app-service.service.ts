import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {TaskResponseDto} from "../dtos/TaskResponseDto";
import {TaskRequestDto} from "../dtos/TaskRequestDto";
import {TaskCategoryRequestDto} from "../dtos/TaskCategoryRequestDto";
import {TaskCategoryResponseDto} from "../dtos/TaskCategoryResponseDto";
import {UserRequestDto} from '../dtos/UserRequestDto';
import {UserResponseDto} from "../dtos/UserResponseDto";
import {AuthRequest} from "../dtos/AuthRequest";
import {AuthResponse} from "../dtos/AuthResponse";
import {DOCUMENT} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {

  private BASE_URL = 'http://localhost:8080/api/v1/';
  private tokenBS: BehaviorSubject<string> =
    new BehaviorSubject<string>("");
  private window: Window = this.document.defaultView as Window;

  private token = this.window.localStorage ?
    this.window.localStorage.getItem("token")
    :
    "";

  constructor(
    private http: HttpClient,
    @Inject(DOCUMENT) private document: Document
  ) {
  }

  set userToken(token: string) {
    this.tokenBS.next(token);
  }

  get userToken(): string {
    return this.tokenBS.getValue();
  }


  createTask(data: TaskRequestDto): Observable<TaskResponseDto> {
    return this.http.post<TaskResponseDto>(this.BASE_URL + "task", data, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.userToken || this.token}`
      }
    });
  }

  updateTask(taskId: number, userId: number, data: TaskRequestDto): Observable<TaskResponseDto> {
    return this.http.put<TaskResponseDto>(`${this.BASE_URL}update/${taskId}/${userId}`, data, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.userToken || this.token}`
      }
    });
  }

  deleteTask(taskId: number, userId: number): Observable<Object> {
    return this.http.delete(`${this.BASE_URL}delete/${taskId}/${userId}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.userToken || this.token}`
      }
    });
  }

  getTaskByIdAndUserId(taskId: number, userId: number): Observable<TaskResponseDto> {
    return this.http.get<TaskResponseDto>(this.BASE_URL + "task/" + taskId + "/" + userId, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.userToken || this.token}`
      }
    });
  }

  getAllTask(): Observable<TaskResponseDto[]> {
    return this.http.get<TaskResponseDto[]>(this.BASE_URL + "tasks", {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.userToken || this.token}`
      }
    });
  }

  getTaskById(id: number): Observable<TaskResponseDto> {
    return this.http.get<TaskResponseDto>(`${this.BASE_URL}tasks/${id}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.userToken || this.token}`
      }
    });
  }


  searchTask(title: string, description: string, priority: string, completionStatus: string): Observable<TaskResponseDto[]> {
    const params = new HttpParams();

    params.append('title', title);
    params.append("description", description);
    params.append("priority", priority);
    params.append("completionStatus", completionStatus);

    return this.http.get<TaskResponseDto[]>(
      `${this.BASE_URL}search?title=${title}&description=${description}&priority=${priority}&completionStatus=${completionStatus}`
      , {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${this.userToken || this.token}`
        }
      });
  }


  createCategory(data: TaskCategoryRequestDto): Observable<TaskCategoryResponseDto> {
    return this.http.post<TaskCategoryResponseDto>(this.BASE_URL + "task-category", data, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.userToken || this.token}`
      }
    });
  }

  addTaskToCategory(categoryName: string, task: TaskResponseDto): Observable<TaskCategoryResponseDto> {
    return this.http.post<TaskCategoryResponseDto>(this.BASE_URL + `add-task-to-category/${categoryName}`, task, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.userToken || this.token}`
      }
    });
  }


  createUser(data: UserRequestDto): Observable<UserResponseMapType> {
    return this.http.post<UserResponseMapType>(this.BASE_URL + "register", data);

  }


  updateUser(id: number, data: UserRequestDto): Observable<UserResponseDto> {
    return this.http.put<UserResponseDto>(`${this.BASE_URL}update/user/${id}`, data, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.userToken || this.token}`
      }
    });
  }


  deleteUser(id: number): Observable<Object> {
    return this.http.delete(`${this.BASE_URL}delete/user/${id}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.userToken || this.token}`
      }
    });
  }


  getAllUser(): Observable<UserResponseDto[]> {
    return this.http.get<UserResponseDto[]>(this.BASE_URL + "users", {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.userToken || this.token}`
      }
    });
  }


  getTasksByUser(userId: number): Observable<TaskResponseDto[]> {
    return this.http.get<TaskResponseDto[]>(`${this.BASE_URL}tasks-by-user/${userId}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.userToken || this.token}`
      }
    });
  }


  getUserById(id: number): Observable<UserResponseDto> {
    return this.http.get<UserResponseDto>(`${this.BASE_URL}users/${id}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.userToken || this.token}`
      }
    });
  }

  authenticate(data: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(this.BASE_URL + "authenticate", data)

  }


}


export type UserResponseMapType = {
  token: string;
  payload: UserResponseDto;
}
