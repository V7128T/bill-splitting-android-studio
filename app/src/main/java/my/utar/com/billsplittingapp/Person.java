package my.utar.com.billsplittingapp;

public class Person {

    private String name, calculationType;
    private double totalAmount, individualAmount, percentage;

    public Person(String calculationType, String name,double totalAmount, double individualAmount, double percentage) {
        this.calculationType = calculationType;
        this.name = name;
        this.totalAmount = totalAmount;
        this.individualAmount = individualAmount;
        this.percentage = percentage;
    }
}
