import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-notfound-page',
  templateUrl: './notfound-page.component.html',
  styleUrls: ['./notfound-page.component.scss'],
})
export class NotfoundPageComponent {
  constructor(private readonly router: Router) {}

  redirect() {
    this.router.navigate(['']);
  }
}
