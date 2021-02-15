package com.app.manytomany;

import java.util.List;

public class Certification {

	private int id;
	private String certificate;
	private List<Employee> employees;
	
	public Certification() {
		// TODO Auto-generated constructor stub
	}
	
	public Certification(String certificate) {
		this.certificate = certificate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Certification [id=" + id + ", certificate=" + certificate + "]";
	}
	
}
