package com.yaoguang.interactor.driver.order.map;


import java.util.List;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/9 0009.
 * 版    权：
 */
public interface MapSearchContact {
    interface View<T> {

        void searchPoi(String keyword, String city);

        void autoNav();

        void searchInputInit();

        void navComeback();

        void showData(List<T> result);

        void showSearchDialog();

        void hideSearchDialog();

        void searchPlanLingMap();

        void setSearchText(String word);

        void searchKeyword(String word);

        void initSearchCache();

        void saveSearchCache(T keyword);

        boolean filterWord(T word);

    }
}
