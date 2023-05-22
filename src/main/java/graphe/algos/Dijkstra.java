package main.java.graphe.algos;

import main.java.graphe.core.IGrapheConst;
import java.util.HashSet;
import java.util.*;
public class Dijkstra {

    private static final int CHEMINSOURCE = 0;
    private static final int INFINI = -1;
    public static void dijkstra(IGrapheConst graphe, String source, Map<String, Integer> dist, Map<String, String> pred) {
        HashSet<String> sommetsVisites = new HashSet<>();
        HashSet<String> sommetsMarques = new HashSet<>();
        String noPredecesseur = "";

        dist.put(source, CHEMINSOURCE);
        Comparator<String> comp = (s1,s2) -> (Integer) (dist.get(s1) - dist.get(s2));
        PriorityQueue<String> queue = new PriorityQueue<>(comp);
        String sommet = source;
        queue.offer(sommet);

        while(!queue.isEmpty()){
            sommet = queue.poll();
            if (!sommetsMarques.contains(sommet)){
                for(String sommetAdj : graphe.getSucc(sommet)){
                    dist.putIfAbsent(sommetAdj,INFINI);
                    pred.putIfAbsent(sommetAdj,noPredecesseur);
                    int nouvelleDistance = dist.get(sommet) + graphe.getValuation(sommet,sommetAdj);
                    if(dist.get(sommetAdj) == INFINI){
                        dist.replace(sommetAdj,nouvelleDistance);
                        pred.replace(sommetAdj,sommet);}
                    else if  (nouvelleDistance < dist.get(sommetAdj) && sommetsVisites.contains(sommetAdj)) {
                        dist.replace(sommetAdj,nouvelleDistance);
                        pred.replace(sommetAdj,sommet);
                        queue.offer(sommetAdj);
                    }
                    if(!sommetsVisites.contains(sommetAdj)){
                        queue.offer(sommetAdj);
                        sommetsVisites.add(sommetAdj);
                    }
                }
                sommetsMarques.add(sommet);
            }
        }
    }
}



