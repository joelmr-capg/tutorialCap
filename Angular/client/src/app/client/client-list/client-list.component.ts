import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ClientService } from '../client.service';
import { Client } from '../model/Client';
import { ClientEditComponent } from '../client-edit/client-edit.component';
import { DialogConfirmationComponent } from '../../core/dialog-confirmation/dialog-confirmation.component';
import { MatTable, MatTableDataSource, MatColumnDef, MatHeaderRowDef, MatTableModule } from "@angular/material/table";
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-client-list',
  imports: [
        MatButtonModule,
        MatIconModule,
        MatTableModule,
        CommonModule
      ],
  templateUrl: './client-list.component.html',
  styleUrl: './client-list.component.scss'
})
export class ClientListComponent implements OnInit {

  dataSource = new MatTableDataSource<Client>();
  displayedColumns: string[] = ['id', 'nombre','accion'];

  constructor(
    private clientService: ClientService,
    public dialog: MatDialog
  ){}

  ngOnInit(): void {
    this.clientService.getClients().subscribe(
      clients => this.dataSource.data = clients
    );
  }

  createClient(){
    const dialogRef = this.dialog.open(ClientEditComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  editClient(client:Client){
    const dialogRef = this.dialog.open(ClientEditComponent, {
      data: {client}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  deleteClient(client:Client){
    const dialogRef = this.dialog.open(DialogConfirmationComponent,{
      data: {title:"Eliminar Cliente", description:"Esta seguro de que desea eliminar el cliente?"}
    })

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.clientService.deleteClient(client.id).subscribe(
          () => this.ngOnInit()
        );
      }
    });
  }
}
