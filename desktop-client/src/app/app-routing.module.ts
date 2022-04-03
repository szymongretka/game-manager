import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddNewBookComponent } from './add-new-book/add-new-book.component';
import { BookListComponent } from './book-list/book-list.component';
import { EditBookComponent } from './edit-book/edit-book.component';
import { FileUploadComponent } from './components/file-upload/file-upload.component';
import { AppListComponent } from './app-list/app-list.component';
import {AddNewAppComponent} from "./add-new-app/add-new-app.component";
import {EditAppComponent} from "./edit-app/edit-app.component";

const routes: Routes = [
  { path: 'browse-books', component: BookListComponent, pathMatch: 'full' },
  { path: 'add-book', component: AddNewBookComponent },
  { path: 'edit-book/:isbn', component: EditBookComponent },
  { path: 'browse-apps', component: AppListComponent, pathMatch: 'full' },
  { path: 'add-app', component: AddNewAppComponent },
  { path: 'edit-app/:id', component: EditAppComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
