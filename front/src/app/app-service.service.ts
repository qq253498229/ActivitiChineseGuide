import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {map, catchError} from 'rxjs/operators';
import * as _ from 'underscore';

@Injectable()
export class AppServiceService {
  CURRENT_USER = 'current_user';
  USER_LIST = 'user_list';

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
    return this.http.get('/api/task/list/' + user['username']);
  }

  newTask(): Observable<any> {
    return this.http.get('/api/task/new');
  }

  passTask(id: any) {
    return this.http.get('/api/task/pass/' + id);
  }

  rejectTask(id: any) {
    return this.http.get('/api/task/reject/' + id);
  }
}
