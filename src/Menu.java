import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class Menu {
    int linhas;
    int colunas;
    boolean jogoAtivo=false;
    String[] opcoes = {"Fácil", "Médio", "Difícil"};

    JFrame frameMenu = new JFrame("Campo Minado");
    JPanel painelMenu = new JPanel();
    JLabel textoMenu = new JLabel();
    JLabel textoLinhas = new JLabel();
    JLabel textoColunas = new JLabel();
    JLabel textoDificuldade = new JLabel();
    JSpinner spinnerLinhas = new JSpinner(new SpinnerNumberModel(3,3,20,1));
    JSpinner spinnerColunas = new JSpinner(new SpinnerNumberModel(3,3,20,1));
    JComboBox CBDificuldade = new JComboBox<>(opcoes);
    JButton botaoIniciar = new JButton();


    Menu(){

        frameMenu.setSize(400,200);
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setResizable(false);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setLayout(new BorderLayout());


        textoMenu.setText("Insira o tamanho e a dificuldade:");
        textoMenu.setFont(new Font("Arial", Font.BOLD, 15));
        textoMenu.setHorizontalAlignment(JLabel.CENTER);
        textoMenu.setOpaque(true);


        textoLinhas.setText("Insira quantas linhas (min 3, max 20):");
        textoLinhas.setFont(new Font("Arial", Font.BOLD, 12));
        textoLinhas.setHorizontalAlignment(JLabel.LEFT);
        textoLinhas.setOpaque(true);

        
        textoColunas.setText("Insira quantas colunas (min 3, max 20):");
        textoColunas.setFont(new Font("Arial", Font.BOLD, 12));
        textoColunas.setHorizontalAlignment(JLabel.LEFT);
        textoColunas.setOpaque(true);

        textoDificuldade.setText("Escolha a dificuldade:");
        textoDificuldade.setFont(new Font("Arial", Font.BOLD, 12));
        textoDificuldade.setHorizontalAlignment(JLabel.LEFT);
        textoDificuldade.setOpaque(true);

        
        

        botaoIniciar.setText("Iniciar");
        

        
        painelMenu.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
       

        
        //texto insira
        
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        painelMenu.add(textoMenu,gbc);

        gbc.insets = new Insets(5, 5, 5, 5);
        //linhas
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        painelMenu.add(textoLinhas, gbc);


        //colunas
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        painelMenu.add(textoColunas, gbc);

        //dificuldade
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        painelMenu.add(textoDificuldade, gbc);


        //spinner linha
        gbc.anchor=GridBagConstraints.EAST;
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        painelMenu.add(spinnerLinhas, gbc);

        //spinner coluna
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        painelMenu.add(spinnerColunas, gbc);

        //combobox Dificuldade
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        painelMenu.add(CBDificuldade, gbc);

        // botao
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        painelMenu.add(botaoIniciar, gbc);

       
        botaoIniciar.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e){
                if(e.getButton() == MouseEvent.BUTTON1){
                    iniciarJogo();
                }
            }
            
        });


        
        frameMenu.add(painelMenu,  BorderLayout.NORTH);

        

        frameMenu.setVisible(true);

    }


    void iniciarJogo(){
        if(jogoAtivo){
            return;
        }
        else{
            Integer linha = (Integer) spinnerLinhas.getValue();
            Integer coluna = (Integer) spinnerColunas.getValue();
            if(2<linha && linha<21 && 2<coluna && coluna<21 && CBDificuldade.getSelectedIndex()!=-1){
                jogoAtivo=true;
                double d=0;
                String difficulty = (String) CBDificuldade.getSelectedItem();
                switch(difficulty){
                    case "Fácil": d = (int) (linha*coluna)*0.2;
                                  break;
                    case "Médio": d = (int) (linha*coluna)*0.35;
                                  break;
                    case "Difícil": d = (int) (linha*coluna)*0.5;
                                  break;
                }
                int dificuldade = (int) d;
                CampoMinado campominado = new CampoMinado(linha, coluna, dificuldade);
            }
        }
    }
}
