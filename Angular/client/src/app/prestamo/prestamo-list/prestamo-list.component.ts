import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Prestamo } from '../model/Prestamo';
import { PrestamoService } from '../prestamo.service';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent, MatPaginator } from '@angular/material/paginator';
import { Pageable } from '../../core/model/page/Pageable';
import { PrestamoEditComponent } from '../prestamo-edit/prestamo-edit.component';
import { DialogConfirmationComponent } from '../../core/dialog-confirmation/dialog-confirmation.component';
import { AuthorService } from '../../author/author.service';
import { Client } from '../../client/model/Client';
import { Game } from '../../game/model/Game';
import { GameService } from '../../game/game.service';
import { ClientService } from '../../client/client.service';
import { MatFormField, MatLabel, MatSelect, MatSelectModule } from "@angular/material/select";
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';


@Component({
  selector: 'app-prestamo-list',
  imports: [
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatPaginator
],
  templateUrl: './prestamo-list.component.html',
  styleUrl: './prestamo-list.component.scss'
})
export class PrestamoListComponent implements OnInit {
  clients: Client[];
  games: Game[];
  filterGame: Game;
  filterClient: Client;
  filterDate: string;
  pageNumber: number = 0;
  pageSize: number = 5;
  totalElements: number = 0;
  prestamos: Prestamo[];

  dataSource = new MatTableDataSource<Prestamo>();
  displayedColumns: string[ ] = ['id', 'nombreJuego','nombreCliente', 'fechaPrestamo', 'fechaDevolucion', ' '];

  constructor(private prestamoService: PrestamoService, public dialog: MatDialog, private gameService: GameService, private clientService: ClientService){ }

  ngOnInit(): void {
    this.loadPage()

    this.prestamoService.getPrestamos().subscribe((prestamos) => (this.prestamos = prestamos));

    this.gameService
            .getGames()
            .subscribe((games) => (this.games = games));

    this.clientService
            .getClients()
            .subscribe((clients) => (this.clients = clients));
  }

  loadPage(event?: PageEvent){


const pageable: Pageable = {
    pageNumber: event ? event.pageIndex : this.pageNumber,
    pageSize: event ? event.pageSize : this.pageSize,
    sort: [
      {
        property: 'id',
        direction: 'ASC'
      }
    ]
  };


const titulo = this.filterGame?.title ?? null;
const idClient = this.filterClient?.id ?? null;

const fecha = this.filterDate ?? null;

this.prestamoService
  .getListaPrestamos(pageable, titulo, idClient, fecha)
  .subscribe(data => {
    this.dataSource.data = data.content;
    this.pageNumber = data.pageable.pageNumber;
    this.pageSize = data.pageable.pageSize;
    this.totalElements = data.totalElements;
  });

  }

  onClearFilter(): void{

this.filterGame = null;
  this.filterClient = null;
  this.filterDate = null;
  this.onSearch();

  }

  onSearch(): void{

this.pageNumber = 0;
  this.loadPage();

  }


  createPrestamo(){
    const dialogRef = this.dialog.open(PrestamoEditComponent, {
      data: {},
    });

    dialogRef.afterClosed().subscribe((result) =>{
      this.ngOnInit();
    });
  }

  editPrestamo(prestamo: Prestamo){
    const dialogRef = this.dialog.open(PrestamoEditComponent, {
      data :{ prestamo: prestamo},
    });
    dialogRef.afterClosed().subscribe((result) =>{
      this.ngOnInit();
    });
  }

  deletePrestamo(prestamo: Prestamo){
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      data: {
        title: 'Confirmar eliminacion',
        description: 'Atención si borra el prestamo se perderán sus datos.<br> ¿Desea eliminar el prestamo?'
      }
    })
    dialogRef.afterClosed().subscribe((result) => {
      if (result){
        this.prestamoService.deletePrestamo(prestamo.id).subscribe((result) => {
          this.ngOnInit();
        })
      }
    })
  }
}
