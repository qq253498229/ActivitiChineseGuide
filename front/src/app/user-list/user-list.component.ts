import {Component, OnInit} from '@angular/core';
import {AppServiceService} from '../app-service.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  userList: any[];
  user: any;

  constructor(
    private service: AppServiceService
  ) {
  }

  ngOnInit() {
    this.userList = this.service.getUserList();
    this.user = this.service.getUser();
    console.log(this.userList);
  }

  logout(item: any) {
    this.service.logout(item);
    this.ngOnInit();
  }

  change(item: any) {
    this.service.setUser(item);
    this.ngOnInit();
  }
}
