import {SafeUrl} from "@angular/platform-browser";

export interface GameDTO {
  id: string,
  name: string,
  thumbnail: SafeUrl | null
}

export interface User {
  id: string,
  username: string,
  firstName: string,
  lastName: string,
  roles: string[]
}

export interface Post {
  id: number;
  title: string;
  body: string;
  comments: Comment[];
  userName: string;
}

export interface Comment {
  id: number;
  text: string;
  postId: number;
  userName: string;
}

export interface Score {
  id: number;
  username: string;
  points: number;
  gameName: string;
}
