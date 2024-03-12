import { Injectable } from '@angular/core';
import { Course } from '../model/course';
import { HttpClient } from '@angular/common/http';
import { delay, first, tap } from 'rxjs';
import { CoursePage } from '../model/course-page';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  private readonly API = 'api/courses';

  constructor(private httpClient: HttpClient) {}

  list(page = 0, pageSize = 10) {
    return this.httpClient
      .get<CoursePage>(this.API, { params: { page, pageSize } })
      .pipe(
        // delay(3000),
        // tap(courses =>console.log(courses)),
        first()
      );
  }

  save(record: Partial<Course>) {
    if (record.id) {
      return this.update(record);
    } else {
      return this.create(record);
    }
  }

  private create(record: Partial<Course>) {
    return this.httpClient.post<Course>(this.API, record).pipe(first());
  }

  private update(record: Partial<Course>) {
    return this.httpClient
      .put<Course>(`${this.API}/${record.id}`, record)
      .pipe(first());
  }

  loadById(id: string) {
    return this.httpClient.get<Course>(`${this.API}/${id}`);
  }

  public remove(id: string) {
    return this.httpClient.delete(`${this.API}/${id}`).pipe(first());
  }
}
