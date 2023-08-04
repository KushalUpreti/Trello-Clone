import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Board } from 'src/app/interfaces/board.interface';

@Component({
  selector: 'app-boards',
  templateUrl: './boards.component.html',
  styleUrls: ['./boards.component.scss'],
})
export class BoardsComponent {
  constructor(
    private readonly http: HttpClient,
    private readonly router: Router
  ) {}

  boards: Board[] = [];

  ngOnInit(): void {
    this.getBoardById(1);
  }

  getBoardById(id: number): void {
    this.http
      .get<Board[]>(`http://localhost:8082/boards/${id}`)
      .subscribe((boards) => {
        this.boards = boards;
      });
  }

  onBoardClick(board: Board) {
    this.router.navigate(['/board', board.id]);
  }
}
