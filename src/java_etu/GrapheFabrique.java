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
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 *
 * @author Eric
 */
public class GrapheFabrique {
    
    static Graph generer(TypeGraphe type, int args[]){
        Graph g;
        switch (type){
            case GRILLE: return createGrille(args[0],false);
            case CHAINE: return createChaine(args[0]);
            case CYCLE: return createCycle(args[0]);
            case ANC: return createCompleteNTree(args[0], args[1]);
            case RANDOM: return createRandom(args[0], args[1]);
            case TORE: return createGrille(args[0],true);
        }
        
        return null;
    }
    
    static Graph createGrille(int taille, boolean torus){
        Graph graph = new SingleGraph("grille de taille"+taille);
        Generator gen = new GridGenerator(false,torus);

        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<taille; i++) {
            gen.nextEvents();
        }
        gen.end();
        return graph;
    }
    
    static Graph createRandom(int n, int avg){
        Graph graph = new SingleGraph("Random graph");
        Generator gen = new RandomGenerator(avg);
        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<n; i++)
            gen.nextEvents();
        gen.end();
        return graph;        
    }
    
    static Graph createChaine(int nb){
        Graph g = new SingleGraph("path "+nb);
        for(int i = 0; i<nb ; i++)
        {
            g.addNode(Integer.toString(i));
            if(i>0){
                g.addEdge(Integer.toString(i)+"-"+Integer.toString(i-1), Integer.toString(i), Integer.toString(i-1));
            }
        }
        return g;
    }
    
    static public Graph createCycle(int nb)
    {
        Graph g = new SingleGraph("cycle "+nb);
        if(nb<3){
            return g;
        }
        for(int i = 0; i<nb ; i++)
        {
            g.addNode(Integer.toString(i));
            if(i>0){
                g.addEdge(Integer.toString(i)+"-"+Integer.toString(i-1), Integer.toString(i), Integer.toString(i-1));
            }
            if(i>0 && i == nb-1){
                g.addEdge(Integer.toString(0)+"-"+Integer.toString(i), Integer.toString(0), Integer.toString(i));
            }
        }
        return g;
    }
    
     /**
     * Returns a complete N tree graph with the number of children and with the number of length given in the parameters
     * @return A complete N tree graph with the number of children and with the number of length given in the parameters
     */
    static public Graph createCompleteNTree(int nb,int height)
    {
        Graph g = new SingleGraph("completeNTree "+nb+" childs on "+height+"generations");
        if(height<0){
            return g;
        }
        g.addNode(Integer.toString(0));
        generateCompleteNTree(g,nb,height,0);
        return g;
    }
    
    /*
     * Generate recursively a complete N tree graph with the number of children and with the number of length given in the parameters
     */
    static private void generateCompleteNTree(Graph g, int nb , int height, int parent)
    {
        if(height == 0)
        {
            return;
        }
        for(int i=0;i<nb;i++)
        {
            g.addNode(Integer.toString(g.getNodeCount()));
            g.addEdge(Integer.toString(parent)+"-"+Integer.toString(g.getNodeCount()-1), Integer.toString(parent), Integer.toString(g.getNodeCount()-1));
            generateCompleteNTree(g,nb,height-1,g.getNodeCount()-1);
        }
    }
}

