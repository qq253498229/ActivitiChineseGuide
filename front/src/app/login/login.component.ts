import {Component, OnInit} from '@angular/core';
import {AppServiceService} from '../app-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  model: any = {
    username: '',
    password: ''
  };

  constructor(
    private service: AppServiceService
  ) {
  }

  ngOnInit() {
  }

  login() {
    console.log(this.model);
    this.service.login(this.model).subscribe(res => {
      if (res === true) {
        this.service.addUser(this.model);
      } else if (res === false) {
        alert('用户名或密码错误');
      } else {
        alert('系统错误');
      }
    });
  }

}
