package Principales;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;

import Database.ConnectionSingleton;

public class Principale {

	public static void main(String[] args) {
		JFrame frame = new JFrame("OnlineLib");
		frame.setLayout(new BorderLayout());
		
		ConnectionSingleton cs = null;

		JPanel interfaces = new JPanel(new GridLayout());
		JPanel interaction = new JPanel();
		JPanel logins = new JPanel();
		JPanel connexion = new JPanel();
		JLabel jl_login = new JLabel("Utilisateur");
		JTextField jt_login = new JTextField();
		JLabel jl_password = new JLabel("Mot de passe");
		JPasswordField jp_password = new JPasswordField();
		JLabel jl_url = new JLabel("URL");
		JTextField jt_url = new JTextField();

		interfaces.setPreferredSize(new Dimension(600, 150));
		interfaces.setLayout(new GridLayout(1,2));
		
		logins.setLayout(new GridLayout(6, 1));
		logins.add(jl_login);
		logins.add(jt_login);
		logins.add(jl_password);
		logins.add(jp_password);
		logins.add(jl_url);
		logins.add(jt_url);

		JButton jb_connexion = new JButton("Connexion");
		jb_connexion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Connection c = DriverManager.getConnection(jt_url.getText(), jt_login.getText(), jp_password.getText());
				} catch (SQLException e) {
					System.err.println("SQLException: " + e.getMessage());
				}
			}
		});
		JButton jb_deconnexion = new JButton("Deconnexion");
		jb_deconnexion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cs.close();

			}
		});
		
		//connexion.setPreferredSize(new Dimension(0, 0));
		connexion.setLayout(new GridLayout(2,1));
		connexion.add(jb_connexion);
		connexion.add(jb_deconnexion);
		
		interfaces.add(logins);
		interfaces.add(connexion);
		frame.add(interfaces, BorderLayout.NORTH);
		
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
