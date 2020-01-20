package com.example.diplom1.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("Type")
    @Expose
    private String type;

    @SerializedName("Customer")
    @Expose
    private String customer;
    @SerializedName("CustomerAddress")
    @Expose
    private String customerAddress;
    @SerializedName("CustomerINN")
    @Expose
    private String customerINN;

    @SerializedName("Executor")
    @Expose
    private String executor;
    @SerializedName("ExecutorAddress")
    @Expose
    private String executorAddress;
    @SerializedName("ExecutorINN")
    @Expose
    private String executorINN;

    @SerializedName("Materials")
    @Expose
    private String materials;
    @SerializedName("Squares")
    @Expose
    private String squares;
    @SerializedName("Prices")
    @Expose
    private String prices;
    @SerializedName("Cost")
    @Expose
    private String cost;
    @SerializedName("TileNum")
    @Expose
    private Integer tileNum;

    @SerializedName("ExciseAmount")
    @Expose
    private String exciseAmount;
    @SerializedName("TaxRate")
    @Expose
    private String taxRate;
    @SerializedName("TaxSum")
    @Expose
    private String taxSum;
    @SerializedName("RegistrationNumber")
    @Expose
    private String registrationNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerINN() {
        return customerINN;
    }

    public void setCustomerINN(String customerINN) {
        this.customerINN = customerINN;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getExecutorAddress() {
        return executorAddress;
    }

    public void setExecutorAddress(String executorAddress) {
        this.executorAddress = executorAddress;
    }

    public String getExecutorINN() {
        return executorINN;
    }

    public void setExecutorINN(String executorINN) {
        this.executorINN = executorINN;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getSquares() {
        return squares;
    }

    public void setSquares(String squares) {
        this.squares = squares;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Integer getTileNum() {
        return tileNum;
    }

    public void setTileNum(Integer tileNum) {
        this.tileNum = tileNum;
    }

    public String getExciseAmount() {
        return exciseAmount;
    }

    public void setExciseAmount(String exciseAmount) {
        this.exciseAmount = exciseAmount;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxSum() {
        return taxSum;
    }

    public void setTaxSum(String taxSum) {
        this.taxSum = taxSum;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
