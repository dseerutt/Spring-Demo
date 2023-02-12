package com.spring.demo.dseerutt.model.object;

import java.sql.Date;


public class ComputerStore {

    private Computer computer;
    private String description;
    private Date lastProvisionDate;

    public ComputerStore() {
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastProvisionDate() {
        return lastProvisionDate;
    }

    public void setLastProvisionDate(Date lastProvisionDate) {
        this.lastProvisionDate = lastProvisionDate;
    }
}
