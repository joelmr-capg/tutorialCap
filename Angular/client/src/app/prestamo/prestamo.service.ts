import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Prestamo } from './model/Prestamo';
import { Pageable } from '../core/model/page/Pageable';
import { PrestamoPage } from './model/PrestamoPage';

@Injectable({
  providedIn: 'root'
})
export class PrestamoService {

  constructor(private http: HttpClient) { }

  private baseUrl = 'http://localhost:8080/prestamo'

  getPrestamos(gameId?: number, clientId?: number, fecha?: Date):Observable<Prestamo[]>{
    return this.http.get<Prestamo[]>(this.composeFindUrl(gameId, clientId, fecha))
  }

  savePrestamo(prestamo:Prestamo): Observable<void>{
    const{id} = prestamo;
    const url = id ? `${this.baseUrl}/${id}` : this.baseUrl
    return this.http.put<void>(url, prestamo)
  }

  composeFindUrl(gameId?: number, clientId?: number, fecha?: Date):string{
    const params = new URLSearchParams();
    if(gameId){
      params.set('gameId', gameId.toString())
    }
    if(clientId){
      params.set('clientId', clientId.toString())
    }
    if(fecha){
      params.set('fecha',fecha.toString())
    }
    const queryString = params.toString()
    return queryString ? `${this.baseUrl}?${queryString}` : this.baseUrl
  }

  deletePrestamo(idPrestamo: number): Observable<void>{
    return this.http.delete<void>(`${this.baseUrl}/${idPrestamo}`)
  }



getListaPrestamos(
  pageable: Pageable,
  titulo?: string,
  idClient?: number,
  fecha?: string
): Observable<PrestamoPage> {

  const body: any = {
    pageable
  };

  if (titulo) {
    body.titulo = titulo;
  }

  if (idClient !== null && idClient !== undefined) {
    body.idClient = idClient;
  }

  if (fecha) {
    body.fecha = fecha;
  }

  return this.http.post<PrestamoPage>(this.baseUrl, body);
}


}
