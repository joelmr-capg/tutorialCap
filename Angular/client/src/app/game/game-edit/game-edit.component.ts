import { Component, Inject, OnInit } from '@angular/core';
import { MatLabel, MatFormField, MatError } from "@angular/material/form-field";
import { Game } from '../model/Game';
import { Author } from '../../author/model/Author';
import { category } from '../../category/model/category';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CategoryService } from '../../category/category.service';
import { AuthorService } from '../../author/author.service';
import { GameService } from '../game.service';
import { MatSelect, MatSelectModule } from "@angular/material/select";
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-game-edit',
  imports: [FormsModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule],
  templateUrl: './game-edit.component.html',
  styleUrl: './game-edit.component.scss'
})
export class GameEditComponent implements OnInit {
game: Game;
authors: Author[];
categories: category[];

constructor(
  public dialogRef: MatDialogRef<GameEditComponent>,
  @Inject(MAT_DIALOG_DATA) public data: any,
  private gameService: GameService,
  private CategoryService: CategoryService,
  private AuthorService: AuthorService
) {}

ngOnInit(): void {
  this.game = this.data.game? Object.assign({}, this.data.game) : new Game();

  this.CategoryService.getCategories().subscribe((categories) =>{
    this.categories = categories;
    if(this.game.category != null){
      const categoryFilter: category[] = categories.filter(
        (category) => category.id == this.data.game.category.id
      );
      if(categoryFilter !=null){
        this.game.category = categoryFilter[0];
      }
    }
  });
  this.AuthorService.getAllAuthors().subscribe((authors) => {
    this.authors = authors;

    if (this.game.author != null){
      const authorFilter: Author[] = authors.filter(
        (author) => author.id == this.data.game.author.id
      );
      if(authorFilter != null){
        this.game.author = authorFilter[0];
      }
    }
  });
}

onSave(){
  this.gameService.saveGame(this.game).subscribe((result) =>{
    this.dialogRef.close();
  });
}

onClose(){
  this.dialogRef.close();
}
}
