package com.example.cs340.tickettoride.Views;

import java.util.List;

import common.PlayerPointSummary;

/**
 * Created by Joseph on 3/19/2018.
 */

interface IGameEndView
{
    void loadPointSummary(List<PlayerPointSummary> playerPointSummaries);
}
