import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Post, Comment} from "../types";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }

  constructor(private httpClient: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.httpClient.get('/posts')
      .pipe(
        catchError(this.errorHandler)
      )
  }

  create(post: Post): Observable<any> {
    return this.httpClient.post('/posts/', post, this.httpOptions)
      .pipe(
        catchError(this.errorHandler)
      )
  }

  find(id: number): Observable<any> {
    return this.httpClient.get('/posts/' + id)
      .pipe(
        catchError(this.errorHandler)
      )
  }

  update(id: number, post: Post): Observable<any> {
    return this.httpClient.put('/posts/' + id, post, this.httpOptions)
      .pipe(
        catchError(this.errorHandler)
      )
  }

  delete(id: number) {
    return this.httpClient.delete('/posts/' + id)
      .pipe(
        catchError(this.errorHandler)
      )
  }

  deleteComment(id: number) {
    return this.httpClient.delete('/posts/comment/' + id)
      .pipe(
        catchError(this.errorHandler)
      )
  }

  addComment(comment: Comment): Observable<any> {
    return this.httpClient.post('/posts/comment/', comment, this.httpOptions)
      .pipe(
        catchError(this.errorHandler)
      )
  }

  errorHandler(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(errorMessage);
  }

}
