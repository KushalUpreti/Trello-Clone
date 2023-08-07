export interface DragEvent {
  boardId: string;
  eventType: string;
  prevList?: number;
  targetList: number;
  prevIndex?: number;
  currIndex: number;
}
