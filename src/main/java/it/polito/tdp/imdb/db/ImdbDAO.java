package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	    
	
	
	public List<Actor> listAllActors(Map<Integer, Actor> idMap){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				idMap.put(actor.getId(), actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
public List<String> getGeneri(){
	
	String sql = "select distinct genre "
			+ "from movies_genres "
			+ "order by genre asc ";
	
	List<String> result = new ArrayList<String>();
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		ResultSet res = st.executeQuery();
		while (res.next()) {

			String s = new String( res.getString("genre"));
			
			result.add(s);
		}
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	
}


public List<Actor> getVertici(String genere){
	
	String sql = "select distinct a.* "
			+ "from movies_genres mg , actors a , roles r "
			+ "where mg.movie_id = r.movie_id "
			+ "and a.id = r.actor_id "
			+ "and mg.genre = ? " ;
	
	List<Actor> result = new ArrayList<Actor>();
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, genere);
		ResultSet res = st.executeQuery();
		while (res.next()) {

			Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
					res.getString("gender"));
			
			result.add(actor);
			
			
		}
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	
	
}

public List<Adiacenza> getAdiacenze(String genere,Map<Integer,Actor> idMap ){
	
	String sql = "select r1.actor_id as a1, r2.actor_id as a2, COUNT(r1.movie_id) as peso "
			+ "from movies_genres mg, roles r1,roles r2 "
			+ "where r1.actor_id > r2.actor_id "
			+ "and r1.movie_id = r2.movie_id "
			+ "and r1.movie_id = mg.movie_id "
			+ "and mg.genre = ? "
			+ "group by a1,a2 ";
	
	List<Adiacenza> result = new ArrayList<Adiacenza>();
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, genere);
		ResultSet res = st.executeQuery();
	
		while (res.next()) {
			
			Actor a1 = idMap.get(res.getInt("a1"));
			Actor a2 = idMap.get(res.getInt("a2"));
			
			if(a1 != null && a2!= null) {
			Adiacenza a  = new Adiacenza( a1, a2,res.getDouble("peso"));
			
			result.add(a);
			
			}
		}
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	
}
	
	}
	
	
	

	
	
	
	

