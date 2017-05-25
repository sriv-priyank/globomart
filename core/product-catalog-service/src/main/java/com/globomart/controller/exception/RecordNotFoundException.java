package com.globomart.controller.exception;

import com.globomart.service.exception.RecordDoesNotExistException;


public final class RecordNotFoundException extends ApiException {

	private static final long serialVersionUID = -8928433347702090662L;

	public RecordNotFoundException(RecordDoesNotExistException e){
		super(e.getMessage(), e);
	}
}
