package Principales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

import javax.swing.*;

import DAO.Article;
import DAO.Chercheur;
import DAO.Requetes;
//import oracle.jdbc.driver.OracleDriver;

import Database.ConnectionSingleton;

public class Principale {

	public static void main(String[] args) {

		final Requetes[] request = new Requetes[1];

		JFrame frame = new JFrame("OnlineLib");
		frame.setLayout(new BorderLayout());

		JPanel connexion = new JPanel();
		JPanel interactions = new JPanel();

		JPanel champs = new JPanel();
		JPanel boutons = new JPanel();

		JLabel jl_user = new JLabel("Utilisateur");
		JLabel jl_password = new JLabel("Mot de passe");
		JLabel jl_url = new JLabel("URL");

		JTextField jt_user = new JTextField();
		JPasswordField jp_password = new JPasswordField();
		JTextField jt_url = new JTextField();

		connexion.setPreferredSize(new Dimension(600, 150));
		connexion.setLayout(new GridLayout(1, 2));

		champs.setLayout(new GridLayout(6, 1));

		champs.add(jl_user);
		champs.add(jt_user);
		champs.add(jl_password);
		champs.add(jp_password);
		champs.add(jl_url);
		champs.add(jt_url);

		JButton jb_connexion = new JButton("Connexion");
		jb_connexion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String user = "louis";
				String mp = "l904129S";
				String url = "jdbc:oracle:thin:@localhost:1521:XE";
				request[0] = new Requetes(ConnectionSingleton.getInstance(url, user, mp).getConnection());
				request[0].creationBdd("valeursProjetBdd.txt");
				System.out.println("connecte.");
			}
		});

		JButton jb_deconnexion = new JButton("Deconnexion");
		jb_deconnexion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (ConnectionSingleton.getInstance() != null
							&& !ConnectionSingleton.getInstance().getConnection().isClosed()) {
						ConnectionSingleton.getInstance().getConnection().close();
						System.out.println("fermee");
					}
				} catch (SQLException e) {
					System.err.println("SQLException: " + e.getMessage());
				}
			}
		});

		boutons.setLayout(new GridLayout(2, 1));

		boutons.add(jb_connexion);
		boutons.add(jb_deconnexion);

		connexion.add(champs);
		connexion.add(boutons);

		frame.add(connexion, BorderLayout.NORTH);

		JLabel jl_resultats = new JLabel("Resultats:");
		JTextArea jt_resultats = new JTextArea();
		// jt_resultats.setEnabled(false);

		JLabel jl_req_1 = new JLabel("Articles ecrits par le chercheur:");
		JLabel jl_req_2 = new JLabel("Co-auteurs ayant travaille avec le chercheur:");
		JLabel jl_req_3 = new JLabel("Laboratoirs de chaque chercheur:");
		JLabel jl_req_4 = new JLabel("Chercheurs ayant annote au moins:");
		JLabel jl_req_5 = new JLabel("Moyenne des notes donnees par le chercheur:");
		JLabel jl_req_6 = new JLabel("Nombre d articles, nombre de notes, moyenne obtenue pour le laboratoire:");
		JLabel jl_req_7 = new JLabel("Verification sur la note maximale de l article:");
		JLabel jl_req_8 = new JLabel("A DETERMINER");

		JTextField jt_req_1 = new JTextField();
		JTextField jt_req_2 = new JTextField();
		JTextField jt_req_4 = new JTextField();
		JTextField jt_req_5 = new JTextField();
		JTextField jt_req_6 = new JTextField();
		JTextField jt_req_7 = new JTextField();

		JButton jb_req_1 = new JButton("Executer la requete 1");
		jb_req_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (ConnectionSingleton.getInstance() != null
							&& !ConnectionSingleton.getInstance().getConnection().isClosed()) {

						PreparedStatement ps = ConnectionSingleton.getInstance().getConnection().prepareStatement(
								"select titre from ecrire " + "where email = ? " + "order by titre asc");

						ps.setString(1, jt_req_1.getText());

						ResultSet res = ps.executeQuery();

						String chaine = "";
						while (res.next()) {
							Article art = new Article(res.getString("titre"));
							chaine += art.toString() + "\n";
						}
						jt_resultats.setText(chaine);

						res.close();
						ps.close();
					}
				} catch (SQLException e1) {
					System.err.println("SQLException: " + e1.getMessage());
				}
			}
		});
		JButton jb_req_2 = new JButton("Executer la requete 2");
		jb_req_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (ConnectionSingleton.getInstance() != null
							&& !ConnectionSingleton.getInstance().getConnection().isClosed()) {
						PreparedStatement ps_1 = ConnectionSingleton.getInstance().getConnection()
								.prepareStatement("select titre from ecrire "
										+ "where email = ? "
										+ "order by titre asc");

						ps_1.setString(1, jt_req_2.getText());

						ResultSet res_1 = ps_1.executeQuery();

						String chaine = "";

						while (res_1.next()) {
							PreparedStatement ps_2 = ConnectionSingleton.getInstance().getConnection()
									.prepareStatement("select email from ecrire "
											+ "where titre = ? and email != ? "
											+ "order by email asc");
							
							ps_2.setString(1, res_1.getString("titre"));
							ps_2.setString(2, jt_req_2.getText());
							
							ResultSet res_2 = ps_2.executeQuery();
							
							res_2.next();
							Chercheur aut = new Chercheur(res_2.getString("email"));
							chaine += aut.toString() + "\n";
							res_2.close();
							ps_2.close();
						}
						jt_resultats.setText(chaine);

						res_1.close();
						ps_1.close();
					}
				} catch (SQLException e1) {
					System.err.println("SQLException: " + e1.getMessage());
				}
			}
		});

		JButton jb_req_3 = new JButton("Executer la requete 3");
		jb_req_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (ConnectionSingleton.getInstance() != null
							&& !ConnectionSingleton.getInstance().getConnection().isClosed()) {

						PreparedStatement ps = ConnectionSingleton.getInstance().getConnection().prepareStatement(
								"select email, nomlabo from travailler " + "group by email " + "order by email asc, nomlabo asc");

						ResultSet res = ps.executeQuery();

						String chaine = "";

						while (res.next()) {
							chaine += res.getString("nomlabo") + "\n";
						}
						jt_resultats.setText(chaine);
					}
				} catch (SQLException e1) {
					System.err.println("SQLException: " + e1.getMessage());
				}

			}
		});
		JButton jb_req_4 = new JButton("Executer la requete 4");
		jb_req_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (ConnectionSingleton.getInstance() != null
							&& !ConnectionSingleton.getInstance().getConnection().isClosed()) {

						PreparedStatement ps = ConnectionSingleton.getInstance().getConnection().prepareStatement("select distinct email, count(titre) from annoter "
								+ "group by email " + "having count(titre) >= ? " + "order by email asc");

						ps.setInt(1, Integer.parseInt(jt_req_4.getText()));

						ResultSet res = ps.executeQuery();

						String chaine = "";

						while (res.next()) {
							Chercheur aut = new Chercheur(res.getString("email"));
							chaine += aut.toString() + "\n";
						}
						jt_resultats.setText(chaine);
					}
				} catch (SQLException e1) {
					System.err.println("SQLException: " + e1.getMessage());
				}

			}
		});
		JButton jb_req_5 = new JButton("Executer la requete 5");
		jb_req_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (ConnectionSingleton.getInstance() != null
							&& !ConnectionSingleton.getInstance().getConnection().isClosed()) {
						double moyenne = 0;

						PreparedStatement ps = ConnectionSingleton.getInstance().getConnection().prepareStatement("select email, avg(note) from noter " + "where email = ?");

						ps.setString(1, jt_req_5.getText());

						ResultSet res = ps.executeQuery();

						if (!res.wasNull()) {
							moyenne = res.getDouble("avg(note)");
						} else {
							moyenne = -1;
						}
						jt_resultats.setText("Moyenne: " + moyenne);

						res.close();
						ps.close();
					}
				} catch (SQLException e1) {
					System.err.println("SQLException: " + e1.getMessage());
				}

			}
		});
		JButton jb_req_6 = new JButton("Executer la requete 6");
		jb_req_6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (ConnectionSingleton.getInstance() != null
							&& !ConnectionSingleton.getInstance().getConnection().isClosed()) {

						PreparedStatement ps = ConnectionSingleton.getInstance().getConnection()
								.prepareStatement("select ecrire.email, count(ecrire.titre), count(note), avg(note) from ecrire "
										+ "inner join noter on ecrire.titre = noter.titre "
										+ "inner join travailler on ecrire.email = travailler.email " + "where nomlabo = ? "
										+ "group by ecrire.email " + "order by count(ecrire.titre) desc, ecrire.email asc");

						ps.setString(1, jt_req_6.getText());

						ResultSet res = ps.executeQuery();

						String chaine = "";

						while (res.next()) {
							Chercheur cherch = new Chercheur(res.getString("email"));
							chaine +=cherch.toString() + " | Article(s) ecrit: " + res.getInt("count(titre)")
									+ " | Note(s) obtenue(s): " + res.getInt("count(note)") + " | Moyenne: "
									+ res.getDouble("avg(note)") + "\n";
						}
						jt_resultats.setText(chaine);
					}
				} catch (SQLException e1) {
					System.err.println("SQLException: " + e1.getMessage());
				}

			}
		});
		JButton jb_req_7 = new JButton("Executer la requete 7");
		jb_req_7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					if (ConnectionSingleton.getInstance() != null
							&& !ConnectionSingleton.getInstance().getConnection().isClosed()) {

						boolean res = false;
						
						List<String> list_1 = new ArrayList<String>();
						List<String> list_2 = new ArrayList<String>();
						
						PreparedStatement ps_1 = ConnectionSingleton.getInstance().getConnection().prepareStatement("select travailler.nomlabo from travailler "
								+ "inner join ecrire on travailler.email = ecrire.email " + "where titre = ?");

						ps_1.setString(1, jt_req_7.getText());

						ResultSet res_1 = ps_1.executeQuery();

						
							while (res_1.next()) {
								list_1.add(res_1.getString("nomlabo"));
							}
						

						PreparedStatement ps_2 = ConnectionSingleton.getInstance().getConnection().prepareStatement("select travailler.nomlabo from travailler "
								+ "inner join noter on travailler.email = noter.email " + "where titre = ? and note = max(note)");

						ps_2.setString(1, jt_req_7.getText());

						ResultSet res_2 = ps_2.executeQuery();
						jt_resultats.setText("");

						String chaine = "";

						while (res_2.next()) {
							list_1.add(res_2.getString("nomlabo"));
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
						
						if (res) {
							jt_resultats.setText("Note maximale attribuee par un co-auteur.");
						} else {
							jt_resultats.setText("Note maximale non attribuee par un co-auteur.");
						}
					}
				} catch (SQLException e1) {
					System.err.println("SQLException: " + e1.getMessage());
				}

			}
		});

		interactions.setLayout(new GridLayout(1, 2));

		JPanel informations = new JPanel();
		JPanel resultats = new JPanel();

		informations.setLayout(new GridLayout(20, 1));

		informations.add(jl_req_1);
		informations.add(jt_req_1);
		informations.add(jb_req_1);

		informations.add(jl_req_2);
		informations.add(jt_req_2);
		informations.add(jb_req_2);

		informations.add(jl_req_3);
		informations.add(jb_req_3);

		informations.add(jl_req_4);
		informations.add(jt_req_4);
		informations.add(jb_req_4);

		informations.add(jl_req_5);
		informations.add(jt_req_5);
		informations.add(jb_req_5);

		informations.add(jl_req_6);
		informations.add(jt_req_6);
		informations.add(jb_req_6);

		informations.add(jl_req_7);
		informations.add(jt_req_7);
		informations.add(jb_req_7);

		resultats.setLayout(new BorderLayout());

		resultats.add(jl_resultats, BorderLayout.NORTH);
		resultats.add(jt_resultats);

		interactions.add(informations);
		interactions.add(resultats);

		frame.add(interactions);

		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
