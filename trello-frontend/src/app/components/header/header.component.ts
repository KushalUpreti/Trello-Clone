import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  constructor(
    private readonly router: Router,
    private readonly auth: AuthService
  ) {}

  redirect() {
    this.router.navigate(['']);
  }

  logout() {
    this.auth.logout();
  }
}
