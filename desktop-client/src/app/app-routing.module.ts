import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppListComponent } from './app-list/app-list.component';
import {AddNewAppComponent} from "./add-new-app/add-new-app.component";
import {EditAppComponent} from "./edit-app/edit-app.component";

const routes: Routes = [
  { path: 'browse-apps', component: AppListComponent, pathMatch: 'full' },
  { path: 'add-app', component: AddNewAppComponent },
  { path: 'edit-app/:id', component: EditAppComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
