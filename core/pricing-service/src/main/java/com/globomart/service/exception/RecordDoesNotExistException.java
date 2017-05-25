package com.globomart.service.exception;

public final class RecordDoesNotExistException extends ServiceException {

	private static final long serialVersionUID = 2536181525886249209L;

	public RecordDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public RecordDoesNotExistException() {
		super("Record does not exist", new RuntimeException());
	}
}
