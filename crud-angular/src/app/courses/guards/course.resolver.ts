import { ResolveFn } from '@angular/router';
import { CourseService } from '../service/course.service';
import { inject } from '@angular/core';
import { of } from 'rxjs';
import { Course } from '../model/course';

export const courseResolver: ResolveFn<Course> = (
  route,
  state,
  service: CourseService = inject(CourseService)
) => {
  if (route.params?.['id']) {
    return service.loadById(route.params['id']);
  }
  return of({ id: '', name: '', category: '' });
};
