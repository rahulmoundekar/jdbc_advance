package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class JdbcCURD {
	private static Connection con = null;
	private static String URL = "jdbc:mysql://localhost:3306/clc";
	private static String USERNAME = "root";
	private static String PASSWORD = "root";
	private boolean flag = Boolean.FALSE;
	Scanner sc = new Scanner(System.in);
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public void insertEmployee() throws SQLException {
		Statement smt = con.createStatement();
		System.out.println("How many employee do you want to add");
		int noOfEmp = sc.nextInt();
		for (int i = 0; i < noOfEmp; i++) {
			Employee employee = new Employee();
			System.out.println("Enter Name ");
			employee.setName(sc.next());
			System.out.println("Enter Address");
			employee.setAddress(sc.next());
			smt.executeUpdate("insert into employee(name,address)values('" + employee.getName() + "','"
					+ employee.getAddress() + "')");
			flag = Boolean.TRUE;

		}
		if (flag == Boolean.TRUE)
			System.out.println("employee successfuly inserted");
		else
			System.err.println("employee not saved!!!");

	}

	public void selectEmployee() {
		List<Employee> employees = new ArrayList<Employee>();
		try (Statement smt = con.createStatement();) {
			ResultSet rs = smt.executeQuery("select * from employee");
			while (rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getInt(1));
				employee.setName(rs.getString(2));
				employee.setAddress(rs.getString(3));
				employees.add(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Employee employee : employees) {
			System.out.println(employee.getId() + "\t" + employee.getName() + "\t" + employee.getAddress());
		}

	}

	public void updateEmployee() {
		try (Statement smt = con.createStatement();) {
			System.out.println("which row you want to update : ");
			int id = sc.nextInt();
			System.out.println("Enter name");
			String name = sc.next();
			int result = smt.executeUpdate("update employee set name='" + name + "' where id=" + id + "");
			if (result > 0) {
				System.out.println("successfuly update");
			} else
				System.err.println("update Failed..Try again..!!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void deleteEmployee() {
		try (Statement smt = con.createStatement();) {
			System.out.println("which row you want to Delete : ");
			int id = sc.nextInt();
			
			int result = smt.executeUpdate("delete from employee where id=" + id + "");
			if (result > 0) {
				System.out.println("successfuly Deleted");
			} else
				System.err.println("delete Failed..Try again..!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	public static void main(String[] args) {
		JdbcCURD curd = new JdbcCURD();
			//curd.updateEmployee();
			curd.deleteEmployee();
			curd.selectEmployee();
			//curd.insertEmployee();
	}

}
