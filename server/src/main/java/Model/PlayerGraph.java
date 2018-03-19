package Model;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common.Route;

/**
 * Created by Brian on 3/17/18.
 */

public class PlayerGraph {

    /**
     * Returns a SimpleWeightedGraph that represents the Routes that have been claimed by the Player
     *
     * @param routes the Routes that have been claimed by the Player
     *
     * @return a SimpleWeightedGraph that represents the Routes that have been claimed by the Player
     */
    public static SimpleWeightedGraph getPlayerGraph(List<Route> routes) {

        // Build player's graph
        SimpleWeightedGraph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (Route route : routes) {
            g.addVertex(route.getStartCity());
            g.addVertex(route.getEndCity());
            DefaultWeightedEdge e = g.addEdge(route.getStartCity(), route.getEndCity());
            g.setEdgeWeight(e, route.getRouteLength());
        }

        return g;
    }

    /**
     * Determines which Player had the longest Route in the game
     *
     * @param players a List of Players in the Game
     * @param allRoutes a List of Routes in the Game
     * @return the index of the Player with
     */
    public static List<Integer> calculateLongestPath(List<Player> players, List<Route> allRoutes){

        double longestPath = 0;
        List<Integer> longestIndexes = new ArrayList<Integer>();
        for (int count = 0; count < players.size(); count++){

            Player player = players.get(count);

            // get all Routes claimed by the Player
            List<Route> playerRoutes = player.getPlayerRoutes(allRoutes);

            // Build player's graph
            SimpleWeightedGraph<String, DefaultWeightedEdge> g = getPlayerGraph(playerRoutes);

            // Add max path length to longestPath array
            double maxLength = 0;
            for (String start : g.vertexSet()) {
                double length = findMaxPathLength(g, start, new HashSet<DefaultWeightedEdge>());
                if (length > maxLength) {
                    maxLength = length;
                }
            }

            if (maxLength == longestPath){
                longestIndexes.add(count);
            }
            else if (maxLength > longestPath) {
                longestPath = maxLength;
                longestIndexes.clear();
                longestIndexes.add(count);
            }
        }



        return longestIndexes;
    }


    /**
     * Calculates the longest path for a Player
     *
     * @param graph the SimpleWeightedGraph to use
     * @param start the point in the Graph to start at
     * @param used the nodes that have been visited
     * @return the longest length found in the graph
     */
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
