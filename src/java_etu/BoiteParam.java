/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_etu;

/**
 *
 * @author Eric-PC
 */
import java.awt.Container;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Eric
 */
public class BoiteParam extends JDialog implements ActionListener{
   

    private ArrayList<JTextField> list=new ArrayList<JTextField>();
    
    private JButton valider=new JButton("VALIDER");
    private JButton annuler=new JButton("ANNULER");

    private FenetrePrincipale.PanneauGraphe parent;
    private int[] args;
    private boolean activee=false;

    public BoiteParam(FenetrePrincipale.PanneauGraphe ownerPanel, String title, String argc[]) {
        //fenêtre modale
        this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        //titre
        this.setTitle(title);
        //on garde le panneau parent (pour appeler sa méthode maj
        parent=ownerPanel;
        //on ajoute les champs utiles
        Container c=getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints contraintes = new GridBagConstraints();
        int i;
        contraintes.anchor=GridBagConstraints.WEST;
        contraintes.fill=GridBagConstraints.VERTICAL;
        for (i=0;i<argc.length;i++){    
            contraintes.insets=new Insets(7,7,7,7);
            contraintes.gridx=0;
            contraintes.gridy=i;
            c.add(new JLabel(argc[i]),contraintes);
            contraintes.gridx=1;
            JTextField tf=new JTextField(10);
            list.add(tf);
            c.add(tf,contraintes);
        }
        contraintes.fill=GridBagConstraints.BOTH;
        contraintes.gridx=0;
        contraintes.gridy=i;
        contraintes.gridwidth=1;
        c.add(valider,contraintes);
        contraintes.gridx=1;
        contraintes.gridy=i;
        contraintes.gridwidth=1;
        c.add(annuler,contraintes);
        annuler.addActionListener(this);
        valider.addActionListener(this);
        setLocation(parent.getWidth()-200,100);
        pack();
        setVisible(true);
    }

    public boolean isActivee() {
        return activee;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==annuler){
            setVisible(false);
        }
        else if (e.getSource()==valider){
            activee=true;
            args=new int[list.size()];
            //on récupère les valeurs saisies
            for (int i=0;i<list.size();i++)
                args[i]=Integer.parseInt(list.get(i).getText());           
            parent.maj(args);
            setVisible(false);
        }
    }
}

    

