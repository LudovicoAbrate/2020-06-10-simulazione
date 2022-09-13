package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;



public class Model {

	
	ImdbDAO dao;
	private Graph<Actor,DefaultWeightedEdge> grafo;
	List<Actor> vertici;
	Map<Integer,Actor> idMap = new HashMap<>();
	public Model() {
		
	this.dao = new ImdbDAO();
	this.vertici= new LinkedList<>();
	
	}
	
	public List<String> getGeneri() {
		
		return dao.getGeneri();
		
	}
	
	public void  creaGrafo(String genere) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.vertici.clear();
		
		
		for( Actor a : dao.getVertici(genere)) {
		idMap.put(a.id, a);
		vertici.add(a);
	
			}
			
		Graphs.addAllVertices(this.grafo, dao.getVertici(genere));
		
		for(Adiacenza a : dao.getAdiacenze(genere, idMap)) {
			
			Graphs.addEdgeWithVertices(this.grafo, a.getA1(), a.getA2(), a.getPeso());
		}
		
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public List<Actor> getVertici() {
		
		List<Actor> atemp = new LinkedList<Actor>();
		for( Actor a : idMap.values()) {
			atemp.add(a);
		}
		
		Collections.sort(atemp,  new Comparator<Actor>(){

			@Override
			public int compare(Actor a1, Actor a2) {
				
				return a1.lastName.compareTo(a2.lastName);
			}});
		
		
		return atemp;
	
	}
	
	  public List<Actor> getAttoriConnessi(Actor tendina) {
		  
		  ConnectivityInspector<Actor, DefaultWeightedEdge> ci = new ConnectivityInspector<Actor, DefaultWeightedEdge>(grafo);
			List<Actor> actors = new ArrayList<>(ci.connectedSetOf(tendina));
			
			actors.remove(tendina);
			
			Collections.sort(actors,new Comparator<Actor>(){

				@Override
				public int compare(Actor a1, Actor a2) {
					
					return a1.lastName.compareTo(a2.lastName);
				}});
			
			
			
			return actors;
	  }
	

	

}

