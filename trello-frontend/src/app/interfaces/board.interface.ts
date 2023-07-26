import { List } from './list.interface';

export interface Board {
  title: string;
  id: string;
  lists: List[];
}
