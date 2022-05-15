import { Component, OnInit } from '@angular/core';
import {PostService} from "../../../services/post.service";
import {Post, User} from "../../../types";
import {AppComponent} from "../../../app.component";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  posts: Post[] = [];

  constructor(public postService: PostService, public appComponent: AppComponent) {
  }

  ngOnInit(): void {
    this.postService.getAll().subscribe((data: Post[])=>{
      this.posts = data;
      console.log(this.posts);
    });
  }

  deletePost(id:number){
    this.postService.delete(id).subscribe(res => {
      this.posts = this.posts.filter(item => item.id !== id);
      console.log('Post deleted successfully!');
    })
  }

}
