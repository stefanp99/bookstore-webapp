package com.book;

import java.util.Comparator;

public class DateComparator implements Comparator<Book> {
	public int compare(Book a, Book b) {
		if (Integer.parseInt(a.getDate().substring(0, 4)) != Integer.parseInt(b.getDate().substring(0, 4)))
			return Integer.parseInt(a.getDate().substring(0, 4)) - Integer.parseInt(b.getDate().substring(0, 4));
		if (Integer.parseInt(a.getDate().substring(5, 7)) != Integer.parseInt(b.getDate().substring(5, 7)))
			return Integer.parseInt(a.getDate().substring(5, 7)) - Integer.parseInt(b.getDate().substring(5, 7));
		return Integer.parseInt(a.getDate().substring(8, 10)) - Integer.parseInt(b.getDate().substring(8, 10));
	}
}
