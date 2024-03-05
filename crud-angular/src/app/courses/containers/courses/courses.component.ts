import { Component } from '@angular/core';
import { Course } from '../../model/course';
import { CourseService } from '../../service/course.service';
import { Observable, catchError, of } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { error } from 'console';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrl: './courses.component.scss',
})
export class CoursesComponent {
  courses$: Observable<Course[]> | null = null;

  constructor(
    public service: CourseService,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {
    this.refresh();
  }

  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg,
    });
  }

  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.route });
  }
  onEdit(course: Course) {
    this.router.navigate(['edit', course.id], { relativeTo: this.route });
  }
  onRemove(course: Course) {
    this.service.remove(course.id).subscribe(
      () => {
        this.refresh(), this.openSnackBar('Curso removido com sucesso', 'X');
      },
      (error) => this.onError('Erro ao tentar remover o curso')
    );
  }
  refresh() {
    this.courses$ = this.service.list().pipe(
      catchError((error) => {
        this.onError('Erro ao carregar os cursos!');
        return of([]);
      })
    );
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  }
}
