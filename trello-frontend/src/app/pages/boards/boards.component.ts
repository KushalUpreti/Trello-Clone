import { HttpClient } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Board } from 'src/app/interfaces/board.interface';
import { FormsModule } from '@angular/forms';
import { map } from 'rxjs';

@Component({
  selector: 'app-boards',
  templateUrl: './boards.component.html',
  styleUrls: ['./boards.component.scss'],
})
export class BoardsComponent {
  constructor(
    private readonly http: HttpClient,
    private readonly router: Router,
    private readonly dialog: MatDialog
  ) {}

  boards: Board[] = [];

  ngOnInit(): void {
    this.getBoardById(1);
  }

  setBoardText = (title: string, description: string): void => {
    if (!title || !description) {
      alert('Enter valid title or description');
      return;
    }
    this.postData(`http://localhost:8082/create-board/${1}`, {
      title,
      description,
    }).then((board) => {
      this.boards.push(board);
    });
  };

  async postData(url = '', data = {}) {
    // Default options are marked with *
    const response = await fetch(url, {
      method: 'POST', // *GET, POST, PUT, DELETE, etc.
      headers: {
        'Content-Type': 'application/json',
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: JSON.stringify(data), // body data type must match "Content-Type" header
    });
    return response.json(); // parses JSON response into native JavaScript objects
  }

  openDialog() {
    const dialogRef = this.dialog.open(DialogContentExampleDialog, {
      height: '320px',
      width: '500px',
      data: {
        setBoardText: this.setBoardText,
      },
    });
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

@Component({
  selector: 'add-board-dialog',
  templateUrl: './add-board-modal.component.html',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, FormsModule],
})
export class DialogContentExampleDialog {
  boardTitle: string;
  boardDescription: string;

  constructor(@Inject(MAT_DIALOG_DATA) public data: { setBoardText }) {}

  clickHandler(): void {
    this.data.setBoardText(this.boardTitle, this.boardDescription);
  }
}
