package controller;

public interface ErrorStatusReportable {
	
	public void displayErrorStatus(String errorText);
	
	public void clearErrorStatus();
}
