package jPanel;

import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        JFrame fen = new JFrame("Projeto Final Sistemas Operacionais 26/10/2021");
        fen.setSize(300,300);

        JPanel pan = new JPanel();
        //fen.setContentPane(pan);

        JPanel l1 = new JPanel();
        JPanel l2 = new JPanel();
        JPanel l3 = new JPanel();
        JPanel l4 = new JPanel();
        JPanel l5 = new JPanel();
        JPanel l6 = new JPanel();
        JPanel l7 = new JPanel();
        JPanel l8 = new JPanel();
        JPanel l9 = new JPanel();
        JPanel l10 = new JPanel();

        l1.setLayout(new BoxLayout(l1, BoxLayout.LINE_AXIS));
        l2.setLayout(new BoxLayout(l2, BoxLayout.LINE_AXIS));
        l3.setLayout(new BoxLayout(l3, BoxLayout.LINE_AXIS));
        l4.setLayout(new BoxLayout(l4, BoxLayout.LINE_AXIS));
        l5.setLayout(new BoxLayout(l5, BoxLayout.LINE_AXIS));
        l6.setLayout(new BoxLayout(l6, BoxLayout.LINE_AXIS));
        l7.setLayout(new BoxLayout(l7, BoxLayout.LINE_AXIS));
        l8.setLayout(new BoxLayout(l8, BoxLayout.LINE_AXIS));
        l9.setLayout(new BoxLayout(l9, BoxLayout.LINE_AXIS));
        l10.setLayout(new BoxLayout(l10, BoxLayout.LINE_AXIS));

        l1.add(new JLabel("Professor: Marcelo Zanchetta do Nascimento"));
        l2.add(new JLabel("Igor Augusto - Matricula"));
        l3.add(new JLabel("João Victor - Matricula"));
        l4.add(new JLabel("Lucas - Matricula"));
        l5.add(new JLabel("Nicolas Fischmann - 12011EMT032"));
        JButton fifo = new JButton("FIFO - First In First Out");
        //fen.getContentPane().add(fifo);
        l6.add(fifo);
        JButton otm = new JButton("OTM - Optimal");
        //fen.getContentPane().add(otm);
        l7.add(otm);
        JButton lru = new JButton("LRU - Least Recently Used");
        //fen.getContentPane().add(lru);
        l8.add(lru);
        JButton sc = new JButton("SC - Second-Chance");
        //fen.getContentPane().add(sc);
        l9.add(sc);
        JButton wsm = new JButton("WSM - Working-Set Model");
        //fen.getContentPane().add(wsm);
        l10.add(wsm);

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        box.add(l1);
        box.add(l2);
        box.add(l3);
        box.add(l4);
        box.add(l5);
        box.add(l6);
        box.add(l7);
        box.add(l8);
        box.add(l9);
        box.add(l10);

        fen.getContentPane().add(box);
        fen.setVisible(true);
    }
}
