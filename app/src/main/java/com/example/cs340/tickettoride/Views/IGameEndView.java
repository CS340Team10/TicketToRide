package com.example.cs340.tickettoride.Views;

import java.util.ArrayList;
import java.util.List;

import ClientModel.Player;
import common.PlayerPointSummary;

/**
 * Created by Joseph on 3/19/2018.
 */

public interface IGameEndView
{
    void loadPointSummary(List<PlayerPointSummary> playerPointSummaries, ArrayList<Player> players);
}
