import {Component, OnInit} from '@angular/core';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {FileUploadService} from 'src/app/services/file-upload.service';
import * as fileSaver from 'file-saver';
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

  selectedThumbnail?: FileList;
  selectedGame?: FileList;
  currentThumbnail?: File;
  currentGame?: File;
  progress = 0;
  message = '';

  fileInfos?: Observable<any>;
  test: string[] = [];

  constructor(private uploadService: FileUploadService, private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    // this.fileInfos = this.uploadService.getFiles();
    //this.download('cheat-sheet.pdf', 'cheat324-sheet.pdf');
    this.uploadService.test().subscribe(test => this.test = test);
    // this.downloadThumbnailTest();
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

      console.log(thumbnail);
      console.log(game);

      if (thumbnail && game) {
        this.currentThumbnail = thumbnail;
        this.currentGame = game;

        this.uploadService.upload(this.currentThumbnail, this.currentGame).subscribe(
          (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round(100 * event.loaded / event.total);
            } else if (event instanceof HttpResponse) {
              this.message = event.body.message;
              this.fileInfos = this.uploadService.getFiles();
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


  download(fileName: string, name: string) {
    this.uploadService.downloadFile(fileName).subscribe((response: any) => {
      let blob:any = new Blob([response]);
      const url = window.URL.createObjectURL(blob);
      fileSaver.saveAs(blob, name);
    }), (error: any) => console.log('Error downloading the file'),
      () => console.info('File downloaded successfully');
  }


  image: SafeUrl | null = null;

  // downloadThumbnailTest() {
  //   const mediaType = 'application/image';
  //   this.uploadService.downloadThumbnail().subscribe(value => {
  //     const blob = new Blob([value], { type: mediaType });
  //     const unsafeImg = URL.createObjectURL(blob);
  //     this.image = this.sanitizer.bypassSecurityTrustUrl(unsafeImg);
  //   }, error1 => {
  //   });
  // }

}
