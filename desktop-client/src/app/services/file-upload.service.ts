import { Injectable } from '@angular/core';
import {HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import {GameDTO} from "../types";
import {saveAs} from 'file-saver';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  constructor(private httpClient: HttpClient) { }

  upload(thumbnail: File, game: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('thumbnail', thumbnail);
    formData.append('game', game);
    formData.append('name', 'testName');

    const req = new HttpRequest('POST', `/app`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.httpClient.request(req);
  }

  getFiles(): Observable<any> {
    return this.httpClient.get<GameDTO[]>(`/app/files`);
  }

  downloadFile(id: string): any {
    return this.httpClient.get(`/app/game/${id}`, {responseType: 'blob'});
  }

  test(): Observable<string[]> {
    return this.httpClient.get<string[]>('/app/save/test');
  }

  downloadThumbnail(id: string): Observable<Blob> {
    return this.httpClient.get(`/app/thumbnail/${id}`, {responseType: "blob"});
  }

}
