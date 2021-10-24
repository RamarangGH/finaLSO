package jPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Simulacao {
    SequenciaPaginas paginas;
    int tipoSimulacao; //0: fifo, 1: otm, 2: lru, 3: sc, 4: wsm
    float hitRate; //em %
    int pageFaults = 0;
    int numeroQuadrosRam; // varia de 1 a 7
    JFrame frameSimu;

    public Simulacao(SequenciaPaginas paginas){
        this.pageFaults = 0;
        this.tipoSimulacao = 0;
        this.hitRate = 0;
        this.numeroQuadrosRam = 7;
        this.paginas = paginas;
        this.frameSimu = new JFrame("Simulacao");
        
    }

    public float getHitRate(){ return hitRate; }
    public int getPageFaults(){ return pageFaults; }
    public void setNumeroQuadros(int numero){ this.numeroQuadrosRam = numero; }
    public void addPageFault(){this.pageFaults += 1;}
    public void setHitRate(){this.hitRate = (1-this.pageFaults/paginas.getSequencia().length)*100; }
    public void setTipoSimulacao(int tipo){ this.tipoSimulacao = tipo; }
    public void setSequencia(int[] paginas){ this.paginas.setSequencia(paginas); }



}