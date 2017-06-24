package it.polito.tdp.gestionale.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.gestionale.db.DidatticaDAO;

public class Model {
	
	private Map<Integer,Studente> mapStudenti;
	private Map<String,Corso> mapCorsi;
	
//	private List<Studente> studenti;
//	private List<Corso> corsi;
	private List<Iscrizione> iscrizioni;
	private DidatticaDAO dao;
	private SimpleGraph<Nodo,DefaultEdge> grafo;
	private List<Integer> stat;
	//private List<Studente> studenti;
	private List<Corso> best;
	
	
	
	public Model(){
		
		dao = new DidatticaDAO();
		mapStudenti = new HashMap<Integer,Studente>();
		mapCorsi = new HashMap<String,Corso>();
		this.dao.getAllStudenti(mapStudenti);
		this.dao.getAllCorsi(mapCorsi);
		iscrizioni = new ArrayList<Iscrizione>();
		//studenti = new ArrayList<Studente>();
		best = new ArrayList<Corso>();
		
		
	}
	
	
	public List<Integer> creaGrafo(){
		stat = new ArrayList<Integer>();
		this.grafo = new SimpleGraph<Nodo,DefaultEdge>(DefaultEdge.class);
		this.iscrizioni = dao.getAllIscrizioni();
		
		for(Studente s : mapStudenti.values()){
			grafo.addVertex(s);
		}
		for(Corso c : mapCorsi.values()){
			grafo.addVertex(c);
		}
		
		
		for(Iscrizione i: iscrizioni){
			Studente s = mapStudenti.get(i.getStudente());
			Corso c = mapCorsi.get(i.getCorso());
			s.getCorsi().add(c);
			c.getStudenti().add(s);
			grafo.addEdge(s,c);	
			}
				
//		for(Iscrizione i :iscrizioni){
//			i.getStudente().getCorsi().add(i.getCorso());
//			i.getCorso().getStudenti().add(i.getStudente());
//			grafo.addEdge(i.getStudente(), i.getCorso());	
//		}
//		System.out.println(grafo);
		
		for(int i =0; i<mapCorsi.values().size()+1;i++){
			stat.add(0);
		}
		
		for(Studente s :mapStudenti.values()){
			int pos = Graphs.neighborListOf(grafo, s).size();
			int valore = stat.get(pos);
			valore++;
			stat.set(pos, valore);
		}
		return stat;
	}
	
	public void chiama(){
		
	List<Corso> corsi = new ArrayList<Corso>();
	best.addAll(mapCorsi.values());
	this.ricorsione( corsi);
	
		
	}
	
	public void ricorsione(List<Corso>corsi){
		
		//System.out.println(corsi);
	HashSet<Studente> studenti = new HashSet<Studente>(mapStudenti.values());
		for(Corso c1: corsi){
			studenti.removeAll(c1.getStudenti());}
		
		if(best.size()>corsi.size() && studenti.isEmpty()){
			best.clear();
			best.addAll(corsi);
			System.out.println(best);
		}
		
		
		for(Corso c : mapCorsi.values()){
			if(corsi.isEmpty() || c.compareTo(corsi.get(corsi.size()-1))>0){
			corsi.add(c);
			this.ricorsione( corsi);
			corsi.remove(c);
		}
	}
	}

}
