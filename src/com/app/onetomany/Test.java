package com.app.onetomany;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.app.rdbms.JdbcUtility;
import com.mysql.cj.protocol.Resultset;

public class Test {

	public void insert() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("how many employee");
		int noofemployee = scanner.nextInt();
		List<Employee> employees = new ArrayList<>();
		for (int i = 0; i < noofemployee; i++) {

			Employee employee = new Employee();
			System.out.println("enter name");
			employee.setName(scanner.next());
			List<Address> addresses = new ArrayList<>();
			System.out.println("How many addresses");
			int noAdd = scanner.nextInt();
			for (int j = 0; j < noAdd; j++) {
				Address address = new Address();
				System.out.println("enter city");
				address.setCity(scanner.next());
				System.out.println("enter pincode");
				address.setPincode(scanner.nextInt());
				addresses.add(address);
			}
			employee.setAddresses(addresses);
			employees.add(employee);
		}
		boolean flag = Boolean.FALSE;
		PreparedStatement ps = null;
		for (Employee employee : employees) {
			Connection con = JdbcUtility.getConnection();
			ps = con.prepareStatement("insert into employee(name)values(?)");
			ps.setString(1, employee.getName());
			int result = ps.executeUpdate();
			if (result > 0) {
				ps = con.prepareStatement("select max(id) from employee");
				ResultSet rs = ps.executeQuery();
				if (rs.next())
					employee.setId(rs.getInt(1));
				for (Address addr : employee.getAddresses()) {
					ps = con.prepareStatement("insert into address(city,pincode,eid)values(?,?,?)");
					ps.setString(1, addr.getCity());
					ps.setInt(2, addr.getPincode());
					ps.setInt(3, employee.getId());
					ps.executeUpdate();
					flag = Boolean.TRUE;
				}
			}
			if (flag) {
				System.out.println("success");
			} else
				System.out.println("failed");
		}

	}

	public void select() throws SQLException {
		List<Employee> employees = new ArrayList<Employee>();
		Connection con = JdbcUtility.getConnection();

		ResultSet rs = con.prepareStatement("select * from employee").executeQuery();
		while (rs.next()) {
			Employee employee = new Employee();
			employee.setId(rs.getInt(1));
			employee.setName(rs.getString(2));
			employees.add(employee);
		}

		for (Employee employee : employees) {
			List<Address> addresses = new ArrayList<>();
			ResultSet rs1 = con.prepareStatement(
					"select a.id,a.city,a.pincode from employee e inner join address a on e.id=a.eid where a.eid="
							+ employee.getId())
					.executeQuery();
			while (rs1.next()) {
				Address address = new Address();
				address.setAid(rs1.getInt(1));
				address.setCity(rs1.getString(2));
				address.setPincode(rs1.getInt(3));
				address.setEmployee(employee); // has-a
				addresses.add(address);
			}

			employee.setAddresses(addresses);// has-a
		}

		for (Employee employee : employees) {
			System.out.println(employee.getId() + "\t" + employee.getName());
			for (Address address : employee.getAddresses()) {
				System.out.println("\t" + address.getAid() + "\t" + address.getCity() + "\t" + address.getPincode());
			}
		}
	}

	public static void main(String[] args) {
		Test t = new Test();
		try {
			t.insert();
			t.select();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
