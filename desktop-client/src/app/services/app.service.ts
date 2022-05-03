import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpParams, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {GameDTO} from "../types";

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private httpClient: HttpClient) {
  }

  getAppsThumbnails() {

  }

  getAppInfo(id: string): Observable<GameDTO> {
    return this.httpClient.get<GameDTO>(`/app/${id}`)
  }

  getAppsInfo(): Observable<GameDTO[]> {
    return this.httpClient.get<GameDTO[]>(`/app`);
  }

  addNewApp(thumbnail: File, game: File, appName: string): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('thumbnail', thumbnail);
    formData.append('game', game);

    let params: HttpParams = new HttpParams();
    params = params.append('name', appName);
    const req = new HttpRequest('POST', `/app`, formData, {
      reportProgress: true,
      responseType: 'json',
      params: params
    });

    return this.httpClient.request(req);
  }

  editApp(appName: string, id: string, thumbnail: File, game: File) {
    const formData: FormData = new FormData();

    if (thumbnail) {
      formData.append('thumbnail', thumbnail);
    }
    if (game) {
      formData.append('game', game);
    }

    let params: HttpParams = new HttpParams();
    params = params.append('newName', appName);
    const req = new HttpRequest('PUT', `/app/${id}`, formData, {
      reportProgress: true,
      responseType: 'json',
      params: params
    });

    return this.httpClient.request(req);
  }

  deleteApp(id: string) {
    return this.httpClient.delete(`/app/${id}`);
  }


}
