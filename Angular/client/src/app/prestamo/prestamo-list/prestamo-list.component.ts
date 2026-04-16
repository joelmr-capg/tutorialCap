import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Prestamo } from '../model/Prestamo';
import { PrestamoService } from '../prestamo.service';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { Pageable } from '../../core/model/page/Pageable';

@Component({
  selector: 'app-prestamo-list',
  imports: [],
  templateUrl: './prestamo-list.component.html',
  styleUrl: './prestamo-list.component.scss'
})
export class PrestamoListComponent implements OnInit {
  pageNumber: number = 0;
  pageSize: number = 5;
  totalElements: number = 0;

  dataSource = new MatTableDataSource<Prestamo>();
  displayedColumns: string[ ] = ['id', 'nombreJuego','nombreCliente', 'fechaPrestamo', 'fechaDevolucion'];

  constructor(private prestamoService: PrestamoService, public dialog: MatDialog){ }

  ngOnInit(): void {
    this.loadPage()
  }

  loadPage(event?: PageEvent){
    const pageable: Pageable = {
      pageNumber: this.pageNumber,
      pageSize: this.pageSize,
      sort: [
        {
          property: 'id',
          direction: 'ASC'
        }
      ]
    };
    if(event != null) {
      pageable.pageSize = event.pageSize;
      pageable.pageNumber = event.pageIndex
    }

    this.prestamoService.getListaPrestamos(pageable).subscribe((data) =>{
      this.dataSource.data = data.content;
      this.pageNumber = data.pageable.pageNumber;
      this.pageSize = data.pageable.pageSize;
      this.totalElements = data.totalElements;
    })
  }
}
