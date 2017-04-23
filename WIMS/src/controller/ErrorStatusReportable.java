package controller;

public interface ErrorStatusReportable {
	
	public void displayErrorStatus(String errorText);
	
	public void clearErrorStatus();
	
	public void displayNeutralStatus(String neutralText);
	
	public void displaySuccessStatus(String successText);
}
