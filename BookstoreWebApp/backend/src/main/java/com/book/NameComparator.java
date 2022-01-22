package com.book;

import java.util.Comparator;

public class NameComparator implements Comparator<Book> {
	public int compare(Book a, Book b) {
		return a.getTitle().compareToIgnoreCase(b.getTitle());
	}
}
