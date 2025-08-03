import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class CampoMinado  {
    private class Celula extends JButton{
        int c;
        int l;
        public Celula(int l, int c){
            this.c = c;
            this.l = l;
        }
    }


    int tamanhoBotao = 70;
    int colunas;
    int linhas;
    int qtdeBombas;
    int qtdeBandeiras = 0;
    

    int larguraFrame;
    int alturaFrame;
    
    
    JFrame frame = new JFrame("Campo Minado");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel painel = new JPanel();
    Celula[][] campo;

    
    ArrayList<Celula> bombas; 
    int celulasReveladas=0;
    boolean fimDeJogo = false;
    Random random = new Random();


    CampoMinado(int linha, int coluna, int dificuldade){

        colunas = coluna;
        linhas = linha;
        qtdeBombas = dificuldade;
        larguraFrame = tamanhoBotao * colunas;
        alturaFrame = tamanhoBotao * linhas;
        campo = new Celula[linhas][colunas];


        frame.setSize(larguraFrame,alturaFrame);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //alterar aqui para so permitir um jogo
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Campo Minado: " + Integer.toString(qtdeBombas));
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);
        
        painel.setLayout(new GridLayout(linhas, colunas));
        for(int i=0;i<linhas;i++){
            for(int j=0;j<colunas;j++){
                Celula celula = new Celula(i, j);
                campo[i][j]=celula;

                celula.setFocusable(false);
                celula.setMargin(new Insets(0, 0, 0, 0));
                celula.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
                celula.setText("");


                celula.addMouseListener(new MouseAdapter() {
                   @Override
                    public void mousePressed(MouseEvent e){
                        Celula celula = (Celula) e.getSource();
                        if(fimDeJogo){
                            return;
                        }
                        //esquerdo
                        if(e.getButton() == MouseEvent.BUTTON1){
                            if(celula.getText()!="🏴"){
                                if(bombas.contains(celula)){
                                revelarBombas();
                            }else{
                                revelarCelula(celula.l,celula.c);
                            }
                            }
                        }
                        //direito
                        if(e.getButton()==MouseEvent.BUTTON3){
                            if(celula.getText() == "" && celula.isEnabled()){
                                celula.setText("🏴");
                                qtdeBandeiras++;
                                textLabel.setText("Campo Minado: " + Integer.toString(qtdeBombas - qtdeBandeiras));
                            }else if(celula.isEnabled()){
                                celula.setText("");
                                qtdeBandeiras--;
                                textLabel.setText("Campo Minado: " + Integer.toString(qtdeBombas - qtdeBandeiras));
                            }
                        }
                            
                    }
                });


                painel.add(celula);
            }
        }
        frame.add(painel);



        
        frame.setVisible(true);
        criaBomba();
    }

    void criaBomba(){
        bombas = new ArrayList<Celula>();
        int bombasFaltando = qtdeBombas;
        while(bombasFaltando > 0){

            int l=random.nextInt(linhas);
            int c=random.nextInt(colunas);
            Celula bombaNova = campo[l][c];
            if(!bombas.contains(bombaNova)){
                bombas.add(bombaNova);
                bombasFaltando--;
            }
        }
        
    
    }


    

    void revelarBombas(){
        for(int i=0;i<bombas.size();i++){
            Celula bomba = bombas.get(i);
            bomba.setText("💣");
        }
        textLabel.setText("Você Perdeu!");
        fimDeJogo=true;
    }



    void revelarCelula(int l, int c){
        if(l>=linhas || l<0 || c>=colunas || c<0){
            return;
        }
        
        Celula celula = campo[l][c];
        if(!celula.isEnabled()){
            return;
        }
        celula.setEnabled(false);
        Integer bombasProx=0;

        //3 de cima
        bombasProx+=contaBomba(l-1,c-1);
        bombasProx+=contaBomba(l-1,c);
        bombasProx+=contaBomba(l-1,c+1);

        //2 do meio
        bombasProx+=contaBomba(l,c-1);
        bombasProx+=contaBomba(l,c+1);

        //3 de baixo
        bombasProx+=contaBomba(l+1,c-1);
        bombasProx+=contaBomba(l+1,c);
        bombasProx+=contaBomba(l+1,c+1);
       
        celulasReveladas++;
        if(celulasReveladas==(colunas*linhas)-bombas.size()){
            fimDeJogo=true;
            textLabel.setText("Você ganhou!");
        }
        if(bombasProx==0){
            
            celula.setText("");

            //3 de cima
            revelarCelula(l-1,c-1);
            revelarCelula(l-1,c);
            revelarCelula(l-1,c+1);

            //2 do meio
            revelarCelula(l,c-1);
            revelarCelula(l,c+1);

            //3 de baixo
            revelarCelula(l+1,c-1);
            revelarCelula(l+1,c);
            revelarCelula(l+1,c+1);

        }else celula.setText(Integer.toString(bombasProx));
    }

    int contaBomba(int l, int c){
        if(l>=linhas || l<0 || c>=colunas || c<0){
            return 0;
        }
        if(bombas.contains(campo[l][c])){
            return 1;
        }else return 0;
    }


}


//o inicio do jogo sempre sera uma celula vazia
//deixar a tela do menu bonita
//só permitir um jogo por vez