import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  {
    path: 'board/:boardId',
    // canActivate: [AuthGuard],
    loadChildren: () =>
      import('./pages/board/board.module').then((m) => m.BoardModule),
  },
  {
    path: 'login',
    loadChildren: () =>
      import('./pages/login/login.module').then((m) => m.LoginModule),
  },
  {
    path: 'boards',
    // canActivate: [AuthGuard],
    loadChildren: () =>
      import('./pages/boards/boards.module').then((m) => m.BoardsModule),
  },
  {
    path: '**',
    loadChildren: () =>
      import('./pages/notfound-page/notfound-page.module').then(
        (m) => m.NotfoundPageModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
