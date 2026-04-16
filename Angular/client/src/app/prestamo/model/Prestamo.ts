import { Client } from "../../client/model/Client";
import { Game } from "../../game/model/Game";

export class Prestamo{
  id: number;
  fechaPrestamo: Date;
  fechaDevolucion: Date;
  client: Client;
  game: Game
}
