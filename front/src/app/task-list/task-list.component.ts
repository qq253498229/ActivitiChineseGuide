import {Component, OnInit} from '@angular/core';
import {AppServiceService} from '../app-service.service';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

  constructor(
    private service: AppServiceService
  ) {
  }

  ngOnInit() {
    this.service.getTaskList().subscribe(res => {
      console.log(res);
    });
  }

}
