package Presenters;

import common.TrainCard;

/**
 * Created by ephraimkunz on 3/3/18.
 */

public interface IPickTrainCardPresenter {
    void pickedFacedown();
    void pickedFaceup(TrainCard card);
}
