package it.polito.tdp.imdb.model;

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
	private Graph grafo;
	
	
	public Model() {
		
		
		this.dao = new ImdbDAO();
		this.idMap = new HashMap<>();

	}
	
	public void creaGrafo(Actor a) {
	grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	
	Graphs.addAllVertices(this.grafo, dao.getVertici(idMap));
		
	
	
	
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
		
	}
	
	public List<String> getTuttiGeneri(){
		return dao.listGeneri();
		
	}
	
	public List<Actor> getTuttiAttori(){
		return dao.listAllActors();
	}
}
