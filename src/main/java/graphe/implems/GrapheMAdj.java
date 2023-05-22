package main.java.graphe.implems;

import main.java.graphe.core.IGraphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheMAdj implements IGraphe {
    private static final int NOARCVALUE = -1;
    private static final int PAS_EXTENSION = 1;
    private int[][] matrice;
    private Map<String, Integer> indices;

    public GrapheMAdj() {
        matrice = new int[0][0];
        indices = new HashMap<String, Integer>();
    }

    public GrapheMAdj(String graphe) {
        this();
        peupler(graphe);
    }
    @Override
    public void ajouterSommet(String noeud) {
        if (contientSommet(noeud))
            return;
        int dernierInd = matrice.length;
        indices.put(noeud, dernierInd);
        agrandir(dernierInd);
    }

    private void agrandir(int dernierInd) {
        int[][] matrice2 = new int[dernierInd +PAS_EXTENSION][dernierInd + PAS_EXTENSION];
        for (int i = 0; i < matrice2.length; ++i) {
            for (int j = 0; j < matrice2.length; ++j) {
                if (i >= dernierInd || j >= dernierInd)
                    matrice2[i][j] = NOARCVALUE;
                else
                    matrice2[i][j] = matrice[i][j];
            }
        }
        matrice = matrice2;
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (valeur < 0 || contientArc(source, destination))
            throw new IllegalArgumentException();
        ajouterSommet(source);
        ajouterSommet(destination);

        int indSrc = indices.get(source);
        int indDest = indices.get(destination);
        matrice[indSrc][indDest] = valeur;
    }

    @Override
    public void oterSommet(String noeud) {
        if(contientSommet(noeud)){
            int indSommet = indices.get(noeud);
            for (int i = 0; i < matrice.length; ++i) {
                matrice[indSommet][i] = NOARCVALUE;
                indices.remove(noeud);
            }
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException();
        int indSrc = indices.get(source);
        int indDest = indices.get(destination);
        matrice[indSrc][indDest] = NOARCVALUE;
    }

    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>(indices.keySet());
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successeurs = new ArrayList<>();
        int indSommet = indices.get(sommet);
        for (String som : getSommets())
            if (matrice[indSommet][indices.get(som)] > NOARCVALUE )
                successeurs.add(som);
        return successeurs;
    }

    @Override
    public int getValuation(String src, String dest) {
        if (!contientArc(src, dest))
            return NOARCVALUE;
        int indSrc = indices.get(src);
        int indDest = indices.get(dest);
        return matrice[indSrc][indDest];

    }

    @Override
    public boolean contientSommet(String sommet) {
        return indices.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (!contientSommet(src) || !contientSommet(dest))
            return false;
        int indSrc = indices.get(src);
        int indDest = indices.get(dest);
        return matrice[indSrc][indDest] != NOARCVALUE;

    }

    public String toString() {
        return toAString();
    }
}
