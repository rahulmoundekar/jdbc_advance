package com.app.manytomany;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.app.rdbms.JdbcUtility;

public class ProductColorTest {

	Scanner sc = new Scanner(System.in);

	public void addColors() throws SQLException {
		boolean flag = Boolean.FALSE;
		System.out.println("How many colors : ");
		Connection con = JdbcUtility.getConnection();
		con.setAutoCommit(false);
		PreparedStatement ps = con.prepareStatement("insert into color(color)values(?)");
		int no = sc.nextInt();
		for (int i = 0; i < no; i++) {
			Color color = new Color();
			System.out.println("enter color");
			color.setColor(sc.next());
			ps.setString(1, color.getColor());
			ps.executeUpdate();
			con.commit();
			flag = Boolean.TRUE;
		}
		if (flag)
			System.out.println("success");
		else
			System.out.println("failure");
	}

	public void displayColors() throws SQLException {
		List<Color> colorsList = new ArrayList<Color>();
		Connection con = JdbcUtility.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from color");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Color color = new Color();
			color.setId(rs.getInt(1));
			color.setColor(rs.getString(2));
			colorsList.add(color);
		}

		for (Color color : colorsList) {
			System.out.println(color.getId() + "\t" + color.getColor());
		}

	}

	public void addProducts() throws SQLException {
		List<Product> products = new ArrayList<>();
		PreparedStatement ps = null;

		boolean flag = Boolean.FALSE;
		System.out.println("How many Product : ");

		int no = sc.nextInt();
		for (int i = 0; i < no; i++) {
			Product product = new Product();
			System.out.println("Enter Product Name");
			product.setName(sc.next());
			System.out.println("enter Price");
			product.setPrice(sc.nextDouble());
			System.out.println("++++++++++++++++++++++++++++");
			displayColors();
			System.out.println("++++++++++++++++++++++++++++");
			System.out.println("how many colors do you want to allocate");
			int n = sc.nextInt();
			List<Color> allocatedColorsList = new ArrayList<>();

			for (int j = 0; j < n; j++) {
				Color color = new Color();
				System.out.println("enter color id");
				color.setId(sc.nextInt());
				allocatedColorsList.add(color);
			}

			product.setColors(allocatedColorsList);
			products.add(product);
		}

		Connection con = JdbcUtility.getConnection();
		ps = con.prepareStatement("insert into product(name,price)values(?,?)");

		for (Product product2 : products) {

			ps.setString(1, product2.getName());
			ps.setDouble(2, product2.getPrice());

			int result = ps.executeUpdate();

			if (result > 0) {
				ResultSet rs1 = con.prepareStatement("select max(id) from product").executeQuery();
				if (rs1.next()) {
					product2.setId(rs1.getInt(1));
					PreparedStatement ps1 = con.prepareStatement("insert into product_color(pid,cid)values(?,?)");
					for (Color color : product2.getColors()) {

						ps1.setInt(1, product2.getId());
						ps1.setInt(2, color.getId());
						ps1.executeUpdate();

						flag = Boolean.TRUE;
					}
					if (flag) {
						System.out.println("success");

					} else
						System.out.println("failure");
				}
			}
		}
	}

	public void select() {

		// select all products

		// select all colors with inner join for

		/*
		 * select c.id,c.color from product p inner join color c inner join
		 * product_color pc on p.id=pc.pid and c.id=pc.cid where pc.pid=product.getId();
		 */

	}

	public static void main(String[] args) {
		ProductColorTest t = new ProductColorTest();
		try {
			t.addColors();
			//t.addProducts();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
