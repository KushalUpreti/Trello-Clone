import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Board } from 'src/app/interfaces/board.interface';
import { List } from 'src/app/interfaces/list.interface';

@Component({
  selector: 'app-boards',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss'],
})
export class BoardComponent {
  constructor(
    private readonly http: HttpClient,
    private route: ActivatedRoute
  ) {}

  board: Board;

  ngOnInit(): void {
    const boardId = this.route.snapshot.paramMap.get('boardId');
    this.getBoardById(boardId);
  }

  getBoardById(id: string): void {
    this.http
      .get<Board>(`http://localhost:8082/board/${id}`)
      .subscribe((board) => {
        this.board = board;
      });
  }

  drop(event: CdkDragDrop<List[]>) {
    moveItemInArray(this.board.lists, event.previousIndex, event.currentIndex);
  }
}
