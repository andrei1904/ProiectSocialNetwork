package socialnetwork.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GrafPrietenii {
    private int nrVerticles;
    private int nrEdges;
    private static Map<Integer, HashSet<Integer>> adj;
    private static Map<Integer, Integer> visited;

    public GrafPrietenii(int nrVerticles, int nrEdges) {
        this.nrVerticles = nrVerticles;
        this.nrEdges = nrEdges;
        adj = new HashMap<>();
        visited = new HashMap<>();
    }

    private void addEdge(int s, int d) {
        adj.putIfAbsent(s, new HashSet<Integer>());
        adj.putIfAbsent(d, new HashSet<Integer>());
        adj.get(s).add(d);
        adj.get(d).add(s);

        visited.put(s, 0);
        visited.put(d, 0);
    }

    private void initializeGraf(Iterable<Utilizator> utilizatori) {
        for (Utilizator utilizator : utilizatori) {
            int id = Math.toIntExact(utilizator.getId());
            adj.putIfAbsent(id, new HashSet<Integer>());
            visited.put(id, 0);
        }
    }

    public void populateGraf(Iterable<Prietenie> prietenii, Iterable<Utilizator> utilizatori) {
        initializeGraf(utilizatori);

        for (Prietenie prietenie : prietenii) {
            addEdge(prietenie.getIdPrieten1(), prietenie.getIdPrieten2());
        }
    }

    private void findDfs(int x) {
        visited.put(x, 1);

        for (Integer i : adj.get(x)) {
            if (visited.get(i) == 0) {
                findDfs(i);
            }
        }
    }

    private void findDfs(int x, HashSet<Integer> p) {
        visited.put(x, 1);
        p.add(x);

        for (Integer i : adj.get(x)) {
            if (visited.get(i) == 0) {
                findDfs(i, p);
            }
        }
    }

    public int componenteConexe() {
        int nr = 0;
        for (Integer i : visited.keySet()) {
            if (visited.get(i) == 0) {
                findDfs(i);
                nr++;
            }
        }
        return nr;
    }

    public HashSet<Integer> ceaMaiLungaComunitate() {
        HashSet<Integer> comunitateMax = new HashSet<Integer>();

        for (Integer i : visited.keySet()) {
            HashSet<Integer> comunitate = new HashSet<Integer>();

            if (visited.get(i) == 0){
                findDfs(i, comunitate);

                if (comunitate.size() > comunitateMax.size()) {
                    comunitateMax = comunitate;
                }
            }
        }
        return comunitateMax;
    }

}
