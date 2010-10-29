package com.ollijepp.poastr.shared.exceptions.socialService;

public class PoastTooLongException extends Exception {
	int maxChars;
	int currentChars;
	public int getMaxChars() {
		return maxChars;
	}
	public void setMaxChars(int maxChars) {
		this.maxChars = maxChars;
	}
	public int getCurrentChars() {
		return currentChars;
	}
	public void setCurrentChars(int currentChars) {
		this.currentChars = currentChars;
	}
	public PoastTooLongException(int maxChars, int currentChars) {
		super();
		this.maxChars = maxChars;
		this.currentChars = currentChars;
	}
}
