package com.balmes.modelo;


import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int emp_no;
    private String last_name;
    private String first_name;
    private Date birth_date;
    private String gender;
    private Date hire_date;
    private String role;
    private double salary;

    @ManyToOne
    @JoinColumn(name = "deptno")
    private Departments department;

    public Employees() {
    }

    public Employees(String last_name, String first_name, Date birth_date, String gender, Date hire_date, String role, double salary, Departments department) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.birth_date = birth_date;
        this.gender = gender;
        this.hire_date = hire_date;
        this.role = role;
        this.salary = salary;
        this.department = department;
    }

    public int getEmp_no() {
        return emp_no;
    }

    public void setEmp_no(int emp_no) {
        this.emp_no = emp_no;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getHire_date() {
        return hire_date;
    }

    public void setHire_date(Date hire_date) {
        this.hire_date = hire_date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Departments getDepartment() {
        return department;
    }

    public void setDepartment(Departments department) {
        this.department = department;
    }
}
