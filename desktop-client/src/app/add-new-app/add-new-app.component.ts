import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AppService} from "../services/app.service";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";

@Component({
  selector: 'app-add-new-app',
  templateUrl: './add-new-app.component.html',
  styleUrls: ['./add-new-app.component.css']
})
export class AddNewAppComponent implements OnInit {

  selectedThumbnail?: FileList;
  selectedGame?: FileList;
  currentThumbnail?: File;
  currentGame?: File;
  progress = 0;
  message = '';
  appName: string = '';

  fileInfos?: Observable<any>;

  invalidThumbnailType: boolean = false;
  thumbnailInvalidType: string | undefined;
  invalidGameType: boolean = false;
  gameInvalidType: string | undefined;

  constructor(private appService: AppService, private router: Router) { }

  ngOnInit(): void {
  }

  selectThumbnail(event: any): void {
    this.selectedThumbnail = event.target.files;
    this.validateExtension(this.selectedThumbnail, 'png');
  }

  selectGame(event: any): void {
    this.selectedGame = event.target.files;
    this.validateGameExtension(this.selectedGame, 'jar');
  }

  validateExtension(selectedThumbnail: FileList | undefined, extension: string): void {
    // @ts-ignore
    const thumbnailName: string = selectedThumbnail.item(0).name;
    let ext = thumbnailName.substring(thumbnailName.lastIndexOf('.') + 1);
    if(ext != extension) {
      this.invalidThumbnailType = true;
      this.thumbnailInvalidType = ext;
    }
  }

  validateGameExtension(selectedThumbnail: FileList | undefined, extension: string): void {
    // @ts-ignore
    const gameName: string = selectedThumbnail.item(0).name;
    let ext = gameName.substring(gameName.lastIndexOf('.') + 1);
    if(ext != extension) {
      this.invalidGameType = true;
      this.gameInvalidType = ext;
    }
  }

  upload(): void {
    this.progress = 0;

    if (this.selectedThumbnail && this.selectedGame) {
      const thumbnail: File | null = this.selectedThumbnail.item(0);
      const game: File | null = this.selectedGame.item(0);

      if (thumbnail && game) {

        this.currentThumbnail = thumbnail;
        this.currentGame = game;

        this.appService.addNewApp(this.currentThumbnail, this.currentGame, this.appName).subscribe(
          (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round(100 * event.loaded / event.total);
            } else if (event instanceof HttpResponse) {
              this.message = event.body.message;
              this.fileInfos = this.appService.getAppsInfo();
            }
          },
          (err: any) => {
            console.log(err);
            this.progress = 0;

            if (err.error && err.error.message) {
              this.message = err.error.message;
            } else {
              this.message = 'Could not upload the file!';
            }

            this.currentThumbnail = undefined;
            this.currentGame = undefined;
          });

      }

      this.selectedThumbnail = undefined;
      this.selectedGame = undefined;
    }
  }

}
