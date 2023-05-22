package main.java.graphe.implems;

import main.java.graphe.core.IGraphe;
import main.java.graphe.core.Arc;

import java.util.*;

public class GrapheLAdj implements IGraphe {
    private Map<String, List<Arc>> ladj;
    private static final int NO_VALUATION = -1;
    private static final int VALUEARCCOMPARE = 0;


    public GrapheLAdj() {
        ladj = new HashMap<>();
    }

    public GrapheLAdj(String graphe) {
        this();
        peupler(graphe);
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud)) {
            ladj.put(noeud, new ArrayList<Arc>());
        }
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (valeur < 0 || contientArc(source, destination)) {
            throw new IllegalArgumentException();
        }
        ajouterSommet(source);

        ajouterSommet(destination);
        Arc arcAjouter = new Arc(source, destination, valeur);
        ladj.get(source).add(arcAjouter);
    }

    @Override
    public void oterSommet(String noeud) {
        if (!contientSommet(noeud))
            return;
        ladj.remove(noeud);
        for (String sommet : getSommets()) {
            Arc arcCompare = new Arc(sommet, noeud, VALUEARCCOMPARE);
            ladj.get(sommet).removeIf(a -> a.equals(arcCompare));
        }

    }

    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException();
        Arc arcCompare = new Arc(source, destination, VALUEARCCOMPARE);
        ladj.get(source).removeIf(a -> a.equals(arcCompare));
    }

    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<String>(ladj.keySet());
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successeurs = new ArrayList<String>();
        if (contientSommet(sommet)) {
            for (Arc a : ladj.get(sommet)) {
                successeurs.add(a.getDestination());
            }
        }
        return successeurs;
    }

    @Override
    public int getValuation(String src, String dest) {
        if (!contientArc(src, dest))
            return NO_VALUATION;
        int valuationArc = 0;
        for (Arc a : ladj.get(src)) {
            if (a.getDestination().equals(dest)) {
                valuationArc = a.getValuation();
                break;
            }
        }
        return valuationArc;

    }

    @Override
    public boolean contientSommet(String sommet) {
        return ladj.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (contientSommet(src) && contientSommet(dest)) {
            Arc arcCompare = new Arc(src, dest, VALUEARCCOMPARE);
            return ladj.get(src).contains(arcCompare);
        }
        return false;
    }

    public String toString() {
        return toAString();
    }
}