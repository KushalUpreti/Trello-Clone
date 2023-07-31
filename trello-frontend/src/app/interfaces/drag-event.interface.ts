export interface DragEvent {
  board: string;
  event: string;
  previous?: string;
  target: string;
  previousIndex?: number;
  currentIndex: number;
}
