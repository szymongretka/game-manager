export interface Book {
  id: number | undefined,
  name: string;
}

export interface GameDTO {
  id: number,
  name: string,
  url: string,
  type: string,
  size: number
}

export interface User {
  id: string,
  username: string,
  firstName: string,
  lastName: string,
  roles: string[]
}
