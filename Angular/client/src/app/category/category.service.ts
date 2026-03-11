import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { category } from './model/category';
import { CATEGORY_DATA } from './model/mock-categories';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor() { }

  getCategories():Observable<category[]>{
    return of (CATEGORY_DATA);
  }

   saveCategory(category: category): Observable<category> {
    return of(null);
  }

  deleteCategory(idCategory : number): Observable<any> {
    return of(null);
  }  
}
