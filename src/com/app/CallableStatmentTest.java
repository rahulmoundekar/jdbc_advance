package com.app;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CallableStatmentTest {

	Scanner sc = new Scanner(System.in);
	private boolean flag = Boolean.FALSE;

	public void insertEmployee() {
		try (CallableStatement cs = JdbcUtility.getConnection().prepareCall("{call insertEmployee(?,?)}");) {
			System.out.println("How many employee do you want to add");
			int noOfEmp = sc.nextInt();
			for (int i = 0; i < noOfEmp; i++) {
				Employee employee = new Employee();
				System.out.println("Enter Name ");
				employee.setName(sc.next());
				System.out.println("Enter Address");
				employee.setAddress(sc.next());

				cs.setString(1, employee.getName());
				cs.setString(2, employee.getAddress());

				cs.executeUpdate();
				flag = Boolean.TRUE;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (flag == Boolean.TRUE)
			System.out.println("employee successfuly inserted");
		else
			System.err.println("employee not saved!!!");

	}

	public void selectEmployee() {
		List<Employee> employees = new ArrayList<Employee>();
		try (ResultSet rs = JdbcUtility.executeQuery("{call selectEmployee()}");) {
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CallableStatmentTest test = new CallableStatmentTest();
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("1} insert");
			System.out.println("2} select");
			
			System.out.println("Select any one option");
			int option = sc.nextInt();

			switch (option) {
			case 1:
				test.insertEmployee();
				break;
			case 2:
				test.selectEmployee();
				break;
				
			default:
				System.exit(0);
				break;
			}
		}

	}

}
