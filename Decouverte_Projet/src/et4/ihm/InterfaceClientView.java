package et4.ihm;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class InterfaceClientView extends JFrame {

	private int onglet;
	private JPanel pContainer, rContainer;
	{

		this.onglet = 0;
		this.pContainer = new JPanel();
		this.rContainer = new JPanel();
	}
	
	public InterfaceClientView(){
		
		setSize(365, 620);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setResizable(false);
		Container pane = getContentPane();
		pane.setBackground(Color.white);
		pane.setLayout(new FlowLayout(0, 0, 0));

		JPanel titre = new JPanel();
		titre.setPreferredSize(new Dimension(350, 50));
		titre.add(new JLabel("Chinois Facile"));
		titre.setBackground(Color.white);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel ongletRecherche = new JPanel();
		ongletRecherche.setPreferredSize(new Dimension(350, 340));
		BoxLayout ongletRLayout = new BoxLayout(ongletRecherche,BoxLayout.Y_AXIS);
		ongletRecherche.setLayout(ongletRLayout);
		
		JPanel inputR = new JPanel();
		JTextField jTFR = new JTextField();
		JLabel jLR = new JLabel("Recherche ");
		BoxLayout inputRLayout = new BoxLayout(inputR,BoxLayout.X_AXIS);
		inputR.setLayout(inputRLayout);
		inputR.add(jLR);
		inputR.add(jTFR);
		
		ongletRecherche.add(inputR);
		
		JPanel ongletConnaissances = new JPanel();
		ongletConnaissances.setPreferredSize(new Dimension(350, 340));
		
		JPanel ongletApprentissage = new JPanel();
		ongletApprentissage.setPreferredSize(new Dimension(350, 340));
		
		tabbedPane.addTab("Recherche", null, ongletRecherche,
				"Pour rechercher un mot ou un ensemble de mot dans notre corpus");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.setBackground(Color.white);
		
		tabbedPane.addTab("Connaissances", null, ongletConnaissances,
				"Affiche vos mot connues");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		tabbedPane.setBackground(Color.white);
		
		tabbedPane.addTab("Apprentissage", null, ongletApprentissage,
				"Pour enrichir vos connaissancs sur le chinois");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		tabbedPane.setBackground(Color.white);
		
		pane.add(titre);
		pane.add(tabbedPane);
		setVisible(true);
	}
	
	public static void main(String[] args) {

		InterfaceClientView chinoisFacile = new InterfaceClientView();

	}
}
