import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BoardsComponent } from './boards.component';
import { ListComponent } from 'src/app/components/list/list.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [{ path: '', component: BoardsComponent }];

@NgModule({
  declarations: [BoardsComponent, ListComponent],
  imports: [CommonModule, DragDropModule, RouterModule.forChild(routes)],
})
export class BoardsModule {}
