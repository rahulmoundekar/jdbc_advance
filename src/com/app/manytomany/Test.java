package com.app.manytomany;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.rdbms.JdbcUtility;
import com.mysql.cj.protocol.Resultset;

public class Test {

	public void insert() throws SQLException {

		Employee e1 = new Employee(11, "rahul");

		Certification c1 = new Certification("OCJP");
		Certification c2 = new Certification("java-8");
		Certification c3 = new Certification("Oracle sql");

		List<Certification> list = new ArrayList<>();
		list.add(c1);
		list.add(c2);
		list.add(c3);

		List<Certification> list1 = new ArrayList<Certification>();

		Connection con = JdbcUtility.getConnection();
		PreparedStatement ps = null;

		ps = con.prepareStatement("insert into employee(name)values(?)");
		ps.setString(1, e1.getName());
		int result = ps.executeUpdate();
		if (result > 0) {
			ps = con.prepareStatement("select max(id) from employee");
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				e1.setId(rs.getInt(1));
		}

		for (Certification certification : list) {
			ps = con.prepareStatement("insert into certification(certificate)values(?)");
			ps.setString(1, certification.getCertificate());
			ps.executeUpdate();
		}

		ResultSet rs = con.prepareStatement("select * from certification").executeQuery();
		while (rs.next()) {
			Certification certification = new Certification();
			certification.setId(rs.getInt(1));
			certification.setCertificate(rs.getString(2));
			list1.add(certification);

		}

		List<Certification> clist = new ArrayList<>();
		clist.add(list1.get(0));
		clist.add(list1.get(2));

		e1.setCertifications(clist);
		System.out.println(e1);
		Boolean flag = Boolean.FALSE;
		ps = con.prepareStatement("insert into employee_certification(cid,emp_id)values(?,?)");
		for (Certification certification : e1.getCertifications()) {
			System.out.println(e1.getId() + "\t" + certification.getId());
			ps.setInt(2, e1.getId());
			ps.setInt(1, certification.getId());
			ps.executeUpdate();
			flag = Boolean.TRUE;
		}
		if (flag) {
			System.out.println("success");
		} else
			System.out.println("failed");

	}

	public static void main(String[] args) throws SQLException {
		Test t = new Test();
		t.insert();
	}

}
