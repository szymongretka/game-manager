import {Component, OnInit} from '@angular/core';
import {GameDTO} from "../types";
import {ActivatedRoute, Router} from "@angular/router";

import {AppService} from "../services/app.service";
import {FileUploadService} from "../services/file-upload.service";
import {DomSanitizer} from "@angular/platform-browser";
import {HttpEventType, HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-edit-app',
  templateUrl: './edit-app.component.html',
  styleUrls: ['./edit-app.component.css']
})
export class EditAppComponent implements OnInit {

  app!: GameDTO;

  selectedThumbnail?: FileList;
  selectedGame?: FileList;
  currentThumbnail?: File;
  currentGame?: File;
  progress = 0;
  message = '';
  appName: string = '';

  constructor(private route: ActivatedRoute, private appService: AppService, private router: Router, private uploadService: FileUploadService, private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id') as string;
    this.appService.getAppInfo(id).subscribe(app => {
      this.app = app;
      this.downloadThumbnail(app.id, app);
    });
  }

  onSubmit(app: GameDTO): void {
    this.upload();
  }

  downloadThumbnail(thumbnailId: string, app: GameDTO) {
    const mediaType = 'application/image';
    this.uploadService.downloadThumbnail(thumbnailId).subscribe(value => {
      const blob = new Blob([value], {type: mediaType});
      const unsafeImg = URL.createObjectURL(blob);
      app.thumbnail = this.sanitizer.bypassSecurityTrustUrl(unsafeImg);
    }, error1 => {
    });
  }

  selectThumbnail(event: any): void {
    this.selectedThumbnail = event.target.files;
  }

  selectGame(event: any): void {
    this.selectedGame = event.target.files;
  }

  upload(): void {
    this.progress = 0;

    if (this.selectedThumbnail && this.selectedGame) {
      const thumbnail: File | null = this.selectedThumbnail.item(0);
      const game: File | null = this.selectedGame.item(0);

      if (thumbnail && game) {
        this.currentThumbnail = thumbnail;
        this.currentGame = game;

        this.appService.editApp(this.appName, this.app.id, this.currentThumbnail, this.currentGame).subscribe(
          (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round(100 * event.loaded / event.total);
            } else if (event instanceof HttpResponse) {
              this.message = event.body.message;
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
