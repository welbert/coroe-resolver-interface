package layout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import utils.ImagePanel;

import javax.swing.JLabel;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class Help extends JFrame {

	private JPanel contentPane;
	JTextArea lblMensagem;

	JButton btnAvancar;
	JButton btnVoltar;
	
	private static Help instance;
	private int page = 1;
	private static final int MAX_PAGE = 9;

	private String getMessageTutorial(){
		String message="";
		
		switch (page) {
		case 1:
			message+="Essa é a visualização inicial do jogo.\n"
					+ " Os passos dos dançarinos são obtidos pelo arquivo 'in.txt' na pasta do executavel.\n"
					+ " A entrada é dada por: \n"
					+ " Primeira linha contém um numero inteiro n seguidas por n linhas contendo os passos do dançarino 1"
						+ " nessas linhas.\n"
					+ " Deve-se seguir esse padrão para o dançarino 2 nas linhas seguintes.\n"
					+ "Obs: Os passos devem possuir as seguintes letras 'a,d,e,m,p' .";
			break;
			
		case 2:
			message+="";
			break;	
			
		case 3:
			message+="";
			break;
			
		case 4:
			message+="";
			break;

		case 5:
			message+="";
			break;
			
		case 6:
			message+="";
			break;
			
		case 7:
			message+="";
			break;
			
		case 8:
			message+="";
			break;
			
		case 9:
			message+="";
			break;

		default:
			break;
		}
		return message;
	}
	
	/**
	 * Create the frame.
	 */
	private Help() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 629, 581);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImagePanel imgPanelTutorial = new ImagePanel();
		imgPanelTutorial.setBorder(new BevelBorder(BevelBorder.RAISED, Color.WHITE, null, null, null));
		imgPanelTutorial.setKeepImageSize(false);
		imgPanelTutorial.setBounds(12, 12, 603, 385);
		try {
			imgPanelTutorial.setImage("IMG/Help1.png");
		} catch (IOException e) {}
		contentPane.add(imgPanelTutorial);
		
		JScrollPane scrollListMessage = new JScrollPane();
		JTextArea lblMensagem = new JTextArea("");
		lblMensagem.setBackground(new Color(238, 238, 238));
		scrollListMessage.setViewportView(lblMensagem);
		lblMensagem.setLineWrap(true);
		lblMensagem.setEditable(false);
		lblMensagem.setText(getMessageTutorial());
		scrollListMessage.setBounds(22, 409, 581, 94);
		contentPane.add(scrollListMessage);
	
		btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(page>1 && page<=MAX_PAGE){
					try {
						page--;
						btnAvancar.setEnabled(true);
						imgPanelTutorial.setImage("IMG/Help"+page+".png");						
						if(page==1)
							btnVoltar.setEnabled(false);
					} catch (IOException ex) {}
				}
			}
		});
		btnVoltar.setEnabled(false);
		btnVoltar.setBounds(186, 515, 83, 25);
		contentPane.add(btnVoltar);
		
		btnAvancar = new JButton("Avançar");
		btnAvancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(page>=1 && page<MAX_PAGE){
					try {
						page++;
						btnVoltar.setEnabled(true);
						imgPanelTutorial.setImage("IMG/Help"+page+".png");						
						if(page==MAX_PAGE)
							btnAvancar.setEnabled(false);
					} catch (IOException ex) {}
				}
					
			}
		});
		btnAvancar.setBounds(351, 515, 90, 25);
		contentPane.add(btnAvancar);
	}
	
	public static Help getInstance(){
		if(instance == null)
			instance = new Help();
		return instance;
	}
}
