package Test;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import common.DestCard;
import common.GameRoutes;
import common.Route;

public class TestGraph {
    public static void main(String[] argv) {
        System.out.println("Running test...");
        List<String> players = new ArrayList<>();
        players.add("player1");
        players.add("player2");
        players.add("player3");

        List<Route> allRoutes = GameRoutes.getAllRoutes();
        List<Double> longestPath = new ArrayList<>();

        allRoutes.get(0).setOwnedByPlayerID(players.get(0));
        allRoutes.get(1).setOwnedByPlayerID(players.get(0));
        allRoutes.get(2).setOwnedByPlayerID(players.get(0));
        allRoutes.get(4).setOwnedByPlayerID(players.get(0));
        allRoutes.get(10).setOwnedByPlayerID(players.get(0));
        allRoutes.get(18).setOwnedByPlayerID(players.get(0));
        allRoutes.get(99).setOwnedByPlayerID(players.get(0));

        List<DestCard> p1Dest = new ArrayList<>();
        p1Dest.add(new DestCard("Winnipeg", "Seattle", 15));
        p1Dest.add(new DestCard("Winnipeg", "Texas", 15));
        p1Dest.add(new DestCard("Calgary", "Portland", 10));
        p1Dest.add(new DestCard("Seattle", "Las Vegas", 3));


        allRoutes.get(3).setOwnedByPlayerID(players.get(1));
        allRoutes.get(5).setOwnedByPlayerID(players.get(1));
        allRoutes.get(6).setOwnedByPlayerID(players.get(1));
        allRoutes.get(8).setOwnedByPlayerID(players.get(1));


        for(String player : players) {
            // Get player's claimed routes
            Set<Route> routes = new HashSet<Route>();
            for (int count = 0; count < allRoutes.size(); count++){
                Route route = allRoutes.get(count);


                if (route.getOwnedByPlayerID() != null){
                    // this route is owned by a player, determine if it is the current player
                    if (route.getOwnedByPlayerID().equals(player)) {
                        routes.add(route);
                    }
                }
            }
            //allRoutes.stream().filter(r -> r.getOwnedByPlayerID().equals(player)).collect(Collectors.toSet());

            // Build player's graph
            SimpleWeightedGraph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
            for(Route route : routes) {
                g.addVertex(route.getStartCity());
                g.addVertex(route.getEndCity());
                DefaultWeightedEdge e = g.addEdge(route.getStartCity(), route.getEndCity());
                g.setEdgeWeight(e, route.getRouteLength());
            }

            // Add max path length to longestPath array
            double maxLength = 0;
            for(String start : g.vertexSet()) {
                double length = findMaxPathLength(g, start, new HashSet<DefaultWeightedEdge>());
                if (length > maxLength) {
                    maxLength = length;
                }
            }

            longestPath.add(maxLength);

            // Calculate valid dest cards
            ConnectivityInspector<String, DefaultWeightedEdge> ci = new ConnectivityInspector<>(g);
            int cardPoints = 0;
            for(DestCard card : p1Dest) {
                if(!(g.containsVertex(card.getStartCity()) && g.containsVertex(card.getEndCity()))) {
                    continue;
                }
                if(ci.pathExists(card.getStartCity(), card.getEndCity())) {
                    cardPoints += card.getPointValue();
                }
            }

            System.out.println("Player:\t" + player);
            System.out.println("Points:\t" + cardPoints);
            System.out.println("Max Length:\t" + maxLength);
        }
    }

    public static double findMaxPathLength(SimpleWeightedGraph<String, DefaultWeightedEdge> graph, String start, Set<DefaultWeightedEdge> used) {
        Set<DefaultWeightedEdge> unvisitedEdges = new HashSet<>(graph.edgesOf(start));
        unvisitedEdges.removeAll(used);

        if (unvisitedEdges.isEmpty()) {
            return 0;
        }

        double maxLen = 0;
        for(DefaultWeightedEdge e : unvisitedEdges) {
            used.add(e);
            String nextStart = graph.getEdgeTarget(e).equals(start) ? graph.getEdgeSource(e) : graph.getEdgeTarget(e);
            double length = graph.getEdgeWeight(e) + findMaxPathLength(graph, nextStart, used);
            used.remove(e);

            if (length > maxLen) {
                maxLen = length;
            }
        }

        return maxLen;
    }
}