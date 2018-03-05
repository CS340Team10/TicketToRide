package Presenters;

import android.support.v4.util.Pair;

import java.util.List;

import common.ICard;
import common.Route;

/**
 * Created by Joseph on 3/5/2018.
 */

public interface IClaimRoutePresenter
{
    void choseRoute(Route route, List<Pair<ICard, Integer>> usedCards);
}
