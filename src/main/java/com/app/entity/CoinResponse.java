package com.app.entity;

public class CoinResponse {

	private String message;
    private String validTill;
    private double remainingChange;
    private double refundAmount;

    public CoinResponse(String message, String validTill, double remainingChange) {
        this.message = message;
        this.validTill = validTill;
        this.remainingChange = remainingChange;
    }

    public CoinResponse(double refundAmount) {
        this.refundAmount = refundAmount;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getValidTill() {
		return validTill;
	}

	public void setValidTill(String validTill) {
		this.validTill = validTill;
	}

	public double getRemainingChange() {
		return remainingChange;
	}

	public void setRemainingChange(double remainingChange) {
		this.remainingChange = remainingChange;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

}
