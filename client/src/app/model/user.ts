export enum Role {
  ADMIN = 'ADMIN',
  MANAGER = 'MANAGER',
  USER = 'USER'
}

export interface User {
  id?: number;
  userId?: number;
  username?: string;
  password?: string;
  email?: string;
  contactNumber?: number;
  role?: Role;
}