import { Component } from '@angular/core';
import {
  CdkDragDrop,
  moveItemInArray,
  transferArrayItem,
} from '@angular/cdk/drag-drop';
import { Board } from './interfaces/board.interface';
import { List } from './interfaces/list.interface';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  board: Board = {
    title: 'Personal',
    id: '9g5x6j90s4',
    lists: [
      {
        title: 'Todo',
        cards: [
          { title: 'Get to Work' },
          { title: 'Go Home' },
          { title: 'Pick Up Groceries' },
          { title: 'Fall Asleep' },
        ],
      },
      {
        title: 'Done',
        cards: [
          { title: 'Sleep' },
          { title: 'Brush' },
          { title: 'Exercise' },
          { title: 'Eat Breakfast' },
        ],
      },
      {
        title: 'Weekend',
        cards: [
          { title: 'Swim' },
          { title: 'Hike' },
          { title: 'Exercise' },
          { title: 'Sleep' },
        ],
      },
    ],
  };

  drop(event: CdkDragDrop<List[]>) {
    moveItemInArray(this.board.lists, event.previousIndex, event.currentIndex);
  }
}
