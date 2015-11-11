package com.example.vo;

import java.sql.Date;

public class Oil {
private int id;
private double litre;
private int cost;
private int kilo;
private String systime;
private int useable;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public double getLitre() {
	return litre;
}
public void setLitre(double litre) {
	this.litre = litre;
}
public int getCost() {
	return cost;
}
public void setCost(int cost) {
	this.cost = cost;
}
public int getKilo() {
	return kilo;
}
public void setKilo(int kilo) {
	this.kilo = kilo;
}
public String getSystime() {
	return systime;
}
public void setSystime(String systime) {
	this.systime = systime;
}
public int getUseable() {
	return useable;
}
public void setUseable(int useable) {
	this.useable = useable;
}
}
