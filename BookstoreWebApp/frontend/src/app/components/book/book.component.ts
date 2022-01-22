import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { timer } from 'rxjs';
import { Book } from './book';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})


export class BookComponent implements OnInit {

  books: Book[];
  found_books: Book[];
  sorted_books: Book[];
  readonly baseUrl = 'http://localhost:8080/BookWebService';
  categories = ['title', 'isbn', 'author'];
  favoriteSeason: string;
  seasons: string[] = ['Winter', 'Spring', 'Summer', 'Autumn'];
  order: string;

  searchForm = this.formBuilder.group({
    value: ''
  });

  addForm = this.formBuilder.group({
    isbn: '',
    title: '',
    author: '',
    price: 0,
    published_date: ''
  });

  removeForm = this.formBuilder.group({
    isbn: ''
  })

  constructor(private http: HttpClient, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.getBooks();
    this.order = 'dsc';
    this.sortBooks();
  }

  refresh(): void {
    window.location.reload();
  }

  getBooks() {
    this.http.get<Book[]>(this.baseUrl + '/library/books').subscribe(data => {
      this.books = [...data];
    });
  }

  findBooks() {
    let selectElement = document.getElementsByName('categorySelect')[0] as HTMLSelectElement;
    let chosenCategory = selectElement.options[selectElement.selectedIndex].value;
    console.warn(chosenCategory + ' has been submitted', this.searchForm.value);
    console.log(this.baseUrl + `/library/search/${chosenCategory}/${this.searchForm.value.value}`);
    this.http.get<Book[]>(this.baseUrl + `/library/search/${chosenCategory}/${this.searchForm.value.value}`).subscribe(data => {
      this.found_books = [...data];
    });
  }

  addBook() {
    console.warn('Book has been submitted', this.addForm.value);

    const params = new HttpParams()
      .set('title', this.addForm.value.title)
      .set('author', this.addForm.value.author)
      .set('price', this.addForm.value.price)
      .set('date', this.addForm.value.published_date);

    this.http.put<Book>(this.baseUrl + '/library/book/' + this.addForm.value.isbn + '?' + params, { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) }).subscribe();

    timer(500).subscribe(x => this.refresh());
  }

  removeBook() {
    console.warn(`delete book with isbn ${this.removeForm.value.isbn}`);
    if (confirm(`Are you sure you want to delete the book with ISBN ${this.removeForm.value.isbn}?`)) {
      this.http.delete<any>(this.baseUrl + '/library/book/' + this.removeForm.value.isbn).subscribe();
      timer(500).subscribe(x => this.refresh());
    }
  }

  sortBooks() {
    let selectElement = document.getElementsByName('sortCategorySelect')[0] as HTMLSelectElement;
    let chosenCategory = selectElement.options[selectElement.selectedIndex].value;
    this.http.get<Book[]>(this.baseUrl + '/library/sort/' + chosenCategory + '/' + this.order).subscribe(data => {
      this.sorted_books = [...data];
      console.log(this.sorted_books);
    })
  }

  radioChangeHandler(event: any) {
    console.log(event);
    this.order = event.target.value;
  }

}
