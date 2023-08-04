import { List } from './list.interface';

export interface Board {
  id: string;
  title: string;
  description: string;
  created_at: Date;
  updated_at: Date;
  lists: List[];
}
