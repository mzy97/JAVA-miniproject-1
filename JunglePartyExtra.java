
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import javax.swing.*;

/**
 * Title: JunglePartyExtra.java
 * <p>Description: JungleParty is a simple application used by children 
 * to practice their counting skills,When launched, a random number of 
 * animal images ranging from 1 to 10 are displayed and the user is expected 
 * to enter a number and click "Check", it provide input check, Click on animals 
 * you wish to kick out of the party. User can determine the number of picture to 
 * show using command line argument.
 * @author Zhengyang Mu
 * @version 1.5
 */

public class JunglePartyExtra extends JFrame{

	
	/**
	 * default serialVersionUID to eliminate threat.
	 */
	private static final long serialVersionUID = 1L;
	//class variable to create the format, containers for components.
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel panel4;
	// Arraylist to store labels.
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	//Components used in GUI.
	JLabel label;
	JButton button;
	JTextField text;
	// Number of pictures to show in GUI.
	int numberToShow = 10;
	// Users input max number to show
	int control;
	/**
	 * Constructor of Class.
	 * <p>This constructor including basic settings of JFrame, the layout of panels, <br>
	 * how these panels were constructed and piled up.
	 * @param input The max number of pictures to show
	 */
	public JunglePartyExtra(String input){
		// Set window title
		super("Welcome to the jungle party");
		// Setting max number
		control = Integer.parseInt(input);
		numberToShow = control;
		// Basic settings of JFrame (Window).
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(900, 750);
		this.setLocationRelativeTo(null); 
		// Instantiation of panels.
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		// Set layouts.
		panel1.setLayout(new GridLayout(0, 5));
		panel2.setLayout(new BorderLayout());
		panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel4.setLayout(new FlowLayout(FlowLayout.CENTER));
		// Add panels to containers.
		this.getContentPane().add(BorderLayout.CENTER,panel1);
		this.getContentPane().add(BorderLayout.SOUTH,panel2);
		panel2.add(panel3, BorderLayout.CENTER);
		panel2.add(panel4, BorderLayout.NORTH);
		
	}
	/**
	 * Instantiation of components and put them on the container.
	 */
	public void go(){
		// Using loop and array, adding pictures to labels and add them to the container.
		JLabel[] labelsArray = new JLabel[control];
		for(int i=0;i<=(control-1);i++){
			ImageIcon image = new ImageIcon("jungle\\animal"+(i+1)+".png");
			labelsArray[i] = new JLabel(image);
			panel1.add(labelsArray[i]);
			labels.add(labelsArray[i]);
		}
		

		
		label = new JLabel("How many animals are in the party now?",JLabel.CENTER);
		panel4.add(label);
		
		button = new JButton("Check!");
		panel3.add(button);
		
		text = new JTextField(2);
		panel4.add(text);
		
		JLabel text2 = new JLabel("Click on animals you wish to kick out of the party!",JLabel.CENTER);
		// Set color of text.
		text2.setForeground(Color.red);
		panel2.add(text2, BorderLayout.SOUTH);
		
//		panel1.validate();
//		panel2.validate();
//		panel3.validate();
//		panel4.validate();
		panel2.setVisible(false);
		panel2.setVisible(true);
		
	}
	/**
	 * Add ActionListener to the button. Add the function that click picture to make it gone.
	 * Add DocumentListener to textfield
	 */
	public void game(){
		button.addActionListener(new ButtonAction());
		for (JLabel i:labels){
			i.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					if(i.isVisible()){
					i.setVisible(false);
					label.setText("Animal gone! How many animals are in the party now?");
					numberToShow--;
					}
				}
			});
		}
		Document doc = text.getDocument();
		doc.addDocumentListener(new TextCheck());
		
	}
	/**
	 * Title: TextCheck
	 * <p>Checking the input in textfield at real time, any time.<br>
	 * And let user know he input a Invalid input
	 * @author Zhengyang Mu
	 * @version 1.2
	 */
	class TextCheck implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			Document doc1 = e.getDocument();
			
			String s = null;
			try {
				s = doc1.getText(0, doc1.getLength());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			} 
			if(!checkinput(s)){
				JOptionPane.showMessageDialog(null, "Invalid input, please check","check infomation",JOptionPane.ERROR_MESSAGE);
			}	
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
	
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
		}
			
		}

	/**
	 * Check if input in the range 0-10 including 0 and 10.
	 * @param a Text which user input.
	 * @return isRight The state to show if it pass the check
	 */
	public boolean checkinput(String a){
		ArrayList<String> check = new ArrayList<>();
		Boolean isRight = false;
		for(int i=0;i<=control;i++){
			check.add(""+i+"");
		}
		for(String i : check){
			if(a.equals(i)){
				isRight=true;
			}
			
		}
		return isRight;
		
	}
	/**
	 * Title: ButtonAction
	 * <p>The button action when button have been clicked.<br>
	 * plus: The checking of input.
	 * @author Zhengyang Mu
	 * @version 1.5
	 */
	class ButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// Get text and check input.
			String get = text.getText();
			// If check passed, continue.
			if(checkinput(get)){
			int check = Integer.parseInt(get);
			// Determine whether input is right and do following things.
			if(check == numberToShow){
				// If right, change number of pictures to show, change text and clear input.
				label.setText("Correct! How many animals are in the party now?");
				text.setText("");
				for(JLabel i:labels){
					i.setVisible(true);
				}
				numberToShow = (int)(Math.random()*control+1);
				for(int i=numberToShow;i<control;i++){
					JLabel temp = labels.get(i);
					temp.setVisible(false);
				}
			}
			// If wrong, clear input.
			else{
				label.setText("Wrong! Try again!");
				text.setText("");
				}
			
			}
		// If check fail, make user notice, clear input.
		else{
			JOptionPane.showMessageDialog(null, "Wrong input, please check","check infomation",JOptionPane.ERROR_MESSAGE);
			text.setText("");
		}
		}	
	}
	/**
	 * Game Launcher
	 * @param args User's input
	 */
	public static void main(String[] args) {
		try{
		int check = Integer.parseInt(args[0]);
		if(!(check>=10&&check<=20)){
			System.out.println("Ilegal input, please check and run this program again.");
			System.exit(0);
			}
		}
		catch(Exception e){
		System.out.println("Ilegal input, you probably didn't input arguments, please check and run this program again.");
		System.exit(0);
		}
		JunglePartyExtra test = new JunglePartyExtra(args[0]);
		test.setVisible(true);
		test.go();
		test.game();

	}

}


