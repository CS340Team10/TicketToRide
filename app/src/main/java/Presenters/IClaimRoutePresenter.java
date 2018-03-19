package Presenters;

import android.support.v4.util.Pair;

import java.util.List;
import java.util.Map;

import common.ICard;
import common.Route;

/**
 * Created by Joseph on 3/5/2018.
 */

public interface IClaimRoutePresenter
{
    void choseRoute(Route route, Map<ICard, Integer> usedCards);
    void onClickClaimRoute();
    void onChangeSelection(Route selectedRoute, Map<ICard, Integer> selectedCards);
}
