package my.utar.com.billsplittingapp;

import com.google.gson.annotations.SerializedName;

public class HistoryItem {

    @SerializedName("date")
    private String date;
    @SerializedName("name")
    private String name;
    @SerializedName("calculationType")
    private String calculationType;
    @SerializedName("totalAmount")
    private double totalAmount;
    @SerializedName("individualAmount")
    private double individualAmount;
    @SerializedName("percentage")
    private double percentage;
    @SerializedName("result")
    private String result;

    // Constructor
    public HistoryItem(String date, String name, String calculationType, double totalAmount,
                       double individualAmount, double individualPercentage, String result) {
        this.date = date;
        this.name = name;
        this.calculationType = calculationType;
        this.totalAmount = totalAmount;
        this.individualAmount = individualAmount;
        this.percentage = individualPercentage;
        this.result = result;
    }

    // Getters for each field
    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getCalculationType() {
        return calculationType;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getIndividualAmount() {
        return individualAmount;
    }

    public double getIndividualPercentage() {
        return percentage;
    }

    public String getResult() {
        return result;
    }

    // Setters for each field
    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCalculationType(String calculationType) {
        this.calculationType = calculationType;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setIndividualAmount(double individualAmount) {
        this.individualAmount = individualAmount;
    }

    public void setIndividualPercentage(double individualPercentage) {
        this.percentage = individualPercentage;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
