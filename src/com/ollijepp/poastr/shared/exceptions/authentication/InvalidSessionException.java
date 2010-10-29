package com.ollijepp.poastr.shared.exceptions.authentication;

import net.customware.gwt.dispatch.shared.ActionException;

public class InvalidSessionException extends ActionException{
    private static final long serialVersionUID = 995112620968798947L;

    public InvalidSessionException(String msg) {
        super(msg);
    }
}
