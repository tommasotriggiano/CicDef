package uniba.di.itps.ciceroneapp.base.mvp.validator;

/**
 * Created by H.T. on 01/12/17.
 */

public interface IFormValidator {
    boolean validate();

    void onValidationSuccess();

    void onValidationError(String errorMessage);
}
