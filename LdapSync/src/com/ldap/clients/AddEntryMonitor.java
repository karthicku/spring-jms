package com.ldap.clients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;

import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPEntry;

import com.ldap.util.DBConnectionTask;

/**
 * @author perficient
 *
 */
public class AddEntryMonitor implements Runnable {

	private LDAPEntry findEntry;

	private Connection con;

	private PreparedStatement preparedStatement;

	public AddEntryMonitor(LDAPEntry findEntry) {
		this.findEntry = findEntry;
	}

	public void run() {

		System.out.println("Entering AddEntryMonitor Thread :");
		HashMap map = new HashMap<String, String>();
		/* Get the set of attributes for that entry. */

		LDAPAttributeSet findAttrs = findEntry.getAttributeSet();

		Enumeration enumAttrs = findAttrs.getAttributes();

		// Iterator attrbs = findAttrs.iterator();
		/* Iterate through the attributes. */
		String attrName = null;
		while (enumAttrs.hasMoreElements()) {

			LDAPAttribute anAttr =

			(LDAPAttribute) enumAttrs.nextElement();

			attrName = anAttr.getName();

			/* Get the set of values for each attribute. */

			Enumeration enumVals = anAttr.getStringValues();

			/* Iterate through the values and print each value. */

			while (enumVals.hasMoreElements()) {

				String aVal = (String) enumVals.nextElement();

				System.out.println(attrName + "\t\t" + aVal);
				map.put(attrName, aVal);

			}

		}

		DBConnectionTask task = new DBConnectionTask();
		con = task.getConnection();

		// Result set get the result of the SQL query

		try {
			// PreparedStatements can use variables and are more efficient
			preparedStatement = con
					.prepareStatement("insert into  DB123.msm_user values (default, ?, ?, ?)");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// Parameters start with 1
			preparedStatement.setString(1, (String) map.get("uid"));
			preparedStatement.setString(2, (String) map.get("displayName"));
			preparedStatement.setDate(3, new java.sql.Date(2009, 12, 11));
			preparedStatement.executeUpdate();
		} catch (Exception eX) {
			eX.printStackTrace();
		} finally {
			close();
		}

	}

	private void close() {
		try {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (con != null) {
				con.close();
			}
		} catch (Exception e) {

		}
	}

}
