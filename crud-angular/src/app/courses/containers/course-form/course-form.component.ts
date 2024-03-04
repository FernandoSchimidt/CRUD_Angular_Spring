import { Component } from '@angular/core';
import { FormBuilder, FormGroup,NonNullableFormBuilder,UntypedFormBuilder,UntypedFormGroup } from '@angular/forms';
import { CourseService } from '../../service/course.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.scss',
})
export class CourseFormComponent {

  form= this.formBuilder.group({
    name: [''],
    category: [''],
  });

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: CourseService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    // this.form
  }

  onSubmit() {
    this.service.save(this.form.value).subscribe(
      (data) => {
        console.log(data);
        this.openSnackBar('Curso salvo com sucesso', 'Ok');
        this.router.navigate(['']);
      },
      (error) => {
        this.openSnackBar('Erro ao salvar o curso', 'Erro');
      }
    );
  }
  onCancel() {
    this.router.navigate(['']);
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }
}
