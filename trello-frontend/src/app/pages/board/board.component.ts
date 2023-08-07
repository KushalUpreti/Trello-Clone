import {
  CdkDragDrop,
  moveItemInArray,
  transferArrayItem,
} from '@angular/cdk/drag-drop';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Board } from 'src/app/interfaces/board.interface';
import { Card } from 'src/app/interfaces/card.interface';
import { DragEvent } from 'src/app/interfaces/drag-event.interface';
import { List } from 'src/app/interfaces/list.interface';
declare let SockJS: any;
declare let Stomp: any;
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
  stompClient: any = null;
  userId: number = 0;

  ngOnInit(): void {
    this.userId = Math.floor(Math.random() * 300);
    const boardId = this.route.snapshot.paramMap.get('boardId');
    this.getBoardById(boardId);
    this.connect(boardId);
  }

  connect(boardId) {
    let socket = new SockJS('http://localhost:8085/chat');
    this.stompClient = Stomp.over(socket);
    this.stompClient.debug = null;
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(
        `/topic/board/${boardId}/update-board-title`,
        (data) => {
          const { userId, email, message } = JSON.parse(data.body);
          if (userId === this.userId || message.trim() == '') {
            return;
          }
          this.board.title = message;
        }
      );

      this.stompClient.subscribe(
        `/topic/board/${boardId}/update-list-order`,
        (data) => {
          const { userId, prevIndex, currIndex } = JSON.parse(data.body);
          if (userId === this.userId) {
            return;
          }
          moveItemInArray(this.board.lists, prevIndex, currIndex);
        }
      );

      this.stompClient.subscribe(
        `/topic/board/${boardId}/update-card-order`,
        (data) => {
          const { userId, prevIndex, currIndex, prevList, targetList } =
            JSON.parse(data.body);
          if (userId === this.userId) {
            return;
          }

          if (prevList === targetList) {
            moveItemInArray(
              this.findCardData(targetList),
              prevIndex,
              currIndex
            );
          } else {
            transferArrayItem(
              this.findCardData(prevList),
              this.findCardData(targetList),
              prevIndex,
              currIndex
            );
          }
        }
      );
    });
    return this.stompClient;
  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }
  }

  ngOnDestroy() {
    this.disconnect();
  }

  getBoardById(id: string): void {
    this.http
      .get<Board>(`http://localhost:8082/board/${id}`)
      .subscribe((board) => {
        this.board = board;
      });
  }

  sendUpdateTitleEvent() {
    this.stompClient.send(
      `/app/board/update-board-title/${this.board.id}`,
      {},
      JSON.stringify({
        userId: this.userId,
        email: 'kushal@gmail.com',
        message: this.board.title,
      })
    );
  }

  drop(event: CdkDragDrop<List[]>) {
    moveItemInArray(this.board.lists, event.previousIndex, event.currentIndex);

    let dragEvent: DragEvent = {
      boardId: this.board.id,
      currIndex: event.currentIndex,
      prevIndex: event.previousIndex,
      eventType: 'list',
      targetList: event.item.data.id,
    };

    this.stompClient.send(
      `/app/board/update-list-order/${this.board.id}`,
      {},
      JSON.stringify({
        ...dragEvent,
        userId: this.userId,
        email: 'kushal@gmail.com',
      })
    );
  }

  sendCardEvent = (dragEvent: DragEvent) => {
    this.stompClient.send(
      `/app/board/update-card-order/${this.board.id}`,
      {},
      JSON.stringify({
        ...dragEvent,
        boardId: this.board.id,
        userId: this.userId,
        email: 'kushal@gmail.com',
      })
    );
  };

  findCardData(listId): Card[] {
    return this.board.lists.find((list) => list.id === listId).cards;
  }
}
