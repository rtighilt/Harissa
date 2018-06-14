package com.uqam.latece.harissa.exceptions;

import java.net.MalformedURLException;

/**
 * Created by Karim on 2017-09-22.
 */
public class UncheckedMalformedUrlException extends RuntimeException {

    //region private fields
        private MalformedURLException malformedURLException;
    //endregion

    public UncheckedMalformedUrlException(MalformedURLException malformedURLException){
        this.malformedURLException = malformedURLException;

    }

    public UncheckedMalformedUrlException(MalformedURLException malformedURLException, String message){
        super(message);
        this.malformedURLException = malformedURLException;
    }

    //region methods

    //endregion

    //region private methods
    //endregion

    //region getters

    public MalformedURLException getMalformedURLException() {
        return malformedURLException;
    }


    //endregion

}
