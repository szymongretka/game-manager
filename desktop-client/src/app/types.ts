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
