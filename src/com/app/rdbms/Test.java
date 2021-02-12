package com.app.rdbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public void insert() {
		Person person = new Person();
		person.setName("pqr");
		person.setMobile("3432423");

		Pancard pancard = new Pancard();
		pancard.setPanNo("SAGFS234F");

		person.setPancard(pancard);

		Connection con = JdbcUtility.getConnection();
		boolean flag = Boolean.FALSE;
		try (PreparedStatement ps = con.prepareCall("insert into person(name,mobile)values(?,?)");) {
			ps.setString(1, person.getName());
			ps.setString(2, person.getMobile());
			int result = ps.executeUpdate();
			if (result > 0) {
				ResultSet rs = JdbcUtility.executeQuery("select max(id) from person");
				if (rs.next())
					person.setPid(rs.getInt(1));
				try (PreparedStatement ps1 = con.prepareCall("insert into pancard(id,pancardno)values(?,?)");) {
					ps1.setInt(1, person.getPid());
					ps1.setString(2, person.getPancard().getPanNo());
					ps1.executeUpdate();
					flag = Boolean.TRUE;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (flag = Boolean.TRUE)
				System.out.println("successfuly inserted");
			else
				System.out.println("failed");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void select() {
		List<Person> list = new ArrayList<Person>();
		Connection con = JdbcUtility.getConnection();
		try (PreparedStatement ps = con
				.prepareStatement("select * from person p inner join pancard pan on p.id=pan.id")) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Person person = new Person();
				person.setPid(rs.getInt(1));
				person.setName(rs.getString(2));
				person.setMobile(rs.getString(3));

				Pancard pancard = new Pancard();
				pancard.setPanId(rs.getInt(4));
				pancard.setPanNo(rs.getString(5));

				person.setPancard(pancard);

				list.add(person);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Person person : list) {
			System.err.println(person.getPid() + "\t" + person.getName() + "\t" + person.getMobile() + "\t"
					+ person.getPancard().getPanNo());
		}

	}

	public static void main(String[] args) {
		Test t = new Test();
		t.insert();
		t.select();
	}

}
