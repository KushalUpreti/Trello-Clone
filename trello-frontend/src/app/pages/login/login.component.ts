import { Component } from '@angular/core';
import { first } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  email: string;
  password: string;

  constructor(private readonly authService: AuthService) {}

  login() {
    if (!this.email || !this.password) {
      alert('Please fill in all fields');
      return;
    }
    this.authService.login(this.email, this.password);
  }
}
