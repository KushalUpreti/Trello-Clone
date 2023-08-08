import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BoardComponent } from './board.component';
import { ListComponent } from 'src/app/components/list/list.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

const routes: Routes = [{ path: '', component: BoardComponent }];

@NgModule({
  declarations: [BoardComponent, ListComponent],
  imports: [
    CommonModule,
    DragDropModule,
    RouterModule.forChild(routes),
    FormsModule,
    NgbModule,
  ],
})
export class BoardModule {}
