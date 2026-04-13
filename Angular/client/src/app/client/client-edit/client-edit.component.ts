import { Component, Inject, OnInit } from '@angular/core';
import { ClientService } from '../client.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Client } from '../model/Client';
import { MatFormField, MatLabel } from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatAnchor } from "@angular/material/button";

@Component({
  selector: 'app-client-edit',
  imports: [MatFormField, MatLabel, MatInputModule, FormsModule, MatAnchor],
  templateUrl: './client-edit.component.html',
  styleUrl: './client-edit.component.scss'
})
export class ClientEditComponent implements OnInit {
  client: Client;
  constructor(
    public dialogRef: MatDialogRef<ClientEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {client: Client},
    private clientService: ClientService
  ){}

  ngOnInit(): void {
    this.client = this.data.client != null ? this.data.client : new Client();
  }

  onSave(){
    this.clientService.saveClient(this.client).subscribe(() => {
      this.dialogRef.close();
    });
  }

  onClose(){
    this.dialogRef.close();
  }
}
