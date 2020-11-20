package Principales;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

import DAO.Requetes;
import oracle.jdbc.driver.OracleDriver;

import Database.ConnectionSingleton;

public class Principale {

	private ConnectionSingleton cs;
	private Connection c;

	public static void main(String[] args) {
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
				String url = "jdbc:oracle:thin:@localhost:1521:XE";
				Requetes request = new Requetes(
						ConnectionSingleton.getInstance(url, jt_user.getText(), jp_password.getText()).getConnection());
				request.creationBdd("valeursProjetBdd.txt");
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
					if (ConnectionSingleton.getInstance() != null && !ConnectionSingleton.getInstance().getConnection().isClosed()) {
						Requetes request = new Requetes(ConnectionSingleton.getInstance().getConnection());
						jt_resultats.setText("");
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
				Requetes request = new Requetes(ConnectionSingleton.getInstance().getConnection());
				jt_resultats.setText("");

			}
		});
		JButton jb_req_3 = new JButton("Executer la requete 3");
		jb_req_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Requetes request = new Requetes(ConnectionSingleton.getInstance().getConnection());
				jt_resultats.setText("");

			}
		});
		JButton jb_req_4 = new JButton("Executer la requete 4");
		jb_req_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Requetes request = new Requetes(ConnectionSingleton.getInstance().getConnection());
				jt_resultats.setText("");

			}
		});
		JButton jb_req_5 = new JButton("Executer la requete 5");
		jb_req_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Requetes request = new Requetes(ConnectionSingleton.getInstance().getConnection());
				jt_resultats.setText("");

			}
		});
		JButton jb_req_6 = new JButton("Executer la requete 6");
		jb_req_6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Requetes request = new Requetes(ConnectionSingleton.getInstance().getConnection());
				jt_resultats.setText("");

			}
		});
		JButton jb_req_7 = new JButton("Executer la requete 7");
		jb_req_7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Requetes request = new Requetes(ConnectionSingleton.getInstance().getConnection());
				jt_resultats.setText("");

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
