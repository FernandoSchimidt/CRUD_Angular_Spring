import { Course } from './course';

export interface CoursePage {
  courseDTOS: Course[];
  totalElements: number;
  totalPages: number;
}
