package common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ephraimkunz on 2/28/18.
 */

public class GameRoutes {
    public static List<Route> getAllRoutes() {
        ArrayList<Route> routes = new ArrayList<>();
        routes.add(new Route("vanc_calg", "Vancouver", "Calgary", 3, TrainCard.Colors.wildcard));
        return routes;
    }
}
