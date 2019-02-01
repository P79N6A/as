package com.lcodecore.tkrefreshlayout.processor;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;

/**
 * Created by lcodecore on 2017/3/3.
 */

public interface IAnimRefresh {
    void scrollHeadByMove(float moveY);

    void scrollBottomByMove(float moveY);

    void animHeadToRefresh();

    void animHeadBack(boolean isFinishRefresh, final RefreshListenerAdapter refreshListener);

    void animHeadHideByVy(int vy);

    void animBottomToLoad();

    void animBottomBack(boolean isFinishRefresh);

    void animBottomHideByVy(int vy);
}
