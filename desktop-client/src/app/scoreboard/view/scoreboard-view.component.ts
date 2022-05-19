import { Component, OnInit } from '@angular/core';
import {Post, Score} from "../../types";
import {ScoreboardService} from "../../services/scoreboard.service";


@Component({
  selector: 'app-view',
  templateUrl: './scoreboard-view.component.html',
  styleUrls: ['./scoreboard-view.component.css']
})
export class ScoreboardViewComponent implements OnInit {

  scores: Score[] = [];

  constructor(public scoreboardService: ScoreboardService) {
  }

  ngOnInit(): void {
    this.scoreboardService.getAll().subscribe((data: Score[])=>{
      this.scores = data;
      console.log(this.scores);
    });
  }

}
