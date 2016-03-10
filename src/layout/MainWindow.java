package layout;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import utils.Arquivo;
import utils.ImagePanel;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.UIManager;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	//Variaveis Globais ----------------------------------------
	private int sleep = 1000*2/3;
	private String shortResult = "";
	private JPanel contentPane;
	private ImagePanel imgDancarino1;
	private ImagePanel imgDancarino2;
	private static Arquivo in,out,log;
	private JList<String> listDancarino1,listDancarino2,listSolutionDancer1,listSolutionDancer2;
	private DefaultListModel<String> listModelSolutionDancer1 = new DefaultListModel<String>();  		
	private DefaultListModel<String> listModelSolutionDancer2 = new DefaultListModel<String>();
	private DefaultListModel<String> listModel,listModel2;
	private JButton btnDanceDance;
	private JTextField lblPassosDancarino1;
	private JTextField lblPassosDancarino2;
	private JButton btnResolverCega;
	private JLabel lbldance2now;
	private JLabel lbldance1now;
	private boolean enabledDancer1=true,enabledDancer2=true;
	private JButton btnResolverInformada;
	//----------------------------------------------------------
	

	//Funções --------------------------------------------------
	
	
	/** Descrição: Seta a imagem no painel.
	 *  Parametros: imgPanel - Painel que sofrerá a mudança.
	 * 				pathImage - Caminho da imagem.
	 *  Retorno: boolean - se conseguiu ou não alterar a imagem.*/
	private boolean setImagePanel(ImagePanel imgPanel, String pathImage){
		try{
			imgPanel.setImage(pathImage);
			return true;
		}catch(IOException e){
			return false;
		}
	}
	
	/** Descrição: Obtem o menor numero de passos da coreografia.
	 *  Parametros: Nenhum.
	 *  Retorno: String - A menor coreografia.*/
	private String getMenorPassos(){
		String dir = new File(".").getAbsolutePath();			
		try {
			String result="";
			out.reset();
			Process process = new ProcessBuilder(
					dir+"/solver",in.getPathName(),"out.txt","--informed").start();		
			process.waitFor();
			if(out.carregar().equals("1")){
				String index[];
				out.carregar();
				index=out.carregar().split(" ");
				for(int i = 0;i<index.length;i++)
					result += listModel.getElementAt(Integer.parseInt(index[i]));
				
				return result;					
			}
			
		} catch (InterruptedException|IOException e) {}
		return "";
	}
	
	/** Descrição: Altera o imgPanel baseado na sequencia de passos.
	 *  Parametros: aList - Qual lista que se obterá a sequencia de passos.
	 *  			aImgPanel - ImgPanel que sofrerá a mudança.
	 *  			aLabel - Label que mostrará qual passo está sendo dançado.
	 *  			numberDancer - Numero do dançarino que está dançando no momento.
	 *  Retorno: Void.*/
	private void changeImageFPS(JList<String> aList,ImagePanel aImgPanel,JLabel aLabel,String numberDancer){
		String selectedText = aList.getSelectedValue().toString();
		for(int i = 0;i<selectedText.length();i++){
			try {
				Thread.sleep(sleep);
			} catch(InterruptedException ex) {}
			setImagePanel(aImgPanel, "");
			aLabel.setText(selectedText.charAt(i)+"");
			if(!setImagePanel(aImgPanel, "IMG/Dance"+numberDancer+selectedText.charAt(i)+".png"))
				JOptionPane.showMessageDialog(contentPane, "Falha ao trocar a imagem do Dançarino "+numberDancer+" em "+
						"/IMG/Dance"+numberDancer+selectedText.charAt(i)+".png");
		}
	}
	
	/** Descrição: Altera o imgPanel baseado na sequencia de passos dos dois dançarinos.
	 *  Parametros: Nenhum.
	 *  Retorno: Void.*/
	private void changeImageFPSALL(){
		String selectedText1 = lblPassosDancarino1.getText();
		String selectedText2 = lblPassosDancarino2.getText();
		for(int i = 0;i<selectedText1.length();i++){
			try {
				Thread.sleep(sleep);
			} catch(InterruptedException ex) {}
			
			setImagePanel(imgDancarino1, "");
			setImagePanel(imgDancarino2, "");
			lbldance1now.setText(selectedText1.charAt(i)+"");
			lbldance2now.setText(selectedText2.charAt(i)+"");
			if(!setImagePanel(imgDancarino1, "IMG/Dance1"+selectedText1.charAt(i)+".png"))
				JOptionPane.showMessageDialog(contentPane, "Falha ao trocar a imagem do Dançarino 1 em "+
						"/IMG/Dance1"+selectedText1.charAt(i)+".png");
			if(!setImagePanel(imgDancarino2, "IMG/Dance2"+selectedText2.charAt(i)+".png"))
				JOptionPane.showMessageDialog(contentPane, "Falha ao trocar a imagem do Dançarino 2 em "+
						"/IMG/Dance2"+selectedText2.charAt(i)+".png");
		}
	}
	
	/** Descrição: Desabilita os controles do dançarino.
	 *  Parametros: dancer = numero do dançarino
	 *  			enabled = ativa/desativa.
	 *  Retorno: Void.*/
	private void disableDancer(int dancer, boolean enabled){
		if(dancer==1){
			enabledDancer1 = enabled;
			listDancarino1.setEnabled(enabled);
			listSolutionDancer1.setEnabled(enabled);
		}else if (dancer == 2){
			enabledDancer2 = enabled;
			listDancarino2.setEnabled(enabled);
			listSolutionDancer2.setEnabled(enabled);
		}
	}
	
	/** Descrição: Desabilita os controles do dançarino.
	 *  Parametros: dancer = numero do dançarino
	 *  			enabled = ativa/desativa.
	 *  Retorno: Void.*/
	private void disableButtons(boolean enabled){
		btnDanceDance.setEnabled(enabled);
		btnResolverCega.setEnabled(enabled);
		btnResolverInformada.setEnabled(enabled);
	}
	
	/** Descrição: Função que carrega o arquivo e inicializa os dançarinos.
	 *  Parametros: filePath = Caminho do arquivo.
	 *  Retorno: boolean = executou com sucesso.*/
	private boolean init(String filePath){
		try {
			in = new Arquivo(filePath);
			int sequenceNumber;
			sequenceNumber = Integer.parseInt(in.carregar());
			for(int i = 0;i<sequenceNumber;i++){
				listModel.addElement(in.carregar()); 
			}
			sequenceNumber = Integer.parseInt(in.carregar());
			for(int i = 0;i<sequenceNumber;i++){
				listModel2.addElement(in.carregar()); 
			}
			in.fecha();
		} catch (NumberFormatException | IOException e) {
			try {
				log.salvar(e.toString());
			} catch (IOException e1) {}
			return false;
		}
		return true;
	}
	
	//----------------------------------------------------------
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			log = new Arquivo("log.txt");
			in = new Arquivo("in.txt");
			out = new Arquivo("out.txt");
			out.deletarArquivo();
		} catch (IOException e1) {}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle(".:Jogo da Coreografia:.");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 612, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		/* DEFINIÇÃO DOS PAINEIS DE IMAGENS ------------------------------------------------------------*/
		imgDancarino1 = new ImagePanel();
		imgDancarino1.setKeepImageSize(true);
		imgDancarino1.setImageSizeFactor(3);
		imgDancarino1.setBounds(34, 70, 77, 127);
		contentPane.add(imgDancarino1);
		
		imgDancarino2 = new ImagePanel();
		imgDancarino2.setKeepImageSize(true);
		imgDancarino2.setImageSizeFactor(3);
		imgDancarino2.setBounds(399, 72, 77, 127);
		contentPane.add(imgDancarino2);
		/* FIM DEFINIÇÃO DOS PAINEIS DE IMAGENS ------------------------------------------------------------*/
		
		/* DEFINIÇÃO DAS LISTA ------------------------------------------------------------*/
		listModel = new DefaultListModel<String>();  		
		listModel2 = new DefaultListModel<String>();  
		
		init("in.txt");
		shortResult = getMenorPassos();
		/* FIM DEFINIÇÃO DAS LISTA ------------------------------------------------------------*/
		
		/* DEFINIÇÃO DO EVENTO CLICKED NA LISTA PARA ADICIONAR ------------------------------------------------------------*/
		JScrollPane scrollListDancarino1 = new JScrollPane();
		listDancarino1 = new JList<String>(listModel);
		scrollListDancarino1.setViewportView(listDancarino1);
		listDancarino1.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(enabledDancer1){
					disableDancer(1,false);
					new java.util.Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() { //Timer utilizado pra função ser executada após o evento				            	
				            	
					            	changeImageFPS(listDancarino1,imgDancarino1,lbldance1now,"1");
					            	String selectValue = listDancarino1.getSelectedValue();
					            	listModelSolutionDancer1.addElement(selectValue);
					            	lblPassosDancarino1.setText(lblPassosDancarino1.getText()+selectValue);
					            	disableDancer(1,true);
					            	
					            }
					        }, 
					        200
					);
				}
			}
		});
		scrollListDancarino1.setBounds(34, 209, 162, 104);
		contentPane.add(scrollListDancarino1);
		
		JScrollPane scrollListDancarino2 = new JScrollPane();
		listDancarino2 = new JList<String>(listModel2);
		scrollListDancarino2.setViewportView(listDancarino2);
		listDancarino2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(enabledDancer2){
					disableDancer(2,false);
					new java.util.Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() { //Timer utilizado pra função ser executada após o evento				            	
					            	changeImageFPS(listDancarino2,imgDancarino2,lbldance2now,"2");
					            	String selectValue = listDancarino2.getSelectedValue();
					            	listModelSolutionDancer2.addElement(selectValue);
					            	lblPassosDancarino2.setText(lblPassosDancarino2.getText()+selectValue);
					            	disableDancer(2,true);
					            }
					        }, 
					        200 
					);
				}
			}
		});
		scrollListDancarino2.setBounds(399, 209, 162, 104);
		contentPane.add(scrollListDancarino2);
		/* FIM DEFINIÇÃO DO EVENTO CLICKED NA LISTA PARA ADICIONAR------------------------------------------------------------*/
		
		JLabel lblJogoDaCoreografia = new JLabel("Jogo da Coreografia");
		lblJogoDaCoreografia.setHorizontalAlignment(SwingConstants.CENTER);
		lblJogoDaCoreografia.setBounds(216, 0, 151, 19);
		contentPane.add(lblJogoDaCoreografia);
		
		/* INICIO DEFINIÇÃO DO EVENTO CLICKED NA LISTA PARA REMOVER--------------------------------------------------------*/
		JScrollPane scrollListSolutionDancer1 = new JScrollPane();
		listSolutionDancer1 = new JList<String>(listModelSolutionDancer1);
		listSolutionDancer1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(enabledDancer1){
					disableDancer(1,false);
					listModelSolutionDancer1.remove(listSolutionDancer1.getSelectedIndex());
					lblPassosDancarino1.setText("");
					for(int i=0;i<listModelSolutionDancer1.getSize();i++){
						lblPassosDancarino1.setText(lblPassosDancarino1.getText()+ listModelSolutionDancer1.getElementAt(i));
					}
					disableDancer(1,true);
				}
			}
		});
		scrollListSolutionDancer1.setViewportView(listSolutionDancer1);
		scrollListSolutionDancer1.setBounds(198, 70, 85, 127);
		contentPane.add(scrollListSolutionDancer1);
		
		JScrollPane scrollListSolutionDancer2 = new JScrollPane();
		listSolutionDancer2 = new JList<String>(listModelSolutionDancer2);
		listSolutionDancer2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(enabledDancer2){
					disableDancer(2,false);
					listModelSolutionDancer2.remove(listSolutionDancer2.getSelectedIndex());
					lblPassosDancarino2.setText("");
					for(int i=0;i<listModelSolutionDancer2.getSize();i++){
						lblPassosDancarino2.setText(lblPassosDancarino2.getText()+ listModelSolutionDancer2.getElementAt(i));
					}
					disableDancer(2,true);
				}
			}
		});
		scrollListSolutionDancer2.setViewportView(listSolutionDancer2);
		scrollListSolutionDancer2.setBounds(300, 70, 85, 127);
		contentPane.add(scrollListSolutionDancer2);
		
		/* FIM DEFINIÇÃO DO EVENTO CLICKED NA LISTA PARA REMOVER--------------------------------------------------------*/
		
		/* INICIO DEFINIÇÃO DO EVENTO CLICKED DO BOTÃO DANCE--------------------------------------------------------*/
		btnDanceDance = new JButton("Dance, Dance!");
		btnDanceDance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				disableDancer(1,false);
				disableDancer(2,false);
				disableButtons(false);
				new java.util.Timer().schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() { //Timer utilizado pra função ser executada após o evento				            	
				            	changeImageFPSALL();
				            	disableDancer(1,true);
								disableDancer(2,true);
								disableButtons(true);
								if(lblPassosDancarino1.getText().equals(lblPassosDancarino2.getText())
										&&  !lblPassosDancarino1.getText().equals(""))
									if(shortResult.equals(lblPassosDancarino1.getText()))
										JOptionPane.showMessageDialog(contentPane, "Parabéns!! Você encontrou a menor "
												+ "coreografia!");
									else
										JOptionPane.showMessageDialog(contentPane, "Parabéns, você encontrou uma coreografia.");
				            }
				        }, 
				        200 
				);	
				
			}
		});
		btnDanceDance.setBounds(218, 220, 151, 25);
		contentPane.add(btnDanceDance);
		
		/* FIM DEFINIÇÃO DO EVENTO CLICKED DO BOTÃO DANCE--------------------------------------------------------*/
	
		lblPassosDancarino1 = new JTextField("");
		lblPassosDancarino1.setEditable(false);
		lblPassosDancarino1.setBounds(34, 43, 162, 19);
		contentPane.add(lblPassosDancarino1);
		
		lblPassosDancarino2 = new JTextField("");
		lblPassosDancarino2.setBounds(399, 43, 162, 19);
		lblPassosDancarino2.setEditable(false);
		contentPane.add(lblPassosDancarino2);
		
		/* INICIO DEFINIÇÃO DO EVENTO CLICKED DO BOTÃO RESOLVER CEGA--------------------------------------------------------*/
		btnResolverCega = new JButton("Resolver (Cega)");
		btnResolverCega.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String dir = new File(".").getAbsolutePath();
					out.reset();
					Process process = new ProcessBuilder(
							dir+"/solver",in.getPathName(),"out.txt").start();
					try {
						process.waitFor();
					} catch (InterruptedException e) {}
					if(out.carregar().equals("1")){
						listModelSolutionDancer1.clear();
						listModelSolutionDancer2.clear();
						String problemResult="";
						String index[],aux;
						
						out.carregar();
						index=out.carregar().split(" ");
						for(int i = 0;i<index.length;i++){
							aux = listModel.getElementAt(Integer.parseInt(index[i]));
							problemResult+=aux;
							listModelSolutionDancer1.addElement(aux);
						}
						out.carregar();
						index=out.carregar().split(" ");
						for(int i = 0;i<index.length;i++){
							aux = listModel2.getElementAt(Integer.parseInt(index[i]));
							listModelSolutionDancer2.addElement(aux);
						}
						
						lblPassosDancarino1.setText(problemResult);
						lblPassosDancarino2.setText(problemResult);
						
						btnDanceDance.doClick();

						
					
					}else{
						JOptionPane.showMessageDialog(contentPane, "Problema sem solução");
					}
				} catch (IOException e) {
					try {
						JOptionPane.showMessageDialog(contentPane, "Falha ao executar o solver");
						log.salvar(e.toString());
					} catch (IOException e1) {}
				}
			}
		});
		btnResolverCega.setBounds(216, 288, 151, 25);
		contentPane.add(btnResolverCega);
		
		/* FIM DEFINIÇÃO DO EVENTO CLICKED DO BOTÃO RESOLVER CEGA--------------------------------------------------------*/
		
		/* INICIO DEFINIÇÃO DO EVENTO CLICKED DO BOTÃO LIMPAR--------------------------------------------------------*/
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listModelSolutionDancer1.clear();
				listModelSolutionDancer2.clear();
				lblPassosDancarino1.setText("");
				lblPassosDancarino2.setText("");
				setImagePanel(imgDancarino1, "");
				setImagePanel(imgDancarino2, "");
				lbldance1now.setText("");
				lbldance2now.setText("");
			}
		});
		btnLimpar.setBounds(252, 33, 82, 25);
		contentPane.add(btnLimpar);
		
		/* FIM DEFINIÇÃO DO EVENTO CLICKED DO BOTÃO LIMPAR--------------------------------------------------------*/
		
		lbldance1now = new JLabel("");
		lbldance1now.setHorizontalAlignment(SwingConstants.CENTER);
		lbldance1now.setFont(new Font("Dialog", Font.BOLD, 40));
		lbldance1now.setBounds(126, 70, 70, 122);
		contentPane.add(lbldance1now);
		
		lbldance2now = new JLabel("");
		lbldance2now.setHorizontalAlignment(SwingConstants.CENTER);
		lbldance2now.setFont(new Font("Dialog", Font.BOLD, 40));
		lbldance2now.setBounds(491, 70, 70, 127);
		contentPane.add(lbldance2now);
		
		/* INICIO DEFINIÇÃO DO EVENTO CLICKED DO BOTÃO RESOLVER INFORMADA ----------------------------------------------------*/
		btnResolverInformada = new JButton("Resolver (Informada)");
		btnResolverInformada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					String dir = new File(".").getAbsolutePath();
					out.reset();			
					Process process = new ProcessBuilder(
							dir+"/solver",in.getPathName(),"out.txt","--informed").start();
					try {
						process.waitFor();
					} catch (InterruptedException e) {}
					
					if(out.carregar().equals("1")){
						listModelSolutionDancer1.clear();
						listModelSolutionDancer2.clear();
						String problemResult="";
						String index[],aux;
						
						out.carregar();
						index=out.carregar().split(" ");
						for(int i = 0;i<index.length;i++){
							aux = listModel.getElementAt(Integer.parseInt(index[i]));
							problemResult+=aux;
							listModelSolutionDancer1.addElement(aux);
						}
						out.carregar();
						index=out.carregar().split(" ");
						for(int i = 0;i<index.length;i++){
							aux = listModel2.getElementAt(Integer.parseInt(index[i]));
							listModelSolutionDancer2.addElement(aux);
						}
						
						lblPassosDancarino1.setText(problemResult);
						lblPassosDancarino2.setText(problemResult);
						
						btnDanceDance.doClick();
						
						
					
					}else{
						JOptionPane.showMessageDialog(contentPane, "Problema sem solução");
					}
				} catch (IOException e) {
					try {
						JOptionPane.showMessageDialog(contentPane, "Falha ao executar o solver");
						log.salvar(e.toString());
					} catch (IOException e1) {}
				}
			}
		});
		btnResolverInformada.setBounds(198, 325, 206, 25);
		contentPane.add(btnResolverInformada);
		
		/* FIM DEFINIÇÃO DO EVENTO CLICKED DO BOTÃO RESOLVER INFORMADA ----------------------------------------------------*/
		
		/* INICIO DEFINIÇÃO DO EVENTO CLICKED DO LABEL AJUDA ----------------------------------------------------*/
		JLabel label = new JLabel("?");
		label.setToolTipText("Clique para obter ajuda.");
		label.setFont(new Font("Dialog", Font.BOLD, 14));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {				
				Help.getInstance().setVisible(true);
			}
		});
		label.setForeground(UIManager.getColor("Button.darkShadow"));
		label.setBackground(UIManager.getColor("Button.highlight"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(590, 2, 15, 15);
		contentPane.add(label);
		
		/* FIM DEFINIÇÃO DO EVENTO CLICKED DO LABEL AJUDA ----------------------------------------------------*/
		
		/* INICIO DEFINIÇÃO DO EVENTO CLICKED DO BOTÃO IMPORTAR ----------------------------------------------------*/
		JButton btnImportar = new JButton("Importar");
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser fc = new JFileChooser();
				 fc.setCurrentDirectory(new File("."));
                 int res = fc.showOpenDialog(null); //Abre o dialogo para selecionar os arquivos
                 
                 if(res == JFileChooser.APPROVE_OPTION){
                	 btnLimpar.doClick();
                	 listModel.clear();
                	 listModel2.clear();
                     File file = fc.getSelectedFile();  
                     try {
                    	if(!init(file.getCanonicalPath()))
                    		JOptionPane.showMessageDialog(contentPane, "Falha ao carregar o arquivo, "
                    				+ "verifique se o arquivo está no padrão.");
                    	
                    	shortResult = getMenorPassos();
					} catch (HeadlessException | IOException e1) {
						JOptionPane.showMessageDialog(contentPane, "Falha ao carregar o arquivo");
						try {
							log.salvar(e.toString());
						} catch (IOException e2) {}
					}
                 }			
			}
		});
		btnImportar.setBounds(12, -3, 104, 22);
		contentPane.add(btnImportar);
		
		/* FIM DEFINIÇÃO DO EVENTO CLICKED DO BOTÃO RESOLVER INFORMADA ----------------------------------------------------*/
	}
}
