import {Component, OnInit} from '@angular/core';
import {GameDTO} from "../types";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {map} from "rxjs/operators";
import {MatDialog} from "@angular/material/dialog";
import {BookService} from "../services/book.service";
import {FileUploadService} from "../services/file-upload.service";
import {Router} from "@angular/router";
import {AppService} from "../services/app.service";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-app-list',
  templateUrl: './app-list.component.html',
  styleUrls: ['./app-list.component.css']
})
export class AppListComponent implements OnInit {

  apps: GameDTO[] = [];

  colNumber = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({matches}) => matches ? 2 : 3));

  constructor(
    public dialog: MatDialog,
    private appService: AppService,
    private fileService: FileUploadService,
    private breakpointObserver: BreakpointObserver,
    private router: Router,
    private uploadService: FileUploadService,
    private sanitizer: DomSanitizer
  ) {
  }

  ngOnInit(): void {
    this.appService.getAppInfo().subscribe(apps => {
      this.apps = apps;
      apps.forEach(app => {
        this.downloadThumbnail(app.thumbnailId, app); //TODO fetch all images at once
      });
    });
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

  removeApp(thumbnailId: string) : void {
    this.appService.deleteApp(thumbnailId).subscribe();
  }

}
