package com.app.manytomany;

import java.util.List;

public class Employee {
	
	private int id;
	private String name;
	
	private List<Certification> certifications;
	

	public Employee(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Certification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<Certification> certifications) {
		this.certifications = certifications;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", certifications=" + certifications + "]";
	}
	

}
