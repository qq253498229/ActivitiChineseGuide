import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {map, catchError} from 'rxjs/operators';
import * as _ from 'underscore';

@Injectable()
export class AppServiceService {
  CURRENT_USER = 'current_user';
  USER_LIST = 'user_list';
  TASK_URL = '/api/task';

  constructor(
    private http: HttpClient
  ) {
  }

  login(param: any): Observable<any> {
    return this.http.get('/api/login/' + param['username'] + '/' + param['password']).pipe(
      map(res => {
        if (res === true) {
          this.setUser(param);
          this.addUser(param);
        }
        return res;
      }),
      catchError(err => {
        return Observable.throw(err);
      })
    );
  }


  public getUser(): any {
    return JSON.parse(localStorage.getItem(this.CURRENT_USER));
  }

  setUser(user: any): void {
    localStorage.setItem(this.CURRENT_USER, JSON.stringify(user));
  }

  getUserList(): any[] {
    return JSON.parse(localStorage.getItem(this.USER_LIST));
  }

  setUserList(userList: any[]): void {
    localStorage.setItem(this.USER_LIST, JSON.stringify(userList));
  }

  addUser(user: any): void {
    const userList = this.getUserList() || [];
    const oldUser = _.find(userList, res => res.username === user['username']);
    if (!oldUser) {
      userList.push(user);
    }
    this.setUserList(userList);
  }

  logout(item: any) {
    let userList = this.getUserList();
    userList = _.filter(userList, res => res['username'] !== item['username']);
    this.setUserList(userList);
    localStorage.removeItem(this.CURRENT_USER);
  }

  getTaskList(): Observable<any> {
    const user = this.getUser();
    return this.http.get(this.TASK_URL + '/list/' + user['username']);
  }

  newTask(day, message): Observable<any> {
    const user = this.getUser();
    const param = {
      userId: user['username'],
      day: day,
      message: message
    };
    return this.http.post(this.TASK_URL, param);
  }


  reapplyTask(taskId, day, message) {
    const user = this.getUser();
    const param = {
      userId: user['username'],
      taskId: taskId,
      day: day,
      message: message
    };
    return this.http.put(this.TASK_URL, param);
  }

  passTask(id, message) {
    const user = this.getUser();
    const param = {
      userId: user['username'],
      taskId: id,
      passMessage: message
    };
    return this.http.post(this.TASK_URL + '/pass', param);
  }

  rejectTask(id, message) {
    const user = this.getUser();
    const param = {
      userId: user['username'],
      taskId: id,
      rejectMessage: message
    };
    return this.http.post(this.TASK_URL + '/reject', param);
  }

  getUserGroup() {
    const user = this.getUser();
    return this.http.get('/api/user/' + user['username']);
  }


}
