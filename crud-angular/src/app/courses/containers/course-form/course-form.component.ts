import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  NonNullableFormBuilder,
  UntypedFormBuilder,
  UntypedFormGroup,
  Validators,
} from '@angular/forms';
import { CourseService } from '../../service/course.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../../model/course';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.scss',
})
export class CourseFormComponent {
  form = this.formBuilder.group({
    id: [''],
    name: [
      '',
      [Validators.required, Validators.minLength(3), Validators.maxLength(100)],
    ],
    category: ['', [Validators.required]],
  });

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: CourseService,
    private snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const course: Course = this.route.snapshot.data['course'];
    // console.log(course);
    this.form.setValue({
      id: course.id,
      name: course.name,
      category: course.category,
    });
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

  getErrorMessage(fieldName: string) {
    const field = this.form.get(fieldName);

    const erros = this.form.getError(fieldName);

    if (field?.hasError('required')) {
      return 'Campo obrigatorio';
    }
    if (field?.hasError('minlength')) {
      const requiredLenght = erros ? erros['requiredlength'] : 5;
      return `O campo nome deve ter pelo menos ${requiredLenght} caracteres`;
    }
    if (field?.hasError('maxlength')) {
      const requiredLenght = erros ? erros['requiredlength'] : 50;
      return `O campo nome deve ter no maximo ${requiredLenght} caracteres`;
    }
    return 'Campo invalido';
  }
}
