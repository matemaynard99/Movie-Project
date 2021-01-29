import java.util.*;
import java.text.*;

public class Movie_Class implements java.lang.Comparable<Movie_Class> {

	//set variables for movie object
		String title;
		Date releaseDate;
		String description;
		Date receiveDate;
		public enum movieStatus{
			received,
			released,
			unreceived;
		};
		movieStatus status;
		Scanner scnr = new Scanner(System.in);
		DateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		public Movie_Class() {}
		//default constructor
		
		//constructor with parameters
		public Movie_Class(String movie, String d, String s, String r, String c) throws ParseException {
			
			title = movie;
			releaseDate = newFormat.parse(d);
			description = s;
			receiveDate = newFormat.parse(r);
			this.setStatus(c);
		}
		
		
		//method to edit the description 
		public void setTitle(String str) {
			title = str;
		}
				
				
		//method to get description
		public String getTitle(){
			return title;
		}
		
		
		//method to edit the description 
		public void setDescription(String des) {
			description = des;
		}
		
		
		//method to get description
		public String getDescription(){
			return description;
		}
		
		//method to set release date
		public void setReleaseDate(String date) throws ParseException {
			releaseDate = newFormat.parse(date);
		}
		
		//method to get release date
		public Date getReleaseDate() {
			return releaseDate;
		}
		
		//method to set receive date
		public void setReceiveDate(String date) throws ParseException {
			receiveDate = newFormat.parse(date);
		}
				
		//method to get receive date
		public Date getReceiveDate() {
			return receiveDate;
		}
		
		//method to set status
		public void setStatus(String c) {
			
			if(c.equals("received")) {
				status = movieStatus.received; 
			}
			else if(c.equals("released")) {
				status = movieStatus.released;
			}
			else {
				status = movieStatus.unreceived;
			}
			
			
		}//End set status
		
		public movieStatus getStatus() {
			return status;
		}
		
		@Override
		public int compareTo(Movie_Class movie) {
			return getReleaseDate().compareTo(movie.getReleaseDate());
		}
		public String toString() {
			return(title + ", " + newFormat.format(releaseDate) + "," + description + ", " + newFormat.format(receiveDate)+ ", " + status);
		}
}

