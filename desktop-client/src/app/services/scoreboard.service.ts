import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GameDTO, Score} from "../types";

@Injectable({
  providedIn: 'root'
})
export class ScoreboardService {

  constructor(private httpClient: HttpClient) {
  }

  getAll(): Observable<Score[]> {
    return this.httpClient.get<Score[]>(`/scoreboard`);
  }

}
