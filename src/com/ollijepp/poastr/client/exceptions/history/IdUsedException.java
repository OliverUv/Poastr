package com.ollijepp.poastr.client.exceptions.history;

public class IdUsedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1448528734372569391L;
	String id;
	public IdUsedException(String id) {
		super();
		this.id = id;
	}
	public String getUsedId(){
		return id;
	}
	
}
