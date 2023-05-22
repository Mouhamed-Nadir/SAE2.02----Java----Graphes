package main.java.graphe.implems;

import main.java.graphe.core.IGraphe;
import main.java.graphe.core.Arc;

import java.util.*;

public class GrapheLArcs implements IGraphe {

    private static final int NO_VALUATION = -1;
    private static final int VALUEARCCOMPARE = 0;
    private static final int VALUEARCFACTICE = 0;
    private static final String STR_EMPTY = "";


    private List<Arc> arcs;

    public GrapheLArcs() {
        arcs = new ArrayList<Arc>();
    }

    public GrapheLArcs(String graphe) {
        this();
        peupler(graphe);
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud)) {
            Arc a = new Arc(noeud);
            arcs.add(a);
        }
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (valeur < 0 || contientArc(source, destination)) {
            throw new IllegalArgumentException();
        }

        ajouterSommet(destination);
        Arc arcAjouter = new Arc(source, destination, valeur);
        for (int i = 0; i < arcs.size(); ++i) {
            if (arcs.get(i).getSource().equals(source) && getSucc(arcs.get(i).getSource()).isEmpty()) {
                arcs.set(i, arcAjouter);
                return;
            }
        }
        arcs.add(arcAjouter);

    }

    @Override
    public void oterSommet(String noeud) {
        List<String> sommets = new ArrayList<String>(getSommets());
        if (contientSommet(noeud)) {
            arcs.removeIf(a -> a.getSource().equals(noeud) || a.getDestination().equals(noeud));
        }
        for (String s : sommets) {
            if (getSucc(s).isEmpty() && !s.equals(noeud))
                ajouterSommet(s);
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException();

        List<String> sommets = new ArrayList<String>(getSommets());

        Arc arcCompare = new Arc(source, destination, VALUEARCCOMPARE);
        arcs.removeIf(a -> a.equals(arcCompare));

        for (String s : sommets) {
            Arc arcCompareFactice = new Arc(s, STR_EMPTY, VALUEARCCOMPARE);
            if (getSucc(s).isEmpty() && !arcs.contains(arcCompareFactice))
                arcs.add(new Arc(s, STR_EMPTY, VALUEARCFACTICE));
        }
    }

    @Override
    public List<String> getSommets() {
        HashSet<String> sommets = new HashSet<String>();
        for (Arc a : arcs)
            sommets.add(a.getSource());
        List<String> sommets1 = new ArrayList<>(sommets);
        return sommets1;
    }

    @Override
    public List<String> getSucc(String sommet) {
        HashSet<String> succ = new HashSet<>();
        for (String somPotAdj : getSommets()) {
            if (contientArc(sommet, somPotAdj))
                succ.add(somPotAdj);
        }
        List<String> successeurs = new ArrayList<String>(succ);
        return successeurs;
    }

    @Override
    public int getValuation(String src, String dest) {
        for (Arc a : arcs) {
            if (a.equals(new Arc(src, dest, VALUEARCCOMPARE)))
                return a.getValuation();
        }
        return NO_VALUATION;
    }

    @Override
    public boolean contientSommet(String sommet) {
        return getSommets().contains(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        return arcs.contains(new Arc(src, dest, VALUEARCCOMPARE));
    }

    public String toString() {
        return toAString();
    }
}