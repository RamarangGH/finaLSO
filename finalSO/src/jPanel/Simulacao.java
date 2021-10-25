package jPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Simulacao {
    SequenciaPaginas paginas;
    int tipoSimulacao; //0: fifo, 1: otm, 2: lru, 3: sc, 4: wsm
    float hitRate; //em %
    int pageFaults = 0;
    int numeroQuadrosRam; // varia de 1 a 7
    JFrame frameSimu;
    JLabel pageFLabel;
    JLabel hitRLabel;

    public Simulacao(SequenciaPaginas paginas){
        this.pageFaults = 0;
        this.tipoSimulacao = 0;
        this.hitRate = 0;
        this.numeroQuadrosRam = 7;
        this.paginas = paginas;
        this.frameSimu = new JFrame("Simulacao");
        pageFLabel = new JLabel ("0");
        hitRLabel = new JLabel("0");

    }

    public float getHitRate(){ return this.hitRate; }
    public int getPageFaults(){ return this.pageFaults; }
    public int[] getSequenciaPaginas(){ return this.paginas.getSequencia(); }
    public void setNumeroQuadros(int numero){ this.numeroQuadrosRam = numero; }
    public int getNumeroQuadros(){ return this. numeroQuadrosRam; }
    public void addPageFault(){
        this.pageFaults += 1;
        pageFLabel.setText(Integer.toString(pageFaults));
    }
    public void setHitRate(){
        this.hitRate = (float) (1-(float) this.pageFaults/(float) paginas.getSequencia().length)*100;
        hitRLabel.setText(Float.toString(hitRate)); 
       }
    public void setTipoSimulacao(int tipo){ this.tipoSimulacao = tipo; }
    public void setSequencia(int[] paginas){ this.paginas.setSequencia(paginas); }

    public void gerarJanela(){ //geramento da janela
        this.frameSimu.setSize(1000,200);
        //criacao das linhas
        JPanel l1 = new JPanel();
        JPanel l2 = new JPanel();
        JPanel l3 = new JPanel();
        JPanel l4 = new JPanel();
        JPanel l5 = new JPanel();
        JPanel l6 = new JPanel();

        l1.setLayout(new BoxLayout(l1, BoxLayout.LINE_AXIS));
        l2.setLayout(new BoxLayout(l2, BoxLayout.LINE_AXIS));
        l3.setLayout(new BoxLayout(l3, BoxLayout.LINE_AXIS));
        l4.setLayout(new BoxLayout(l4, BoxLayout.LINE_AXIS));
        l5.setLayout(new BoxLayout(l5, BoxLayout.LINE_AXIS));
        l6.setLayout(new BoxLayout(l6, BoxLayout.LINE_AXIS));
        JButton fechar = new JButton("FECHAR PROGRAMA");
        fechar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ 
                System.exit(0);
            }
        });
        l1.add(new JLabel("Numeros Paginas : "));
        l1.add(new JLabel(Arrays.toString(paginas.getSequencia())));
        l2.add(new JLabel("Page Faults : "));
        l2.add(this.pageFLabel);
        l3.add(new JLabel("Hit Rate : "));
        l3.add(this.hitRLabel);
        l6.add(fechar);
        JLabel algoritmo;
        if(tipoSimulacao == 0){
            algoritmo = new JLabel("Algoritmo : FIFO - First In First Out");
        }else if(tipoSimulacao ==1){
            algoritmo = new JLabel("Algoritmo : OTM - Optimal");
        }else if(tipoSimulacao == 2){
            algoritmo = new JLabel("Algoritmo : LRU - Last Recently Used");
        }else if(tipoSimulacao == 3){
            algoritmo = new JLabel("Algoritmo : SC - Second Chance");
        }else if(tipoSimulacao == 4){
            algoritmo = new JLabel("Algoritmo : WSM - Working Set Model");
        }else{
            algoritmo = new JLabel("Algoritmo : none");
        }
        l4.add(algoritmo);
        l5.add(new JLabel("Quantidade Quadros de RAM : "));
        l5.add(new JLabel(Integer.toString(this.getNumeroQuadros())));

        
        JPanel boxsimu = new JPanel();
        boxsimu.setLayout(new BoxLayout(boxsimu, BoxLayout.PAGE_AXIS));
        boxsimu.add(l1);
        boxsimu.add(l2);
        boxsimu.add(l3);
        boxsimu.add(l4);
        boxsimu.add(l5);
        boxsimu.add(l6);

        this.frameSimu.getContentPane().add(boxsimu);
        this.frameSimu.setVisible(true);
    }

    public void simular(){
        if(tipoSimulacao == 0){
            simularFifo();
        }else if(tipoSimulacao == 1){
            simularOtm();
        }else if(tipoSimulacao == 2){
            simularLru();
        }else if(tipoSimulacao == 3){
            simularSc();
        }else if(tipoSimulacao == 4){
            simularWsm();
        }
        this.frameSimu.setVisible(false);
        this.pageFLabel.setText(Integer.toString(this.getPageFaults()));
        this.setHitRate();
        this.hitRLabel.setText(Float.toString(this.getHitRate()));

    }

    public void simularFifo(){
        int[] paginas = getSequenciaPaginas();
        int n = getNumeroQuadros();
        int[] memoria = new int[n];

        int[] historico = new int[n];

        for(int i = 0; i<n; i++){
            memoria[i] = -1;
            historico[i] = -1;
        }

        for(int i = 0; i<paginas.length; i++){  //para cada valor da lista de paginas: 
            Boolean presencia = false;   
            for(int j = 0; j<n; j++){               //o endereco da pagina ja esta presente na memoria ?
                if(paginas[i] == memoria[j]){       
                    presencia = true; 
                }
            }

            if(presencia){                          //se sim:

                int j=0;

                while(historico[j]!=paginas[i]){    //achar a posicao do endereco no historico
                    j++;

                }
                int enderecoHistorico = j;
                int temp = historico[enderecoHistorico];

                for(j=enderecoHistorico-1; j>=0; j--){    //atualizar o historico
                    historico[j+1]=historico[j];
                }
                historico[0] = temp;
            }else{                                  //se não:
                addPageFault();
                int ultimo = historico[n-1]; //verificar o ultimo endereco de pagina a ter sido chamado

                int j=0;

                while(memoria[j]!= ultimo){         //achar a posicao desse ultimo endereco na memoria
                    j++;
                }
                int enderecoMemoria = j;

                memoria[enderecoMemoria]=paginas[i];//atualizar valor na memoria

                for(j=n-2;j>=0;j--){
                    historico[j+1]=historico[j];    //atualizar o historico
                }
                historico[0] = paginas[i];
            }
        }
        this.setHitRate();
    }


    public void simularOtm(){

    }


    public void simularLru(){

    }


    public void simularSc(){

    }


    public void simularWsm(){
        long tempoInicial = System.currentTimeMillis();
        int[] paginas = getSequenciaPaginas();
        int n = getNumeroQuadros();
        int[] memoria = new int[n];
        long[] tempos = new long[n];
        long tmax;
        int jmax;
        long tau = 50;                              //tau = 50ms tempo do working set
        for(int i=0; i<n; i++){                     //initialização
            memoria[i] = -1;
            tempos[i] = System.currentTimeMillis()-tempoInicial;
        }
        for(int i = 0; i<paginas.length; i++){ 
            tmax = 0;
            jmax = 0; 
            Boolean hit = false;
            for(int j = 0; j<n; j++){               //o endereco da pagina ja esta presente na memoria ?
                /**
                System.out.println("");
                System.out.print("i : ");
                System.out.print(i);
                System.out.print("; j : ");
                System.out.println(j);
                System.out.print("Temps : ");
                System.out.println(System.currentTimeMillis()-tempoInicial);
                System.out.print("tempos : ");
                System.out.println(Arrays.toString(tempos));
                System.out.print("memoria : ");
                System.out.println(Arrays.toString(memoria));
                System.out.print("pagina : ");
                System.out.println(paginas[i]);
                */
                if(paginas[i] == memoria[j]){       // Se sim :      
                    tempos[j] = System.currentTimeMillis()-tempoInicial;                  //reiniciar tempo no endereco
                    hit = true;
                }else if(System.currentTimeMillis()-tempoInicial-tempos[j] > tau){   //Se não e o tempo de alocacao > tau :                       
                    tmax = tau;                                               //priorizar a alocacao nesse endereco em caso de falha
                    jmax = j;
                }else{                                                  //se não e o tempo de alocacao <= tau :                                                      
                    if(System.currentTimeMillis()-tempoInicial-tempos[j] > tmax){   //atualizar tempo maximo
                        tmax = System.currentTimeMillis()-tempoInicial-tempos[j];
                        jmax = j;
                    }
                }
                /**
                System.out.print("tmax : ");
                System.out.println(tmax);
                System.out.print("jmin : ");
                System.out.println(jmax);
                */
                if(j == n-1){ 
                    if(!hit){                                             //se parcorer toda a memoria sem achar onde alocar a pagina:
                        addPageFault();                                     // adicionar um Page Fault
                        memoria[jmax] = paginas[i];                             //alocar pagina nesse endereco de memoria
                        tempos[jmax] = System.currentTimeMillis()-tempoInicial;               //reiniciar tempo do endereco
                    }
                }
            }
        }
        this.setHitRate();
    }

}   