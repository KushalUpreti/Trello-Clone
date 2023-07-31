import { Component } from '@angular/core';
import { Board } from 'src/app/interfaces/board.interface';
import {
  CdkDragDrop,
  moveItemInArray,
  transferArrayItem,
} from '@angular/cdk/drag-drop';
import { List } from 'src/app/interfaces/list.interface';

@Component({
  selector: 'app-boards',
  templateUrl: './boards.component.html',
  styleUrls: ['./boards.component.scss'],
})
export class BoardsComponent {
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
