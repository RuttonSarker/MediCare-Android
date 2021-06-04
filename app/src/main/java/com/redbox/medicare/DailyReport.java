package com.redbox.medicare;

public class DailyReport {

    private String date, bloodPressure, diabetesTest;

    public DailyReport() {
    }

    public DailyReport(String date, String bloodPressure, String diabetesTest) {
        this.date = date;
        this.bloodPressure = bloodPressure;
        this.diabetesTest = diabetesTest;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getDiabetesTest() {
        return diabetesTest;
    }

    public void setDiabetesTest(String diabetesTest) {
        this.diabetesTest = diabetesTest;
    }
}
