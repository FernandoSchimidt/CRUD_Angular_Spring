import { Component } from '@angular/core';
import {
  FormGroup,
  NonNullableFormBuilder,
  UntypedFormArray,
  Validators,
} from '@angular/forms';
import { CourseService } from '../../service/course.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../../model/course';
import { Lesson } from '../../model/lesson';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.scss',
})
export class CourseFormComponent {
  form!: FormGroup;

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: CourseService,
    private snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const course: Course = this.route.snapshot.data['course'];
    this.form = this.formBuilder.group({
      id: [course.id],
      name: [
        course.name,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(100),
        ],
      ],
      category: [course.category, [Validators.required]],
      lessons: this.formBuilder.array(this.retrieveLessons(course)),
    });
    console.log(this.form);
    console.log(this.form.value);
  }

  private retrieveLessons(course: Course) {
    const lessons = [];
    if (course?.lessons) {
      course.lessons.forEach((lesson) =>
        lessons.push(this.createLesson(lesson))
      );
    } else {
      lessons.push(this.createLesson());
    }
    return lessons;
  }

  private createLesson(lesson: Lesson = { id: '', name: '', youtubeUrl: '' }) {
    return this.formBuilder.group({
      id: [lesson.id],
      name: [lesson.name],
      youtubeUrl: [lesson.youtubeUrl],
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
  getLessonsformArray() {
    return (<UntypedFormArray>this.form.get('lessons')).controls;
  }

  addNewClass(){
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.push(this.createLesson());
  }
  removeLesson(index:number){
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.removeAt(index);
  }
}
