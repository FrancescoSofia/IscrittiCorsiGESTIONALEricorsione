package it.polito.tdp.gestionale.db;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.gestionale.model.Corso;
import it.polito.tdp.gestionale.model.Iscrizione;
import it.polito.tdp.gestionale.model.Studente;

public class DidatticaDAO {

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String codins) {

		final String sql = "SELECT * FROM corso where codins=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Corso corso = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"),
						rs.getInt("pd"));
				return corso;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Data una matricola ottengo lo studente.
	 */
	public Studente getStudente(int matricola) {

		final String sql = "SELECT * FROM studente where matricola=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Studente studente = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"),
						rs.getString("cds"));
				return studente;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	
	
	public void getAllStudenti(Map<Integer, Studente> mapStudenti) {

		final String sql = "SELECT * FROM studente ";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Studente studente = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"),rs.getString("cds"));
				mapStudenti.put(rs.getInt("matricola"), studente);
				
			}
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public void getAllCorsi(Map<String, Corso> mapCorsi) {
		final String sql = "SELECT * FROM corso ";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Corso corso = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"),
						rs.getInt("pd"));
				mapCorsi.put(corso.getCodins(),corso);
			}


		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
	

		public List<Iscrizione> getAllIscrizioni() {
			
			final String sql = "SELECT * FROM iscrizione";

			try {
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				
				List<Iscrizione> iscrizioni = new ArrayList<Iscrizione>();
				ResultSet rs = st.executeQuery();

				while (rs.next()) {
					iscrizioni.add(new Iscrizione(rs.getInt("matricola"),rs.getString("codins")));
				
				}
//				for(Iscrizione i: iscrizioni){
//					//System.out.println(i);
//				}
				return iscrizioni;


			} catch (SQLException e) {
				// e.printStackTrace();
				throw new RuntimeException("Errore Db");
			}
			
		}
}
