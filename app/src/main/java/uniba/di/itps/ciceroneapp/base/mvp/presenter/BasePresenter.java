package uniba.di.itps.ciceroneapp.base.mvp.presenter;


import uniba.di.itps.ciceroneapp.base.mvp.view.BaseView;

/**
 * Created by H.T. on 01/12/17.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void onDestroy();
}