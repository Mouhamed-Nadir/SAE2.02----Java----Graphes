package main.java.graphe.implems;

import main.java.graphe.core.IGraphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheHHAdj implements IGraphe {
    private static final int NO_VALUATION = -1;
    private Map<String, Map<String, Integer>> hhadj;

    public GrapheHHAdj() {
        hhadj = new HashMap<String, Map<String, Integer>>();
    }

    public GrapheHHAdj(String graphe) {
        this();
        peupler(graphe);
    }


    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud)) {
            hhadj.put(noeud, new HashMap<String, Integer>());
        }
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (valeur < 0 || contientArc(source, destination)) {
            throw new IllegalArgumentException();
        }

        ajouterSommet(source);
        ajouterSommet(destination);
        hhadj.get(source).put(destination, valeur);
    }

    @Override
    public void oterSommet(String noeud) {
        if (contientSommet(noeud)) {
            hhadj.remove(noeud);
            for (String sommet : getSommets())
                if (contientArc(sommet, noeud))
                    hhadj.get(sommet).remove(noeud);
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (contientArc(source, destination)) {
            hhadj.get(source).remove(destination);
        } else
            throw new IllegalArgumentException();
    }

    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<String>(hhadj.keySet());
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> sucesseurs = new ArrayList<String>(hhadj.get(sommet).keySet());
        return sucesseurs;
    }

    @Override
    public int getValuation(String src, String dest) {
        if (contientArc(src, dest))
            return hhadj.get(src).get(dest);
        return NO_VALUATION;
    }

    @Override
    public boolean contientSommet(String sommet) {
        return hhadj.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (contientSommet(src) && contientSommet(dest))
            return hhadj.get(src).containsKey(dest);

        return false;
    }

    public String toString() {
        return toAString();
    }

}
