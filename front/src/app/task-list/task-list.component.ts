import {Component, OnInit} from '@angular/core';
import {AppServiceService} from '../app-service.service';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  taskList: any[] = [];

  constructor(
    private service: AppServiceService
  ) {
  }

  ngOnInit() {
    this.service.getTaskList().subscribe(res => {
      console.log(res);
      this.taskList = res;
    });
  }

  newTask() {
    this.service.newTask().subscribe(res => {
      console.log(res);
    });
  }

  pass(id) {
    console.log(id);
    this.service.passTask(id).subscribe(res => {
      console.log(res);
    });
  }

  reject(id) {
    console.log(id);
    this.service.rejectTask(id).subscribe(res => {
      console.log(res);
    });
  }
}
