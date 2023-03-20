package Model;

import java.util.*;

import Enums.Categories;


public class Book_Details {

	public  int ISBN_No;
    public String bookName;
    public String authorName;
    public int Pages;
    public Date publishedDate;
	public Categories category;
	int availableCount;

    
    public int getAvailableCount() {
		return availableCount;
	}

	public void setAvailableCount(int availableCount) {
		this.availableCount = availableCount;
	}

	public int getISBN_No() {
		return ISBN_No;
	}

	public void setISBN_No(int iSBN_No) {
		ISBN_No = iSBN_No;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getPages() {
		return Pages;
	}

	public void setPages(int pages) {
		Pages = pages;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}
	
    public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}
	
}


