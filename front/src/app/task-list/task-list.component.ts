import {Component, OnInit} from '@angular/core';
import {AppServiceService} from '../app-service.service';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  taskList: any[] = [];
  groupId: string;
  newFlag = false;
  passFlag = false;
  rejectFlag = false;
  reapplyFlag = false;
  day = 0;
  message: string;


  constructor(
    private service: AppServiceService
  ) {
  }

  ngOnInit() {
    this.service.getUserGroup().subscribe(res => {
      this.groupId = res[0]['groupId'];
    });
    this.service.getTaskList().subscribe(res => {
      console.log(res);
      this.taskList = res;
    });
  }

  newTask() {
    this.service.newTask(this.day, this.message).subscribe(res => {
      console.log(res);
    });
  }

  pass(id) {
    this.service.passTask(id, this.message).subscribe(res => {
      console.log(res);
    });
  }

  reject(id) {
    console.log(id);
    this.service.rejectTask(id, this.message).subscribe(res => {
      console.log(res);
    });
  }

  reapply(id, day) {
    this.service.reapplyTask(id, day, this.message).subscribe(res => {
      console.log(res);
    });
  }
}
