package com.asahi.bookmarkingApp.constants;

public enum BookGenre {
	 ART("Art"),
	 BIOGRAPHY("Biography"),
	 CHILDREN("Children"),
	 FICTION("Fiction"),
	 ROMANCE("Romance"),
	 PHILOSOPHY("Philosophy"),
	 TECHNICAL("Technical"),
	 SELF_HELP("Self_help");
//	 ART("Art";
//	 ART("Art";
//	 ART("Art";
//	 ART("Art";
	
	 private BookGenre(String name) {
		 this.name = name;
		}
	 
	 private String name;
	 public String getName() {
		 return name;
	 }
}
