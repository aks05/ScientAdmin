package com.example.scientadmin;

public class Project {

    public String name;
    public String roll_No;
    public String contact_No;
    public String email;
    public String projectTitle;
    public String projectDesc;
    public String token;

    public Project() {

    }

    public Project(String Name, String Roll_No, String contact_No, String email,
                   String ProjectTitle, String ProjectDesc, String token) {
        this.name= Name;
        this.roll_No= Roll_No;
        this.contact_No=contact_No;
        this.email=email;
        this.projectTitle= ProjectTitle;
        this.projectDesc= ProjectDesc;
        this.token= token;
    }

    public String getroll_No() {
        return roll_No;
    }

    public String getname() {
        return name;
    }

    public String getprojectDesc() {
        return projectDesc;
    }

    public String getprojectTitle() {
        return projectTitle;
    }

    public String gettoken() {
        return token;
    }

    public String getcontact_No() {
        return contact_No;
    }

    public String getemail() {
        return email;
    }

}
