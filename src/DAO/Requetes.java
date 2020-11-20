package DAO;

import java.io.*;
import java.sql.*;
import java.util.*;

import Database.*;

public class Requetes {

	private Connection c;

	public Requetes(Connection connec) {
		this.c = connec;
	}

	public List<Article> requete_1(String email) {
		List<Article> list = new ArrayList<Article>();

		try {
			PreparedStatement ps = c.prepareStatement(
					"select titre from ecrire "
							+ "where email = ? "
							+ "order by article.titre asc");

			ps.setString(1, email);

			ResultSet res = ps.executeQuery();

			if (!res.wasNull()) {
				while (res.next()) {
					list.add(new Article(res.getString("titre")));
				}
			}

			res.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
		return list;
	}

	public List<Chercheur> requete_2(String email) {
		List<Chercheur> list = new ArrayList<Chercheur>();

		try {
			PreparedStatement ps = c.prepareStatement("select email from ecrire "
					+ "where email != ?  and titre = (select titre from ecrire where email = ?) "
					+ "order by email asc");

			ps.setString(1, email);
			ps.setString(2, email);

			ResultSet res = ps.executeQuery();

			if (!res.wasNull()) {
				while (res.next()) {
					list.add(new Chercheur(res.getString("email")));
				}
			}

			res.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
		return list;
	}

	public List<String> requete_3() {
		List<String> list = new ArrayList<String>();

		try {
			PreparedStatement ps = c.prepareStatement(
					"select email, nomlabo from travailler " + "group by email " + "order by email asc, nomlabo asc");

			ResultSet res = ps.executeQuery();

			if (!res.wasNull()) {
				while (res.next()) {
					Chercheur cherch = new Chercheur(res.getString("email"));
					list.add(res.getString("nomlabo") + " | " + cherch.toString());
				}
			}

			res.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
		return list;
	}

	public List<Chercheur> requete_4(int nb) {
		List<Chercheur> list = new ArrayList<Chercheur>();

		try {
			PreparedStatement ps = c.prepareStatement("select distinct email, count(titre) from annoter " + "group by email "
					+ "having count(titre) >= ? " + "order by email asc");

			ps.setInt(1, nb);

			ResultSet res = ps.executeQuery();

			if (!res.wasNull()) {
				while (res.next()) {
					list.add(new Chercheur(res.getString("email")));
				}
			}

			res.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
		return list;
	}

	public double requete_5(String email) {
		double moyenne = 0;

		try {
			PreparedStatement ps = c.prepareStatement("select email, avg(note) from noter " + "where email = ?");

			ps.setString(1, email);

			ResultSet res = ps.executeQuery();

			if (!res.wasNull()) {
				moyenne = res.getDouble("avg(note)");
			} else {
				moyenne = -1;
			}

			res.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
		return moyenne;
	}

	public List<String> requete_6(String labo) {
		List<String> list = new ArrayList<String>();

		try {
			PreparedStatement ps = c
					.prepareStatement("select ecrire.email, count(ecrire.titre), count(note), avg(note) from ecrire "
							+ "inner join noter on ecrire.titre = noter.titre "
							+ "inner join travailler on ecrire.email = travailler.email " + "where nomlabo = ? "
							+ "group by ecrire.email " + "order by count(ecrire.titre) desc, ecrire.email asc");

			ps.setString(1, labo);

			ResultSet res = ps.executeQuery();

			if (!res.wasNull()) {
				while (res.next()) {
					Chercheur cherch = new Chercheur(res.getString("email"));
					list.add(cherch.toString() + " | Article(s) ecrit: " + res.getInt("count(titre)")
							+ " | Note(s) obtenue(s): " + res.getInt("count(note)") + " | Moyenne: "
							+ res.getDouble("avg(note)"));
				}
			}

			res.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
		return list;
	}

	public boolean requete_7(String article) {
		boolean res = false;
		
		List<String> list_1 = new ArrayList<String>();
		List<String> list_2 = new ArrayList<String>();

		try {
			PreparedStatement ps_1 = c.prepareStatement("select travailler.nomlabo from travailler "
					+ "inner join ecrire on travailler.email = ecrire.email " + "where titre = ?");

			ps_1.setString(1, article);

			ResultSet res_1 = ps_1.executeQuery();
			
			if (!res_1.wasNull()) {
				while (res_1.next()) {
					list_1.add(res_1.getString("nomlabo"));
				}
			}

			PreparedStatement ps_2 = c.prepareStatement("select travailler.nomlabo from travailler "
					+ "inner join noter on travailler.email = noter.email " + "where titre = ? and note = max(note)");

			ps_2.setString(1, article);

			ResultSet res_2 = ps_2.executeQuery();
			
			if (!res_2.wasNull()) {
				while (res_2.next()) {
					list_1.add(res_2.getString("nomlabo"));
				}
			}

			int i = 0;
			while (!res && i < list_1.size()) {
				if (list_2.contains(list_1.get(i))) {
					res = true;
				} else {
					i++;
				}
			}

			res_1.close();
			res_2.close();
			ps_1.close();
			ps_2.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
		return res;
	}

	public void creationBdd(String fichier) {
		String chaine = "";
		List<String> nom_table = new ArrayList<String>();
		
		nom_table.add("publier");
		nom_table.add("support");
		nom_table.add("travailler");
		nom_table.add("annoter");
		nom_table.add("ecrire");
		nom_table.add("noter");
		nom_table.add("chercheur");
		nom_table.add("laboratoire");
		nom_table.add("annotation");
		nom_table.add("article");
		
		try {
			for (int i = 0; i < 10; i++) {
				PreparedStatement ps = c.prepareStatement("select count(*) from user_tables where table_name = "+nom_table.get(i));
				ResultSet res = ps.executeQuery();
				res.next();
				if(res.getInt("count(*)")==1) {
					PreparedStatement ps_1 = c.prepareStatement("drop table " + nom_table.get(i));
					ps_1.executeQuery();
					ps_1.close();
				}

				ps.close();
			}
			

			InputStream ips = new FileInputStream(fichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {
				chaine = chaine + ligne;
				if (ligne.contains(";")) {
					PreparedStatement ps_2 = c.prepareStatement(chaine.substring(0, chaine.length() - 1));
					System.out.println(chaine);
					ps_2.executeQuery();
					ps_2.close();
					chaine = "";
				}
			}
			br.close();

		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("erreur de fichier" + e.getMessage());
		}
	}

}
