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
        routes.add(new Route("calg_wini", "Calgary", "Winnipeg", 6, TrainCard.Colors.white));
        routes.add(new Route("seat_vanc_1", "Seattle", "Vancouver", 1, TrainCard.Colors.wildcard));
        routes.add(new Route("seat_vanc_2", "Seattle", "Vancouver", 1, TrainCard.Colors.wildcard));
        routes.add(new Route("port_seat_1", "Portland", "Seattle", 1, TrainCard.Colors.wildcard));
        routes.add(new Route("port_seat_2", "Portland", "Seattle", 1, TrainCard.Colors.wildcard));
        routes.add(new Route("sanfran_seat_1", "San Franciso", "Seattle", 5, TrainCard.Colors.green));
        routes.add(new Route("sanfran_seat_2", "San Franciso", "Seattle", 5, TrainCard.Colors.purple));
        routes.add(new Route("la_sanfran_1", "Los Angeles", "San Francisco", 3, TrainCard.Colors.yellow));
        routes.add(new Route("la_sanfran_2", "Los Angeles", "San Francisco", 3, TrainCard.Colors.purple));
        routes.add(new Route("seat_calg", "Seattle", "Calgary", 4, TrainCard.Colors.wildcard));
        routes.add(new Route("calg_hel", "Calgary", "Helena", 4, TrainCard.Colors.wildcard));
        routes.add(new Route("seat_hel", "Seattle", "Helena", 6, TrainCard.Colors.yellow));
        routes.add(new Route("hel_wini", "Helena", "Winnipeg", 4, TrainCard.Colors.blue));
        routes.add(new Route("hel_slc", "Helena", "Salt Lake City", 3, TrainCard.Colors.purple));
        routes.add(new Route("port_slc", "Portland", "Salt Lake City", 6, TrainCard.Colors.blue));
        routes.add(new Route("sanfran_slc_1", "San Francisco", "Salt Lake City", 5, TrainCard.Colors.orange));
        routes.add(new Route("sanfran_slc_2", "San Francisco", "Salt Lake City", 5, TrainCard.Colors.white));
        routes.add(new Route("la_lv", "Los Angeles", "Las Vegas", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("slc_lv", "Salt Lake City", "Las Vegas", 3, TrainCard.Colors.orange));
        routes.add(new Route("la_phoen", "Los Angeles", "Phoenix", 3, TrainCard.Colors.wildcard));
        routes.add(new Route("phoen_elpaso", "Phoenix", "El Paso", 3, TrainCard.Colors.wildcard));
        routes.add(new Route("phoen_santa", "Phoenix", "Santa Fe", 3, TrainCard.Colors.wildcard));
        routes.add(new Route("slc_denver_1", "Salt Lake City", "Denver", 3, TrainCard.Colors.red));
        routes.add(new Route("slc_denver_2", "Salt Lake City", "Denver", 3, TrainCard.Colors.yellow));
        routes.add(new Route("phoen_denver", "Phoenix", "Denver", 5, TrainCard.Colors.white));
        routes.add(new Route("la_elpaso", "Los Angeles", "El Paso", 6, TrainCard.Colors.black));
        routes.add(new Route("elpaso_santa", "El Paso", "Santa Fe", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("santa_denver", "Santa Fe", "Denver", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("denver_hel", "Denver", "Helena", 4, TrainCard.Colors.green));
        routes.add(new Route("wini_sault", "Winnipeg", "Sault St. Marie", 6, TrainCard.Colors.wildcard));
        routes.add(new Route("wini_dulu", "Winnipeg", "Duluth", 4, TrainCard.Colors.black));
        routes.add(new Route("hel_dulu", "Helena", "Duluth", 6, TrainCard.Colors.orange));
        routes.add(new Route("hel_oma", "Helena", "Omaha", 5, TrainCard.Colors.red));
        routes.add(new Route("denver_oma", "Denver", "Omaha", 4, TrainCard.Colors.purple));
        routes.add(new Route("denver_kc_1", "Denver", "Kansas City", 4, TrainCard.Colors.black));
        routes.add(new Route("denver_kc_2", "Denver", "Kansas City", 4, TrainCard.Colors.orange));
        routes.add(new Route("denver_oc", "Denver", "Oklahoma City", 4, TrainCard.Colors.red));
        routes.add(new Route("santa_oc", "Santa Fe", "Oklahoma City", 3, TrainCard.Colors.blue));
        routes.add(new Route("elpaso_oc", "El Paso", "Oklahoma City", 5, TrainCard.Colors.yellow));
        routes.add(new Route("elpaso_dal", "El Paso", "Dallas", 4, TrainCard.Colors.red));
        routes.add(new Route("elpaso_hous", "El Paso", "Houston", 6, TrainCard.Colors.green));
        routes.add(new Route("hous_dal_1", "Houston", "Dallas", 1, TrainCard.Colors.wildcard));
        routes.add(new Route("hous_dal_2", "Houston", "Dallas", 1, TrainCard.Colors.wildcard));
        routes.add(new Route("dal_oc_1", "Dallas", "Oklahoma City", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("dal_oc_2", "Dallas", "Oklahoma City", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("oc_kc_1", "Oklahoma City", "Kansas City", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("oc_kc_2", "Oklahoma City", "Kansas City", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("kc_oma_1", "Kansas City", "Omaha", 1, TrainCard.Colors.wildcard));
        routes.add(new Route("kc_oma_2", "Kansas City", "Omaha", 1, TrainCard.Colors.wildcard));
        routes.add(new Route("oma_dulu_1", "Omaha", "Duluth", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("oma_dulu_2", "Omaha", "Duluth", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("dulu_saul", "Duluth", "Sault St. Marie", 3, TrainCard.Colors.wildcard));
        routes.add(new Route("saul_toro", "Sault St. Marie", "Toronto", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("saul_mont", "Sault St. Marie", "Montreal", 5, TrainCard.Colors.black));
        routes.add(new Route("dulu_toro", "Duluth", "Toronto", 6, TrainCard.Colors.purple));
        routes.add(new Route("mont_bost_1", "Montreal", "Boston", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("mont_bost_2", "Montreal", "Boston", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("bost_ny_1", "Boston", "New York", 2, TrainCard.Colors.yellow));
        routes.add(new Route("bost_ny_2", "Boston", "New York", 2, TrainCard.Colors.red));
        routes.add(new Route("ny_wash_1", "New York", "Washington", 2, TrainCard.Colors.orange));
        routes.add(new Route("ny_wash_2", "New York", "Washington", 2, TrainCard.Colors.black));
        routes.add(new Route("ny_pitts_1", "New York", "Pittsburgh", 2, TrainCard.Colors.white));
        routes.add(new Route("ny_pitts_2", "New York", "Pittsburgh", 2, TrainCard.Colors.green));
        routes.add(new Route("toro_pitts", "Toronto", "Pittsburgh", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("pitts_wash", "Pittsburgh", "Washington", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("pitts_ral", "Pittsburgh", "Raleigh", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("wash_ral_1", "Washington", "Raleigh", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("wash_ral_2", "Washington", "Raleigh", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("ral_atl_1", "Raleigh", "Atlanta", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("ral_atl_2", "Raleigh", "Atlanta", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("atl_char", "Atlanta", "Charleston", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("ral_char", "Raleigh", "Charleston", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("chic_stl_1", "Chicago", "Saint Louis", 2, TrainCard.Colors.green));
        routes.add(new Route("chic_stl_2", "Chicago", "Saint Louis", 2, TrainCard.Colors.white));
        routes.add(new Route("kc_stl_1", "Kansas City", "Saint Louis", 2, TrainCard.Colors.blue));
        routes.add(new Route("kc_stl_2", "Kansas City", "Saint Louis", 2, TrainCard.Colors.purple));
        routes.add(new Route("stl_lr", "Saint Louis", "Little Rock", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("stl_nash", "Saint Louis", "Nashville", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("oc_lr", "Oklahoma City", "Little Rock", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("dal_lr", "Dallas", "Little Rock", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("hous_nor", "Houston", "New Orleans", 2, TrainCard.Colors.wildcard));
        routes.add(new Route("chico_pitts_1", "Chicago", "Pittsburgh", 3, TrainCard.Colors.orange));
        routes.add(new Route("chico_pitts_2", "Chicago", "Pittsburgh", 3, TrainCard.Colors.black));
        routes.add(new Route("dulu_chico", "Duluth", "Chicago", 3, TrainCard.Colors.red));
        routes.add(new Route("toro_mont", "Toronto", "Montreal", 3, TrainCard.Colors.wildcard));
        routes.add(new Route("lr_nash", "Little Rock", "Nashville", 3, TrainCard.Colors.white));
        routes.add(new Route("lr_nor", "Little Rock", "New Orleans", 3, TrainCard.Colors.green));
        routes.add(new Route("mont_ny", "Montreal", "New York", 3, TrainCard.Colors.blue));
        routes.add(new Route("nash_ral", "Nashville", "Raleigh", 3, TrainCard.Colors.black));
        routes.add(new Route("toro_chico", "Toronto", "Chicago", 4, TrainCard.Colors.white));
        routes.add(new Route("chico_oma", "Chicago", "Omaha", 4, TrainCard.Colors.blue));
        routes.add(new Route("atl_nor_1", "Atlanta", "New Orleans", 4, TrainCard.Colors.yellow));
        routes.add(new Route("atl_nor_2", "Atlanta", "New Orleans", 4, TrainCard.Colors.orange));
        routes.add(new Route("char_mia", "Charleston", "Miami", 4, TrainCard.Colors.purple));
        routes.add(new Route("pitts_nash", "Pittsburgh", "Nashville", 4, TrainCard.Colors.yellow));
        routes.add(new Route("nash_atl", "Nashville", "Atlanta", 1, TrainCard.Colors.wildcard));
        routes.add(new Route("atl_mia", "Atlanta", "Miami", 5, TrainCard.Colors.blue));
        routes.add(new Route("stl_pitts", "Saint Louis", "Pittsburgh", 5, TrainCard.Colors.green));
        routes.add(new Route("nor_mia", "New Orleans", "Miami", 6, TrainCard.Colors.red));

        return routes;
    }
}
