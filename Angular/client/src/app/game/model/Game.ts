import { Author } from "../../author/model/Author";
import { category } from "../../category/model/category";

export class Game {
  id: number;
  title: string;
  age: number;
  category: category;
  author: Author;
}
