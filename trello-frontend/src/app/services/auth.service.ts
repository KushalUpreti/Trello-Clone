import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, map, Observable, Subject } from 'rxjs';
import { User } from '../interfaces/user.interface';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private userSubject: BehaviorSubject<User | null>;
  private user: Observable<User | null>;

  constructor(private router: Router, private http: HttpClient) {
    this.userSubject = new BehaviorSubject(
      JSON.parse(localStorage.getItem('user')!)
    );
    this.user = this.userSubject.asObservable();
  }

  public get userId() {
    return this.userValue.userId;
  }

  public get email() {
    return this.userValue.email;
  }

  public get userValue() {
    return this.userSubject.value;
  }

  login(email: string, password: string) {
    this.postData(`http://localhost:8080/auth/login`, {
      email,
      password,
    }).then((user) => {
      localStorage.setItem('user', JSON.stringify(user));
      this.userSubject.next(user);
      this.router.navigate(['/']);
    });

    // return this.http
    //   .post<any>(`http://localhost:8080/auth/login`, { email, password })
    //   .pipe(
    //     map((user) => {
    //       console.log('Authentication');

    //       localStorage.setItem('user', JSON.stringify(user));
    //       this.userSubject.next(user);
    //       return user;
    //     })
    //   );
  }
  logout() {
    localStorage.removeItem('user');
    this.userSubject.next(null);
    this.router.navigate(['/login']);
  }

  private async postData(url = '', data = {}) {
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

  isLoggedIn() {
    if (this.userSubject.value) {
      return true;
    }
    return false;
  }
}
