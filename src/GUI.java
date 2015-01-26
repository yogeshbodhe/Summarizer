import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GUI{

	/**
	 * @param args
	 */
	
	JFrame frame;
	JPanel panel1;
	JLabel fileLabel,compressionRateLabel,categoryLabel;
	JTextField fileTextField;
	JButton browse;
	JComboBox compressionRateComboBox,categoryComboBox;
	JButton summarize;
	JTextArea textArea;
	JScrollPane scrollPane;
	
	File file;
	String[] summarySentences;
	String[] rates={"20","25","30","35","40","45","50"};
	String[] categories={"Sports","Technical"};
	
	public GUI(){
	
		// TODO Auto-generated method stub

		frame=new JFrame("Summarizer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 600);
		frame.setLocation(80,60);
		frame.setLayout(new BorderLayout());
		
		panel1=new JPanel(new GridLayout(1, 7));
		
		fileLabel=new JLabel("File:");
		panel1.add(fileLabel);
		
		fileTextField=new JTextField();
		panel1.add(fileTextField);
		
		browse=new JButton();
		browse.setText("Browse");
		summarize=new JButton();
		summarize.setText("Summarize");
		
		panel1.add(browse);
		
		compressionRateLabel=new JLabel("Compression Rate:");
		panel1.add(compressionRateLabel);
		
		compressionRateComboBox=new JComboBox(rates);
		panel1.add(compressionRateComboBox);
		
		categoryLabel=new JLabel("Category:");
		panel1.add(categoryLabel);
		
		categoryComboBox=new JComboBox(categories);
		panel1.add(categoryComboBox);
		
		frame.add(panel1, BorderLayout.NORTH);
		
		frame.add(summarize, BorderLayout.SOUTH);
		
		textArea=new JTextArea();
		textArea.setFont(new Font("Times New Roman", Font.BOLD, 20));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane=new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.add(scrollPane, BorderLayout.CENTER);
		
		browse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser=new JFileChooser();
				int val=fileChooser.showOpenDialog(null);
				if(val==JFileChooser.APPROVE_OPTION){
					file=fileChooser.getSelectedFile();
					fileTextField.setText(file.getName());
				}
				else{
					fileTextField.setText("Please enter a filename");
				}
			}
		});
		
		summarize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int rate=compressionRateComboBox.getSelectedIndex();
				int category=categoryComboBox.getSelectedIndex();
				try{
					summarySentences=Coordinator.coordinate(file,rate,category);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				textArea.setText("");
				for(int i=0;i<summarySentences.length;i++){
					textArea.append(summarySentences[i]+"\n");
				}
			}
		});
		
		frame.setVisible(true);
	}
	public static void main(String[] args){
		new GUI();
	}
}
