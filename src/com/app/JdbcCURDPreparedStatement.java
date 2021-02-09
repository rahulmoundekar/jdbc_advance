package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class JdbcCURDPreparedStatement {
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

	public void insertEmployee() {
		try (PreparedStatement ps = con.prepareStatement("insert into employee(name,address)values(?,?)");) {
			System.out.println("How many employee do you want to add");
			int noOfEmp = sc.nextInt();
			for (int i = 0; i < noOfEmp; i++) {
				Employee employee = new Employee();
				System.out.println("Enter Name ");
				employee.setName(sc.next());
				System.out.println("Enter Address");
				employee.setAddress(sc.next());

				ps.setString(1, employee.getName());
				ps.setString(2, employee.getAddress());

				ps.executeUpdate();
				flag = Boolean.TRUE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag == Boolean.TRUE)
			System.out.println("employee successfuly inserted");
		else
			System.err.println("employee not saved!!!");

	}

	public void selectEmployee() {
		List<Employee> employees = new ArrayList<Employee>();
		try (PreparedStatement smt = con.prepareStatement("select * from employee");) {
			ResultSet rs = smt.executeQuery();
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

	public void maxRecord() {
		String sql="select max(id) from employee";
		try (PreparedStatement ps = con.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) //itr.hasNext()  itr.next()
				System.out.println(rs.getInt(1));
			else
				System.out.println("record not found");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JdbcCURDPreparedStatement curd = new JdbcCURDPreparedStatement();
		// curd.updateEmployee();
		// curd.deleteEmployee();
		// curd.insertEmployee();
		// curd.selectEmployee();
		curd.maxRecord();
	}

}
