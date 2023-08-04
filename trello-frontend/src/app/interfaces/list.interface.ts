import { Card } from './card.interface';

export interface List {
  id: number;
  title: string;
  listIndex: number;
  archived: boolean;
  cards: Card[];
}
