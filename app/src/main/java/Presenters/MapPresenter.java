package Presenters;

import com.example.cs340.tickettoride.Views.IMapView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ClientModel.ClientModel;
import ClientModel.Player;
import common.Results;
import common.Route;

/**
 * Created by ephraimkunz on 3/3/18.
 */

public class MapPresenter implements IMapPresenter, IPresenter, Observer {
    IMapView view;

    public MapPresenter(IMapView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void onPostExecute(Results result) {
    }

    @Override
    public void update(Observable observable, Object o) {
        List<Route> routes = ClientModel.getInstance().getGameRoutes();
        for(Route r : routes) {
            if (r.getOwnedByPlayerID() != null) {
                Player player = ClientModel.getInstance().getPlayerByID(r.getOwnedByPlayerID());
                view.drawRouteAsClaimed(r.getRouteID(), player.getColor());
            }
        }
    }
}
