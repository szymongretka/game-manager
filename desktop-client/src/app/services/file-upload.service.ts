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

  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);

    const req = new HttpRequest('POST', `/app/upload`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.httpClient.request(req);
  }

  getFiles(): Observable<any> {
    return this.httpClient.get<GameDTO[]>(`/app/files`);
  }

  downloadFile(id: number): any {
    return this.httpClient.get(`/app/files/${id}`, {responseType: 'blob'});
  }

}
