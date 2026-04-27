import { Component, Inject, OnInit } from '@angular/core';
import { DialogConfirmationComponent } from '../../core/dialog-confirmation/dialog-confirmation.component';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Prestamo } from '../model/Prestamo';
import { PrestamoService } from '../prestamo.service';
import { MatFormField, MatLabel, MatSelectModule } from "@angular/material/select";
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { GameService } from '../../game/game.service';
import { ClientService } from '../../client/client.service';
import { Game } from '../../game/model/Game';
import { Client } from '../../client/model/Client';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-prestamo-edit',
  imports: [FormsModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule],
  templateUrl: './prestamo-edit.component.html',
  styleUrl: './prestamo-edit.component.scss'
})
export class PrestamoEditComponent implements OnInit {
  prestamo: Prestamo;
  games: Game[];
  clients: Client[];

  constructor(
    public dialogRef: MatDialogRef<PrestamoEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private prestamoService: PrestamoService,
    private gameService: GameService,
    private clientService: ClientService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.prestamo = this.data.prestamo ? Object.assign({}, this.data.prestamo): new Prestamo();

    this.gameService.getGames().subscribe((games) =>{
        this.games = games;
        if(this.prestamo.game != null){
          const gameFilter: Game[] = games.filter(
            (game) => game.id == this.data.prestamo.game.id
          );
          if(gameFilter !=null){
            this.prestamo.game = gameFilter[0];
          }
        }
      });

      this.clientService.getClients().subscribe((clients) => {
          this.clients = clients;

          if (this.prestamo.client != null){
            const clientFilter: Client[] = clients.filter(
              (client) => client.id == this.data.prestamo.client.id
            );
            if(clientFilter != null){
              this.prestamo.client = clientFilter[0];
            }
          }
        });
  }

  onSave() {
    this.prestamoService.savePrestamo(this.prestamo).subscribe({
      next: () => {
        this.dialogRef.close(true);
        this.snackBar.open("Prestamo guardado correctamente", "Cerrar",{
          duration: 3000
        });
      },
      error: (err) => {
        let message = "Error al guardar el prestamo"

        if(typeof err.error === "string"){
          message = err.error;
        } else if (err.error?.message){
          message = err.error.message;
        }
        this.snackBar.open(message, "Cerrar", {
          duration: 5000
        })
      }
    })
  }

  onClose(){
    this.dialogRef.close()
  }
  }
