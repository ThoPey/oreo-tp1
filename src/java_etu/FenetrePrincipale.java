// Thomas PEYROT 11608040

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_etu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
//import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;


public class FenetrePrincipale extends JFrame {

    Graph grapheCourant;
    PanneauVisu panoVisu;
    PanneauGraphe panoGraphe;
    PanneauActions panoActions;
    PanneauAlgorithmes panoAlgo;
    int nbcouleurs=10;

    public FenetrePrincipale() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panoVisu = new PanneauVisu();
        panoGraphe = new PanneauGraphe();
        panoActions = new PanneauActions();
        panoAlgo = new PanneauAlgorithmes();

        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints cont = new GridBagConstraints();
        cont.fill = GridBagConstraints.BOTH;
        cont.weighty = 1.0;
        cont.gridx = 0;
        cont.gridy = 0;
        cont.gridheight = 3;
        this.getContentPane().add(panoVisu, cont);
        cont.gridx = 1;
        cont.gridheight = 1;
        this.getContentPane().add(panoGraphe, cont);
        cont.gridy = 1;
        this.getContentPane().add(panoActions, cont);
        cont.gridy = 2;
        this.getContentPane().add(panoAlgo, cont);

        this.pack();

    }

    public class PanneauVisu extends JPanel {

        final String styleSheet = "node {"
                + "size-mode: dyn-size;"
                + " fill-color: red;"
                + " size: 10px;"
                + " stroke-mode: plain;"
                + " stroke-color: black;"
                + " stroke-width: 1px;"
                + "}"
                + "node.important {"
                + " fill-color: red;"
                + " size: 30px;"
                + "}"
                + "edge {"
                + "text-alignment: along;"
                + "}"
                + "edge.notintree {"
                + "size:1px;"
                + "fill-color:gray;"
                + "} "
                + "edge.intree {size:3px;fill-color:blue;}";

        public Viewer graphViewer = null;
        public View graphView = null;

        public PanneauVisu() {
            this.setLayout(new BorderLayout());
            this.setPreferredSize(new Dimension(800, 600));
        }

        public void update() {
            if (grapheCourant != null) {
                try {
                    this.remove(graphViewer.getView(Viewer.DEFAULT_VIEW_ID));
                } catch (Exception ex) {
                }
                grapheCourant.addAttribute("ui.stylesheet", styleSheet);
                graphViewer = new Viewer(grapheCourant, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
                graphViewer.enableAutoLayout();
                graphView = graphViewer.addDefaultView(false);
                this.add(graphView);
                graphView.revalidate();
            }
        }
    }

    public class PanneauGraphe extends JPanel {

        public JComboBox<TypeGraphe> comboChoixGraphe = new JComboBox(new DefaultComboBoxModel());
        public JButton btGraphe = new JButton("Générer");
        public int args[];

        public PanneauGraphe() {
            this.setBorder(BorderFactory.createTitledBorder("Choix du graphe"));
            //remplissage du combo
            for (TypeGraphe x : TypeGraphe.values()) {
                ((DefaultComboBoxModel) comboChoixGraphe.getModel()).addElement(x);
            }
            //ajout des éléments graphiques
            this.setLayout(new GridBagLayout());
            GridBagConstraints cont = new GridBagConstraints();
            cont.gridx = cont.gridy = 0;
            this.add(comboChoixGraphe, cont);
            cont.gridx = 1;
            this.add(btGraphe, cont);
            //action bouton
            btGraphe.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    TypeGraphe type = (TypeGraphe) ((DefaultComboBoxModel) comboChoixGraphe.getModel()).getSelectedItem();
                    if (new BoiteParam(PanneauGraphe.this, type.toString(), type.getParam()).isActivee()) {
                        grapheCourant = GrapheFabrique.generer(type, args);
                        panoVisu.update();
                    }
                }
            });
        }

        //pour mettre à jour les données saisies dans la boite de dialogue
        public void maj(int args[]) {
            this.args = new int[args.length];
            for (int i = 0; i < args.length; i++) {
                this.args[i] = args[i];
            }
        }

    }

    public class PanneauActions extends JPanel {
        public JLabel nbcouleurs_label= new JLabel("#couleurs");
        public JTextField nbcouleurs_field = new JTextField("10");
        
        public PanneauActions(){
            this.setBorder(BorderFactory.createTitledBorder("Choix des parametres"));
            //remplissage du combo        
            //ajout des éléments graphiques
            this.setLayout(new GridBagLayout());
            GridBagConstraints cont = new GridBagConstraints();
            cont.gridx = cont.gridy = 0;
            this.add(nbcouleurs_label, cont);
            cont.gridy=1;
            nbcouleurs_field.setColumns(8);
            this.add(nbcouleurs_field, cont);
        }              
        
        public void getNbCouleurs(){
            nbcouleurs = Integer.parseInt(nbcouleurs_field.getText());
        }
                
    }

    public class PanneauAlgorithmes extends JPanel {
        
        public JButton welsh_button = new JButton("Welsh Powell");
        public JLabel welsh_label= new JLabel("     ");
        
        public JButton alea_button = new JButton("Aleatoire");
        public JLabel alea_label = new JLabel("       ");
        
        public JButton descent_button = new JButton("Descente");
        public JLabel descent_label= new JLabel("     ");
        
        public JButton tabu_button = new JButton("Tabu");
        public JLabel tabu_label= new JLabel("     ");

        
        public PanneauAlgorithmes(){
            this.setBorder(BorderFactory.createTitledBorder("Choix de l'algo"));
            //remplissage du combo        
            //ajout des éléments graphiques
            this.setLayout(new GridBagLayout());
            GridBagConstraints cont = new GridBagConstraints();
            cont.gridx = cont.gridy = 0;
            this.add(alea_button, cont);
            cont.gridy=1;
            this.add(alea_label, cont);
            cont.gridx = 1; cont.gridy = 0;
            this.add(descent_button, cont);
            cont.gridy=1;
            this.add(descent_label, cont);
            cont.gridx = 2; cont.gridy = 0;
            this.add(tabu_button, cont);
            cont.gridy=1;
            this.add(tabu_label, cont);

            alea_button.addActionListener(new ActionListener() {             
                @Override
                public void actionPerformed(ActionEvent ae) {
                    panoActions.getNbCouleurs();
                    alea();
                }   
                private void alea() {
                    int nbsommets=grapheCourant.getNodeCount();             
                    for (int i=0;i<nbsommets;i++){
                        if (grapheCourant.getNode(i).hasAttribute("color_alea"))
                            grapheCourant.getNode(i).setAttribute("color_alea",(int)(Math.random()*nbcouleurs));
                        else
                            grapheCourant.getNode(i).addAttribute("color_alea",(int)(Math.random()*nbcouleurs));
                    }
                    Color[] cols = new Color[nbcouleurs];
                    
                    for (int i = 0; i < nbcouleurs; i++) {
                        cols[i] = Color.getHSBColor((float) (Math.random()), 0.8f, 0.9f);
                    }
                    for (Node n : grapheCourant) {
                        int col = n.getAttribute("color_alea");
                        n.addAttribute("ui.style", "fill-color:rgba(" + cols[col].getRed() + "," + cols[col].getGreen() + "," + cols[col].getBlue() + ",200);");
                    }
                    alea_label.setText(""+getNbConflits("color_alea"));
                }
            });
            
            descent_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    panoActions.getNbCouleurs();
                    descent();
                }

                private void descent() {   
                    //la couleur des noeuds sera stockée dans l'attribut color_descent
                    //la boucle ci-dessous initialise les couleurs aléatoirement
                    int nbsommets=grapheCourant.getNodeCount();
                    for (int i=0;i<nbsommets;i++){
                        if (grapheCourant.getNode(i).hasAttribute("color_descent"))
                            grapheCourant.getNode(i).addAttribute("color_descent",(int)(Math.random()*nbcouleurs));
                        else
                            grapheCourant.getNode(i).setAttribute("color_descent",(int)(Math.random()*nbcouleurs));
                    }
                    //mapping des attributs couleurs vers couleurs, utilisé uniquement pour affichage
                    Color[] cols = new Color[nbcouleurs];
                    for (int i = 0; i < nbcouleurs; i++) {
                        cols[i] = Color.getHSBColor((float) (Math.random()), 0.8f, 0.9f);
                    }
                    
                    // Exercice 2
                    String resultAttr = "color_descent";

                    int resultNbConflicts = getNbConflits(resultAttr);
                    while (resultNbConflicts != 0) {
                        int modifNode = -1;
                        int modifColor = -1;
                        int modifNbConflicts = resultNbConflicts;

                        // Get best modification
                        for (int i = 0; i < nbsommets; i++) {
                            Node currentNode = grapheCourant.getNode(i);
                            int resultNodeColor = currentNode.getAttribute(resultAttr);
                            for (int j = 0; j < nbcouleurs; j++) {
                                if (j == resultNodeColor) continue;

                                currentNode.setAttribute(resultAttr, j);
                                int currentNbConflicts = getNbConflits(resultAttr);
                                if (currentNbConflicts < modifNbConflicts) {
                                    modifNode = i;
                                    modifColor = j;
                                    modifNbConflicts = currentNbConflicts;
                                }
                                currentNode.setAttribute(resultAttr, resultNodeColor);
                            }
                        }

                        // Apply best modification
                        if (modifNode == -1) break;
                        grapheCourant.getNode(modifNode).setAttribute(resultAttr, modifColor);
                        resultNbConflicts = modifNbConflicts;
                    }
                    // Fin Exercice 2

                    //on colorie les noeuds à la fin de l'algo
                    for (Node n : grapheCourant) {
                        int col = (int) n.getNumber("color_descent");
                        n.addAttribute("ui.style", "fill-color:rgba(" + cols[col].getRed() + "," + cols[col].getGreen() + "," + cols[col].getBlue() + ",200);");
                    }
                    //on affiche le nombre de conflits à la fin de la descente
                    descent_label.setText(""+getNbConflits("color_descent"));
                    
                }
                               
            
            });
            
            
            tabu_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    panoActions.getNbCouleurs();
                    tabu_opt(300);
                }

                private void tabu(int nb_iterations_max) {
                    int nbsommets=grapheCourant.getNodeCount();
                    for (int i=0;i<nbsommets;i++){
                        if (grapheCourant.getNode(i).hasAttribute("color_tabu"))
                            grapheCourant.getNode(i).setAttribute("color_tabu",(int)(Math.random()*nbcouleurs));
                        else 
                            grapheCourant.getNode(i).addAttribute("color_tabu",(int)(Math.random()*nbcouleurs));
                    }
                    
                     //mapping atributs couleurs vers couleurs
                    Color[] cols = new Color[nbcouleurs];
                    for (int i = 0; i < nbcouleurs; i++) {
                        cols[i] = Color.getHSBColor((float) (Math.random()), 0.8f, 0.9f);
                    }

                    // Exercice 3
                    String resultAttr = "color_tabu";
                    String movingAttr = "moving_tabu";
                    final int TABU_LIST_SIZE = 10;
                    LinkedList<SimpleImmutableEntry<Integer, Integer>> tabuList = new LinkedList<SimpleImmutableEntry<Integer, Integer>>();
                    int currentNbIterations = 0;

                    for (int i = 0; i < nbsommets; i++) {
                        Node g = grapheCourant.getNode(i);
                        g.setAttribute(movingAttr, (int) g.getAttribute(resultAttr));
                    }

                    int resultNbConflicts = getNbConflits(resultAttr);
                    int movingNbConflicts = resultNbConflicts;
                    while (resultNbConflicts != 0 && currentNbIterations < nb_iterations_max) {
                        int modifNode = -1;
                        int modifColor = -1;
                        int modifNbConflicts = -1;

                        // Get best modification
                        for (int i = 0; i < nbsommets; i++) {
                            Node currentNode = grapheCourant.getNode(i);
                            int movingNodeColor = currentNode.getAttribute(movingAttr);
                            ArrayList<Integer> tabuColors = new ArrayList<Integer>();
                            for (int k = 0; k < tabuList.size(); k++) {
                                if (tabuList.get(k).getKey() == i) {
                                    tabuColors.add(tabuList.get(k).getValue());
                                }
                            }

                            for (int j = 0; j < nbcouleurs; j++) {
                                if (j == movingNodeColor) continue;

                                currentNode.setAttribute(movingAttr, j);
                                int currentNbConflicts = getNbConflits(movingAttr);
                                if (
                                    modifNbConflicts == -1
                                    || currentNbConflicts < resultNbConflicts
                                    || (currentNbConflicts < modifNbConflicts && tabuColors.contains(modifColor))
                                    || (currentNbConflicts < modifNbConflicts && !tabuColors.contains(j))
                                    || (!tabuColors.contains(j) && tabuColors.contains(modifColor))
                                ) {
                                    modifNode = i;
                                    modifColor = j;
                                    modifNbConflicts = currentNbConflicts;
                                }
                                currentNode.setAttribute(movingAttr, movingNodeColor);
                            }
                        }

                        if (modifNode == -1) break;

                        // Set Tabu List
                        int lastColor = grapheCourant.getNode(modifNode).getAttribute(movingAttr);
                        tabuList.addLast(new SimpleImmutableEntry<Integer, Integer>(modifNode, lastColor));
                        if (tabuList.size() > TABU_LIST_SIZE) tabuList.removeFirst();

                        // Apply best modification
                        grapheCourant.getNode(modifNode).setAttribute(movingAttr, modifColor);
                        movingNbConflicts = modifNbConflicts;
                        if (movingNbConflicts < resultNbConflicts) {
                            for (int i = 0; i < nbsommets; i++) {
                                Node g = grapheCourant.getNode(i);
                                g.setAttribute(resultAttr, (int) g.getAttribute(movingAttr));
                            }
                            resultNbConflicts = movingNbConflicts;
                            currentNbIterations = 0;
                        } else {
                            ++currentNbIterations;
                        }
                    }
                    // Fin Exercice 3

                     //on colorie les noeuds
                    for (Node n : grapheCourant) {
                        int col = n.getAttribute("color_tabu");
                        if (n.hasAttribute("ui.style"))
                            n.setAttribute("ui.style", "fill-color:rgba(" + cols[col].getRed() + "," + cols[col].getGreen() + "," + cols[col].getBlue() + ",200);");
                        else 
                            n.addAttribute("ui.style", "fill-color:rgba(" + cols[col].getRed() + "," + cols[col].getGreen() + "," + cols[col].getBlue() + ",200);");
                    }
                    tabu_label.setText(""+getNbConflits("color_tabu"));
                }
            
                // Exercice 4
                private void tabu_opt(int nb_iterations_max) {
                    int nbsommets=grapheCourant.getNodeCount();
                    for (int i=0;i<nbsommets;i++){
                        if (grapheCourant.getNode(i).hasAttribute("color_tabu_opt"))
                            grapheCourant.getNode(i).setAttribute("color_tabu_opt",(int)(Math.random()*nbcouleurs));
                        else
                            grapheCourant.getNode(i).addAttribute("color_tabu_opt",(int)(Math.random()*nbcouleurs));
                    }

                     //mapping atributs couleurs vers couleurs
                    Color[] cols = new Color[nbcouleurs];
                    for (int i = 0; i < nbcouleurs; i++) {
                        cols[i] = Color.getHSBColor((float) (Math.random()), 0.8f, 0.9f);
                    }

                    // --- Vrai Exercice 4 -- //
                    String resultAttr = "color_tabu_opt";
                    String movingAttr = "moving_tabu_opt";
                    int tabuListSize = 10;
                    LinkedList<SimpleImmutableEntry<Integer, Integer>> tabuList = new LinkedList<SimpleImmutableEntry<Integer, Integer>>();
                    int currentNbIterations = 0;

                    for (int i = 0; i < nbsommets; i++) {
                        Node g = grapheCourant.getNode(i);
                        g.setAttribute(movingAttr, (int) g.getAttribute(resultAttr));
                    }

                    // Matrix creation
                    int[][] matrix = new int[nbsommets][nbcouleurs];
                    for (int v = 0; v < nbsommets; v++) {
                        for (int c = 0; c < nbcouleurs; c++) {
                            matrix[v][c] = 0;
                        }
                    }
                    for (int i = 0; i < nbsommets; i++) {
                        Node n = grapheCourant.getNode(i);
                        Iterator<Node> it = n.getNeighborNodeIterator();
                        while (it.hasNext()) {
                            int neighbourColor = it.next().getAttribute(resultAttr);
                            matrix[i][neighbourColor] += 1;
                        }
                    }

                    int resultNbConflicts = getNbConflits(resultAttr);
                    int movingNbConflicts = resultNbConflicts;
                    while (resultNbConflicts != 0 && currentNbIterations < nb_iterations_max) {
                        int modifNode = -1;
                        int modifColor = -1;
                        int modifNbConflicts = -1;

                        // Get best modification
                        for (int i = 0; i < nbsommets; i++) {
                            Node currentNode = grapheCourant.getNode(i);
                            int movingNodeColor = currentNode.getAttribute(movingAttr);
                            ArrayList<Integer> tabuColors = new ArrayList<Integer>();
                            for (int k = 0; k < tabuList.size(); k++) {
                                if (tabuList.get(k).getKey() == i) {
                                    tabuColors.add(tabuList.get(k).getValue());
                                }
                            }

                            for (int j = 0; j < nbcouleurs; j++) {
                                if (j == movingNodeColor) continue;

                                int currentNbConflicts = movingNbConflicts + matrix[i][j] - matrix[i][movingNodeColor];
                                if (
                                    modifNbConflicts == -1
                                    || currentNbConflicts < resultNbConflicts
                                    || (currentNbConflicts < modifNbConflicts && tabuColors.contains(modifColor))
                                    || (currentNbConflicts < modifNbConflicts && !tabuColors.contains(j))
                                    || (!tabuColors.contains(j) && tabuColors.contains(modifColor))
                                ) {
                                    modifNode = i;
                                    modifColor = j;
                                    modifNbConflicts = currentNbConflicts;
                                }
                            }
                        }

                        if (modifNode == -1) break;

                        // Set Tabu List
                        int lastColor = grapheCourant.getNode(modifNode).getAttribute(movingAttr);
                        tabuList.addLast(new SimpleImmutableEntry<Integer, Integer>(modifNode, lastColor));
                        tabuListSize = (int) 0.6 * resultNbConflicts + (int) (Math.random() * 9);
                        while (tabuList.size() > tabuListSize) {
                            tabuList.removeFirst();
                        }

                        // Apply best modification
                        Node n = grapheCourant.getNode(modifNode);
                        for (int i = 0; i < nbsommets; i++) {
                            if (n.hasEdgeBetween((Node) grapheCourant.getNode(i))) {
                                matrix[i][lastColor] -= 1;
                                matrix[i][modifColor] += 1;
                            }
                        }
                        n.setAttribute(movingAttr, modifColor);
                        movingNbConflicts = modifNbConflicts;

                        if (movingNbConflicts < resultNbConflicts) {
                            for (int i = 0; i < nbsommets; i++) {
                                Node g = grapheCourant.getNode(i);
                                g.setAttribute(resultAttr, (int) g.getAttribute(movingAttr));
                            }
                            resultNbConflicts = movingNbConflicts;
                            currentNbIterations = 0;
                        } else {
                            ++currentNbIterations;
                        }
                    }
                    // --- Fin Vrai Exercice 4 -- //

                     //on colorie les noeuds
                    for (Node n : grapheCourant) {
                        int col = n.getAttribute("color_tabu_opt");
                        if (n.hasAttribute("ui.style"))
                            n.setAttribute("ui.style", "fill-color:rgba(" + cols[col].getRed() + "," + cols[col].getGreen() + "," + cols[col].getBlue() + ",200);");
                        else
                            n.addAttribute("ui.style", "fill-color:rgba(" + cols[col].getRed() + "," + cols[col].getGreen() + "," + cols[col].getBlue() + ",200);");
                    }
                    tabu_label.setText(""+getNbConflits("color_tabu_opt"));
                }
                // Fin Exercice 4
            });
        }
    }
    
    //calcule le nombre de conflits pour un seul sommet n, en utilisant la couleur attrName
    public int getNbConflits(Node n, String attrName) {
        // Exercice 1
        int nbConflits = 0;
        int nodeColor = n.getAttribute(attrName);
        Iterator<Node> it = n.getNeighborNodeIterator();

        while (it.hasNext()) {
            int neighbourColor = it.next().getAttribute(attrName);
            if (nodeColor == neighbourColor)
                nbConflits += 1;
        }

        return nbConflits;
        // Fin Exercice 1
    }
        
    //calcule le nombre de conflits pour tout le graphe, en utilisant la couleur attrName
    public int getNbConflits(String attrName) {
        // Exercice 1
        int nbConflitsToutesNodes = 0;
        int nbsommets = grapheCourant.getNodeCount();             
        for (int i = 0; i < nbsommets; i++) {
            nbConflitsToutesNodes += getNbConflits(grapheCourant.getNode(i), attrName);
        }

        return nbConflitsToutesNodes / 2;
        // Fin Exercice 1
    }
    
    
    
}
