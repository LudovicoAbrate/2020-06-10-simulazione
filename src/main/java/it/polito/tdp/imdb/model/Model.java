package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	
	private ImdbDAO dao;
	private Map<Integer,Actor> idMap;
	private Graph<Actor,DefaultWeightedEdge> grafo;
	
	
	public Model() {
		
		
		this.dao = new ImdbDAO();
		this.idMap = new HashMap<>();
        dao.listAllActors(idMap);
	}
	
	public void creaGrafo(String genre) {
	
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	
	     Graphs.addAllVertices(grafo, dao.getVertici(genre, idMap));
		
	
	
	
	}
	
	public int nVertici() {
		return grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return grafo.edgeSet().size();
		
	}
	
	public List<String> getTuttiGeneri(){
		return dao.listGeneri();
		
	}
	
	public List<Actor> getTuttiAttori(){
		List<Actor> attori = dao.listAllActors(idMap);
		Collections.sort(attori, new Comparator<Actor>(){

			@Override
			public int compare(Actor a1, Actor a2) {
				
				return a1.lastName.compareTo(a2.lastName);
			}
			
			
		});
	
		
		return attori;
	}
}
