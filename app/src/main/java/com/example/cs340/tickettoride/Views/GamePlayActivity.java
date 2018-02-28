package com.example.cs340.tickettoride.Views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.cs340.tickettoride.R;

import java.util.List;

import common.DestCard;
import common.PlayerAttributes;
import common.TrainCard;

public class GamePlayActivity extends Activity implements IGamePlayView {

    IMapView mapView = new MapView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        ImageView map = findViewById(R.id.mapView);
        mapView.setParams(this, map);
        mapView.drawRouteAsClaimed("vanc_calg", PlayerAttributes.Color.red);
        mapView.drawRouteAsClaimed("calg_wini", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("seat_vanc_1", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("seat_vanc_2", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("port_seat_1", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("port_seat_2", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("sanfran_seat_1", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("sanfran_seat_2", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("la_sanfran_1", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("la_sanfran_2", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("seat_calg", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("calg_hel", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("seat_hel", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("hel_wini", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("hel_slc", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("port_slc", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("sanfran_slc_1", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("sanfran_slc_2", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("la_lv", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("slc_lv", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("la_phoen", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("phoen_elpaso", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("phoen_santa", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("slc_denver_1", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("slc_denver_2", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("phoen_denver", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("la_elpaso", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("elpaso_santa", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("santa_denver", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("denver_hel", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("wini_sault", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("wini_dulu", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("hel_dulu", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("hel_oma", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("denver_oma", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("denver_kc_1", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("denver_kc_2", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("denver_oc", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("santa_oc", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("elpaso_oc", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("elpaso_dal", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("elpaso_hous", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("hous_dal_1", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("hous_dal_2", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("dal_oc_1", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("dal_oc_2", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("oc_kc_1", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("oc_kc_2", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("kc_oma_1", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("kc_oma_2", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("oma_dulu_1", PlayerAttributes.Color.green);
        mapView.drawRouteAsClaimed("oma_dulu_2", PlayerAttributes.Color.green);

    }

    @Override
    public void enableTrainCardButton() {

    }

    @Override
    public void disableTrainCardButton() {

    }

    @Override
    public void enableDrawRouteButton() {

    }

    @Override
    public void disableDrawRouteButton() {

    }

    @Override
    public void enableClaimRouteButton() {

    }

    @Override
    public void disableClaimRouteButton() {

    }

    @Override
    public void dismissPickDestCardView() {

    }

    @Override
    public void offerDestCards(List<DestCard> cards) {

    }

    @Override
    public void dismissPickTrainCardView() {

    }

    @Override
    public void offerTrainCards(List<TrainCard> cards) {

    }

    @Override
    public void dismissClaimRouteView() {

    }

    @Override
    public void offerRoutes(List<String> routeIDs) {

    }

    @Override
    public void setDestDeckSize(int n) {

    }

    @Override
    public void setTrainDeckSize(int n) {

    }
}
