package com.redbox.medicare;

class Doctor {


    private String id, name, department, degree, medical, visit;

    public Doctor(){}

    public Doctor(String id, String name,  String department, String degree, String medical, String visit) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.degree = degree;
        this.medical = medical;
        this.visit = visit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

}
