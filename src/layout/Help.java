package layout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import utils.ImagePanel;

import java.io.IOException;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class Help extends JFrame {

	private JPanel contentPane;
	JTextArea lblMensagem;

	JButton btnAvancar;
	JButton btnVoltar;
	
	private static Help instance;
	private int page = 1;
	private static final int MAX_PAGE = 9;


	/** Descrição: Retorna a mensagem de ajuda que deve ser mostrado na tela baseado na pagina atual*/
	private String getMessageTutorial(){
		String message="";
		
		switch (page) {
		case 1:
			message+="Essa é a visualização inicial do jogo.\n"
					+ " Os passos dos dançarinos são obtidos pelo arquivo 'in.txt' na pasta do executável,"
					+ 	" mas podem ser alterados pelo botão importar no canto superior esquerdo.\n"
					+ " A entrada é dada por: \n"
					+ " Primeira linha contém um numero inteiro n seguidas por n linhas contendo os passos do dançarino 1"
						+ " nessas linhas.\n"
					+ " Deve-se seguir esse padrão para o dançarino 2 nas linhas seguintes.\n"
					+ "Obs: Os passos devem possuir as seguintes letras 'a,d,e,m,p' .";
			break;
			
		case 2:
			message+="Para selecionar os passos, clique em algum passo da lista e espere o dançarino executar seus movimentos.";
			break;	
			
		case 3:
			message+="Os passos selecionados ficaram disponíveis em na seleção 1.\n"
					+ "Os passos que estão sendo dançados ficam na seleção 2.\n"
					+ "A junção de todos passos dançados ficam na seleção 3.";
			break;
			
		case 4:
			message+="Caso você tenha selecionado um passo errado, não se preocupe, você pode clicar na lista para removê-lo.";
			break;

		case 5:
			message+="E se você selecionou passos demais e quer limpar tudo? Não tem problema, aperte o botão 'Limpar'.";
			break;
			
		case 6:
			message+="Fez um conjunto de passos e quer verificar se acertou? Então dance, dance! e veja o resultado.";
			break;
			
		case 7:
			message+="Cansou de tentar? Você pode pedir ao programa mostrar uma resposta pra você, caso ela exista.\n"
					+ "A busca pela resposta é o algoritmo de 'Busca em largura', ela não vai te dar "
					+ "garantia do menor passo em comum, mas pelo menos mata sua curiosidade de saber a resposta "
					+ "ou economiza seu tempo.";
			break;
			
		case 8:
			message+="Resolve o problema utilizando a 'A*', buscando a menor sequência de passo em comum"
					+ " para se formar a coreografia.";
			break;
			
		case 9:
			message+="Parabéns, você completou o desafio! Mas não será que existem passos menores?";
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
		contentPane.setFocusable(true);
		contentPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
					btnVoltar.doClick();
				else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
					btnAvancar.doClick();
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImagePanel imgPanelTutorial = new ImagePanel();
		imgPanelTutorial.setBorder(new BevelBorder(BevelBorder.RAISED, Color.WHITE, null, null, null));
		imgPanelTutorial.setKeepImageSize(false);//A imagem terá o tamanho do canvas
		imgPanelTutorial.setBounds(12, 12, 603, 385);
		try {
			imgPanelTutorial.setImage("IMG/Help1.png");//Primeira imagem do tutorial
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
				if(page>1 && page<=MAX_PAGE){//Evita que passe do numero de imagens
					try {
						page--;
						btnAvancar.setEnabled(true);
						imgPanelTutorial.setImage("IMG/Help"+page+".png");
						lblMensagem.setText(getMessageTutorial());
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
				if(page>=1 && page<MAX_PAGE){//Evita que passe do numero de imagens
					try {
						page++;
						btnVoltar.setEnabled(true);
						imgPanelTutorial.setImage("IMG/Help"+page+".png");
						lblMensagem.setText(getMessageTutorial());
						if(page==MAX_PAGE)
							btnAvancar.setEnabled(false);
					} catch (IOException ex) {}
				}
					
			}
		});
		btnAvancar.setBounds(351, 515, 90, 25);
		contentPane.add(btnAvancar);
	}
	
	public static Help getInstance(){ //Singleton
		if(instance == null)
			instance = new Help();
		return instance;
	}
}
