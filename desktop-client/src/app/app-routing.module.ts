import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppListComponent } from './app-list/app-list.component';
import {AddNewAppComponent} from "./add-new-app/add-new-app.component";
import {EditAppComponent} from "./edit-app/edit-app.component";
import {IndexComponent} from "./forum/post/index/index.component";
import {ViewComponent} from "./forum/post/view/view.component";
import {CreateComponent} from "./forum/post/create/create.component";
import {EditComponent} from "./forum/post/edit/edit.component";
import {ScoreboardViewComponent} from "./scoreboard/view/scoreboard-view.component";

const routes: Routes = [
  { path: 'browse-apps', component: AppListComponent, pathMatch: 'full' },
  { path: 'add-app', component: AddNewAppComponent },
  { path: 'edit-app/:id', component: EditAppComponent },
  { path: 'post', redirectTo: 'post/index', pathMatch: 'full'},
  { path: 'post/index', component: IndexComponent },
  { path: 'post/:postId/view', component: ViewComponent },
  { path: 'post/create', component: CreateComponent },
  { path: 'post/:postId/edit', component: EditComponent },
  { path: 'scoreboard/view', component: ScoreboardViewComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
