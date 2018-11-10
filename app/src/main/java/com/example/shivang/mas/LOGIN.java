package com.example.shivang.mas;

public class LOGIN {

    //private variables
    String _id;
    String _name;
    String _pwd;

    // Empty constructor
    public LOGIN(){

    }
    // constructor
    public LOGIN(String id, String name, String _pwd){
        this._id = id;
        this._name = name;
        this._pwd = _pwd;
    }

    // constructor
    public LOGIN(String name, String _pwd){
        this._name = name;
        this._pwd = _pwd;
    }
    // getting ID
    public String getID(){
        return this._id;
    }

    // setting id
    public void setID(String id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getpwd(){
        return this._pwd;
    }

    // setting phone number
    public void setpwd(String pwd){
        this._pwd = pwd;
    }
}