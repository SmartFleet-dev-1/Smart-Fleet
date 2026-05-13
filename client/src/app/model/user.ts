export enum Role {
  ADMIN = 'ADMIN'
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