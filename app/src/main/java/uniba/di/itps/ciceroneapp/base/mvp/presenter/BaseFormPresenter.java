package uniba.di.itps.ciceroneapp.base.mvp.presenter;


import uniba.di.itps.ciceroneapp.base.mvp.validator.IFormValidator;
import uniba.di.itps.ciceroneapp.base.mvp.view.BaseView;

/**
 * Created by H.T. on 01/12/17.
 */

public interface BaseFormPresenter<T extends BaseView> extends BasePresenter<T> {
    void addValidator(IFormValidator formValidator);
}