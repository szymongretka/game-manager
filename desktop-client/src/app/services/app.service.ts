import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';
import {GameDTO} from "../types";

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private httpClient: HttpClient) { }

  getAppsThumbnails() {

  }

  getAppInfo(): Observable<GameDTO[]> {
    return this.httpClient.get<GameDTO[]>(`/app`);
  }

  addNewApp(thumbnail: File, game: File, appName: string): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('thumbnail', thumbnail);
    formData.append('game', game);
    formData.append('name', appName); //TODO check why null

    const req = new HttpRequest('POST', `/app`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.httpClient.request(req);
  }

  editApp() {

  }

  deleteApp(thumbnailId: string) {
    return this.httpClient.delete(`/app/${thumbnailId}`);
  }

}
