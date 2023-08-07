import {
  CdkDragDrop,
  moveItemInArray,
  transferArrayItem,
} from '@angular/cdk/drag-drop';
import { Component, Input } from '@angular/core';
import { Card } from 'src/app/interfaces/card.interface';
import { DragEvent } from 'src/app/interfaces/drag-event.interface';
import { List } from 'src/app/interfaces/list.interface';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
})
export class ListComponent {
  @Input() list: List;
  @Input() sendCardEvent: any;

  drop(event: CdkDragDrop<Card[]>) {
    let dragEvent: DragEvent = {
      boardId: '',
      currIndex: event.currentIndex,
      prevIndex: event.previousIndex,
      eventType: 'card',
      targetList: +event.container.element.nativeElement.getAttribute('listid'),
      prevList:
        +event.previousContainer.element.nativeElement.getAttribute('listid'),
    };

    if (event.previousContainer === event.container) {
      moveItemInArray(
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    }

    this.sendCardEvent(dragEvent);
  }

  validDate(date: Date): string {
    let prettyDate = new Date(date);
    return prettyDate.toDateString();
  }
}
