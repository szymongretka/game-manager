import {SafeUrl} from "@angular/platform-browser";

export interface Book {
  id: number | undefined,
  name: string;
}

export interface GameDTO {
  id: string,
  thumbnailId: string,
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
