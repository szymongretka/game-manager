import {Component, OnInit} from '@angular/core';

import {ActivatedRoute, Router} from '@angular/router';
import {Post, Comment, User} from '../../../types';
import {PostService} from "../../../services/post.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AppComponent} from "../../../app.component";

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  id: number;
  post: Post;

  form!: FormGroup;

  constructor(
    public appComponent: AppComponent,
    public postService: PostService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['postId'];
    this.postService.find(this.id).subscribe((data: Post) => {
      this.post = data;
    });

    this.form = new FormGroup({
      text: new FormControl('', Validators.required),
      postId: new FormControl(this.id, Validators.required)
    });
  }

  deleteComment(id: number) {
    this.postService.deleteComment(id).subscribe(res => {
      this.post.comments = this.post.comments.filter(item => item.id !== id);
      console.log('Comment deleted successfully!');
    })
  }

  get f() {
    return this.form.controls;
  }

  submit() {
    console.log(this.form.value);
    this.postService.addComment(this.form.value).subscribe(res => {
      console.log('Comment added successfully!');
      this.post.comments.push(res);
    })
  }

}
