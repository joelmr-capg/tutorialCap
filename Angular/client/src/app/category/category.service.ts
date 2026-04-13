import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { category } from './model/category';
import { CATEGORY_DATA } from './model/mock-categories';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(
    private http: HttpClient
  ) { }

  private baseUrl = "http://localhost:8080/category"

  getCategories():Observable<category[]>{
    return this.http.get<category[]>(this.baseUrl)
  }

   saveCategory(category: category): Observable<category> {
    const {id} = category;
    const url = id ? `${this.baseUrl}/${id}`: this.baseUrl;
    return this.http.put<category>(url,category)
  }

  deleteCategory(idCategory : number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${idCategory}`);
  }
}
