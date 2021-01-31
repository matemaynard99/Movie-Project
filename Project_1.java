/*
*Samuel Maynard
*Dalton Vining
*Shane Callaway
*/

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Project_1 {
	
	static Scanner console = new Scanner(System.in);
	
	static Linked_List <Movie_Class>showingMovies = new Linked_List<Movie_Class>();
	static Linked_List <Movie_Class>comingMovies = new Linked_List<Movie_Class>();
	
	static List<Movie_Class> comingList = new ArrayList<Movie_Class>();
	
	static DateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws Exception {
		
		//creating linked lists and iterators for showing and coming movies
		
		/**
		 * opens and reads the movies file until the end of file,
		 * closing file after reading.
		 * Just using temporary data for movies, like the title being movie and movie2
		 * Citation for code to read from file
		 * https://www.w3schools.com/java/java_files_create.asp
		 */
		
		FileReader fr = new FileReader("Dates.txt");
		BufferedReader br = new BufferedReader(fr);
		String str="", l="";
		while((l=br.readLine())!=null) { 
		    str += l;
		}
		br.close();
		 
		/**
		 * Creates an array to store movies.txt data
		 * Splits data at ; 
		 * ; Uses in movies.txt to separate each movie object
		 */
		String[] wholeMovie = str.split(";");
		/**
		 * Splits data again, this time splitting at ,
		 * , used to separate attributes of the movie object
		 */
		
		for (String movies : wholeMovie) {
			
			String[] fields = movies.split(",");
			fields[4] = fields[4].trim();
			/**
			 * Creates a movie object
			 * Sets strings to attributes of Movie object
			 */
			Movie_Class newMovie = new Movie_Class(fields[0], fields[1], fields[2], fields[3], fields[4]);
			
			/**
			 * adds each movie to a list depending on their status
			 * "Coming" movies are added to a list instead of the linked list. 
			 * This is to sort them in the regular list first before adding them.
			 */
			switch(newMovie.getStatus()) {
			case released:
				showingMovies.addLast(newMovie);
				break;
			case received:
				comingList.add(newMovie);
				break;
			case unreceived:
				comingList.add(newMovie);
			}	

		}
		/**
		 * sorting the coming movies by release date using the compareTo method in the Movie_Class page
		 * This is to easily sort them before adding them to the Coming Linked list one by one
		 */
		Collections.sort(comingList);
		for(Movie_Class movie : comingList) {
			comingMovies.addLast(movie);
		}
		
		menu();
		
	}
	/**
	 * Menu method where every other method can be selected through the terminal with String "option"
	 * using switch case with "option" to bring user to desired method.
	 * @throws Exception: nearly every method in menu creates an exception
	 */
	static public void menu() throws Exception {
		System.out.println("Welcome to our movie viewer!");
		System.out.println();
		
		System.out.println("D - Display Showing and Coming Movies");
		System.out.println("A - Add a Movie to our Coming List");
		System.out.println("E - Edit a Release Date for a Movie");
		System.out.println("M - Edit a Description for a Movie");
		System.out.println("S - Start Showing Movies with a given Release Date");
		System.out.println("N - Number of Movies before a given Release Date");
		System.out.println("V - Save your Changes to our Files");
		System.out.println("X - Exit");
		String option = console.next().toUpperCase();
		console.nextLine();
		
		switch(option) {
		
		case "D":
			displayMovies();
			break;
		case "A":
			addMovie();
			break;
		case "E":
			editRelease();
			break;
		case "M":
			editDescription();
			break;
		case "S":
			showMoviesWithGivenDate();
			break;
		case "N":
			countBeforeReleaseDate();
			break;
		case "V":
			overwriteFile();
			break;
		case "X":
			System.exit(0);
			break;
		default:
			System.out.println("That option is invalid, please retry.");
			menu();
			break;	
		}
		
	}
	/**
	 * creates an organized list of showing movies, followed by coming movies in release date order
	 * @throws Exception: iterators used provide exceptions, this must follow suit.
	 */
	public static void displayMovies() throws Exception {
		List_Iterator <Movie_Class>comingIterator = comingMovies.iterator();
		List_Iterator <Movie_Class> showingIterator = showingMovies.iterator();
		
		//shows each movie object in showingMovies using custom toString() method
		System.out.println("Showing Movies");
		while(showingIterator.hasNext()) {
			Movie_Class showingMovie = showingIterator.next();
			System.out.println(showingMovie);
		}
		System.out.println();
		
		//shows each movie object in comingMovies using custom toString() method
		System.out.println("Coming Movies");
		while(comingIterator.hasNext()) {
			Movie_Class comingMovie = comingIterator.next();
			System.out.println(comingMovie);
		}
		menu();
	}
	/**
	 * the user adds their own movie attributes, where the program will make it into a Movie_Class object
	 * the object is then inserted into the comingMovies list with sorting in mind.
	 * @throws Exception
	 */
	private static void addMovie() throws Exception {
		boolean latestDate = true;
		
		//user input for four movie attributes (status will always be "received")
		Movie_Class newMovie = new Movie_Class();
		System.out.println("Please enter your movie title: ");
		String title = console.nextLine();
		System.out.println("Please enter your movie release date (yyyy-mm-dd): ");
		String releaseDate = console.nextLine();
		System.out.println("Please enter your movie description: ");
		String description = console.nextLine();
		System.out.println("Please enter your movie receive date (can't be after release date): ");
		String receiveDate = console.nextLine();
		
		String status = "received";
		
		//checks invalid date format for either releaseDate and receiveDate
		try {
			newMovie = new Movie_Class(title, releaseDate, description, receiveDate, status);
		}
		catch(ParseException e){
			System.out.println("One of your dates were invalid, returning to menu.");
			menu();
		}
		
		//checks if release date is earlier or equal to receive date
		if(newMovie.getReleaseDate().compareTo(newMovie.getReceiveDate()) <= 0) {
			System.out.println("Release date is earlier or equal to your receive date, returning to menu.");
			menu();
		}
		
		//checks if the users movie title matches another title in the comingMovies list.
		List_Iterator <Movie_Class>comingIterator = comingMovies.iterator();
		while(comingIterator.hasNext()) {
			Movie_Class movie = comingIterator.next();
			if(newMovie.getTitle().equals(movie.getTitle())) {
				System.out.println("The movie you added is already on our list, returning to menu.");
				menu();
			}
		}
		
		//if all checks pass, the iterator will determine where the users movie will go based on the release date
		List_Iterator <Movie_Class>comingIterator2 = comingMovies.iterator();
		while(comingIterator2.hasNext()) {
			Movie_Class movie = comingIterator2.next();//REMOVE THIS AS WELL???
			
		}
		
		
		if(latestDate) {
			comingMovies.addLast(newMovie);
		}
		
		
		menu();
	}
	
	/**
	 * allows the user to edit the release date of a movie in the comingMovies list of their choosing
	 * @throws Exception: iterator exceptions and a parse exception for an invalid date format
	 */
	private static void editRelease() throws Exception {
		List_Iterator <Movie_Class>comingIterator = comingMovies.iterator();
		
		System.out.print("Enter the name of the movie you want to edit: ");
		String movieName = console.nextLine();
		
		System.out.println("Please enter your altered release date (yyyy-mm-dd): ");
		String newDate = console.next();

		boolean movieFound = false;
		
		//sets new release date for movie in comingMovies list, with a check for invalid date format
		while(comingIterator.hasNext()) {
			Movie_Class movie = comingIterator.next();
			if(movie.getTitle().equals(movieName)) {
				movieFound = true;
				try {
					movie.setReleaseDate(newDate);
				}
				catch(ParseException e) {
					System.out.println("Release date invalid, returning to menu");
					menu();
				}
				System.out.println("Movie found and altered!");
				menu();
				
			}
		}
		//if no movie titles in comingMovies list match the user's, nothing will be done.
		if(movieFound == false) {
			System.out.println("Movie not found, returning to menu.");
			menu();
		}
	}
	
	/**
	 * allows user to edit the description of a movie in the comingMovies list of their choosing
	 * @throws Exception: the iterator used come with their exceptions
	 */
	public static void editDescription() throws Exception {
		List_Iterator <Movie_Class>comingIterator = comingMovies.iterator();
		
		System.out.print("Enter the name of the movie you want to edit: ");
		String movieName = console.nextLine();
		
		System.out.println("Please enter your altered description: ");
		String newDes = console.nextLine();

		boolean movieFound = false;
		
		//uses the iterator to search comingMovies list for the matching title, and set the user's description if succeeded
		while(comingIterator.hasNext()) {
			Movie_Class movie = comingIterator.next();
			if(movie.getTitle().equals(movieName)) {
				movieFound = true;
				movie.setDescription(newDes);
				System.out.println("Movie found and altered!");
				menu();	
			}
		}
		
		//if no movie titles match the user's, nothing will be done
		if(movieFound == false) {
			System.out.println("Movie not found, returning to menu.");
			menu();
		}
	}
	
	/**
	 * user inputs a release date, and all movies in the ComingMovies list with the same release date will
	 * be taken off the comingMovies list and inserted into the showingMovies list
	 * @throws Exception: iterator exceptions and a parse exception for an invalid date format
	 */
	public static void showMoviesWithGivenDate() throws Exception {
		List_Iterator <Movie_Class>comingIterator = comingMovies.iterator();
		List_Iterator <Movie_Class> showingIterator = showingMovies.iterator();
		
		//user input release date, with a check for invalid date format
		System.out.print("Please enter your release date (yyyy-mm-dd): ");
		String releaseString = console.next();
		Date releaseDate;
		try {
			releaseDate = newFormat.parse(releaseString);
		}
		catch(ParseException e) {
			System.out.println("Release date invalid, returning to menu");
			menu();
		}
		
		releaseDate = newFormat.parse(releaseString);
		boolean movieFound = false;
		boolean showMovieFound = false;
		
		//tests each release date in the comingMovies list, and if triggered, adds to showingMovies and removes from comingMovies
		while(comingIterator.hasNext()) {
			Movie_Class comingMovie = comingIterator.next();
			if(releaseDate.compareTo(comingMovie.getReleaseDate()) == 0) {
				System.out.println("Movie Found");
				movieFound = true;
				while(showingIterator.hasNext()){
					Movie_Class showingMovie = showingIterator.next();
					if (comingMovie.getTitle() == showingMovie.getTitle()) {
						System.out.println("One of your coming movies already exists in our showing list.");
					}
				}
				comingMovie.setStatus("released");
				showingMovies.addLast(comingMovie);
				comingIterator.removePrevious();
				continue;
			}
		}
		//if no matching release dates are found, nothing will be done.
		if(!movieFound) {
			System.out.println("No movie found with your release date.");
		}
		menu();	
	}
	
	/**
	 * user inputs a release date, the program will count how many movies in the comingMovies list 
	 * will release before the given release date
	 * @throws Exception: iterator exceptions and a parse exception for an invalid date format
	 */
	static public void countBeforeReleaseDate() throws Exception {
		List_Iterator <Movie_Class>comingIterator = comingMovies.iterator();
		
		//user input, with check for invalid date format
		System.out.print("Enter your release date we should count to (yyyy-mm-dd): ");
		String countString = console.next();
		Date countDate;
		try {
			countDate = newFormat.parse(countString);
		}
		catch(ParseException e) {
			System.out.println("invalid date, returning to menu");
			menu();
		}
		countDate = newFormat.parse(countString);
		
		int count = 0;
		boolean noMovies = true;
		
		//iterator iterates the comingMovies list, counting up for each Movie_Class object with a release date before the user's
		while(comingIterator.hasNext()) {
			Movie_Class movie = comingIterator.next();
			if(movie.getReleaseDate().compareTo(countDate) >= 0) {
				noMovies = false;
				System.out.println("The amount of movies coming before " + newFormat.format(countDate) + " is " + count);
				menu();
			}
			else {
				count += 1;
			}
		}
		
		//if all movies are released before the user's, the program will let them know
		if(noMovies) {
			System.out.println("All of our coming movies is coming out before your release date, congratulations!");
			menu();
		}
	}
	
	/**
	 * save any changes that have been made with the other functions to the .txt file
	 * @throws Exception if file cannot be reached
	 * Citations to save to the txt file
	 * https://stackoverflow.com/questions/20307754/save-data-from-linkedlist-in-java
	 * https://stackoverflow.com/questions/24335906/linkedlist-to-a-string
	 */
	public static void overwriteFile() throws Exception {
		try {
			StringBuilder toFile = new StringBuilder();
			Iterator<?> showingIT = showingMovies.iterator();
			Iterator<?> comingIT = comingMovies.iterator();

			while(showingIT.hasNext()) {	
				toFile.append(showingIT.next());
				toFile.append(";\n");
				//toFile.append(System.lineSeparator());
			}
			while(comingIT.hasNext()) {	
				toFile.append(comingIT.next());
				toFile.append(";\n");
				//toFile.append(System.lineSeparator());
			}
			 
			FileWriter fw = new FileWriter("Dates.txt");
	
			fw.write(toFile.toString());
			fw.close();

		}catch (IOException e) {
			System.out.println("Exception: " + e);
		}

		menu();
	}
	
}
	

	
	








