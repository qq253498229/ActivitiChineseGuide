import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {TaskListComponent} from './task-list/task-list.component';
import {UserListComponent} from './user-list/user-list.component';
import {LoginComponent} from './login/login.component';
import {RouterModule, Routes} from '@angular/router';
import {TaskComponent} from './task/task.component';
import {UserComponent} from './user/user.component';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {LoginGuardGuard} from './login-guard.guard';
import {AppServiceService} from './app-service.service';

const routes: Routes = [
  {path: '', redirectTo: '/task/list', pathMatch: 'full'},
  {
    path: 'task', component: TaskComponent, canActivate: [LoginGuardGuard], children: [
      {path: 'list', component: TaskListComponent}
    ]
  },
  {path: 'login', component: LoginComponent},
  {
    path: 'user', component: UserComponent, children: [
      {path: 'list', component: UserListComponent}
    ]
  }
];

@NgModule({
  declarations: [
    AppComponent,
    TaskListComponent,
    UserListComponent,
    LoginComponent,
    TaskComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],
  providers: [RouterModule, LoginGuardGuard, AppServiceService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
