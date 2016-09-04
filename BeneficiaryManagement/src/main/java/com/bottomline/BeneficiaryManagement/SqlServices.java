package com.bottomline.BeneficiaryManagement;

import java.io.IOException;
import java.sql.Connection;

import javax.swing.JOptionPane;
import javax.xml.parsers.*;

import org.xml.sax.*;
import org.w3c.dom.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SqlServices {
	
	/**
	 * http://stackoverflow.com/questions/7373567/java-how-to-read-and-write-xml-files
	 * Resource
	 * @return
	 * @throws SQLException
	 */
	private static Connection getConnection() throws SQLException
	{
		String username = "";
		String password = "";//solarLint can't tell me what to do
		String connectionURL = "";
	    Document dom;
	        // Make an  instance of the DocumentBuilderFactory
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        // use the factory to take an instance of the document builder
	    	DocumentBuilder db = dbf.newDocumentBuilder();
	    	// parse using the builder to get the DOM mapping of the    
	    	// XML file
	        dom = db.parse(SqlServices.class.getClassLoader().getResource("ServerInfo.xml").toString());
	        Element doc = dom.getDocumentElement(); 
	        username = getTextValue(username, doc, "username");
	        password = getTextValue(password, doc, "password");
	        connectionURL = getTextValue(connectionURL, doc, "url");
	    } catch (ParserConfigurationException pce) {
			JOptionPane.showMessageDialog(null, pce.toString());
	    } catch (SAXException se) {
			JOptionPane.showMessageDialog(null, se.toString());
	    } catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, ioe.toString());
	    }
		Connection conn;
		final Properties connectionProps = new Properties();
		connectionProps.put("user", username);
		connectionProps.put("password", password);
		conn = DriverManager.getConnection(connectionURL, connectionProps);
		return conn;
	}
	
	/**
	 * http://stackoverflow.com/questions/7373567/java-how-to-read-and-write-xml-files
	 * @param def
	 * @param doc
	 * @param tag
	 * @return
	 */
	private static String getTextValue(String def, Element doc, String tag) {
	    String value = def;
	    NodeList nl;
	    nl = doc.getElementsByTagName(tag);
	    if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
	        value = nl.item(0).getFirstChild().getNodeValue();
	    }
	    return value;
	}
	
	/**
	 * Performs some sql function with the given contact
	 * 
	 * @param mode decides what the sql will do with the contact.  "readall" for a SELECT *
	 * 	"insert" for a DELETE followed by INSERT.
	 * 	"remove" for a DELETE.
	 * @param c a contact
	 * @return a list of all contacts if "readall" is the mode
	 * @throws SQLException
	 */
	public static List<Contact> lookUpContact(final String mode, final Contact c) 
			throws SQLException
	{
		Connection conn = getConnection();
		if("readall".equals(mode)){
			return readAllContacts(conn); //see Utility.fillRows
		}
		if("insert".equals(mode)){
			insertContact(conn,c); //see button events in ManagementWindow.java
		}
		if("remove".equals(mode)){
			removeContact(conn,c); //see remove button event in ManagementWindow.java
		}
		conn.close();
		return null;
	}
	
	private static void removeContact(final Connection con, final Contact c)
			throws SQLException
	{
		Statement stmt = null;
		int business = c.isBusiness() ? 1 : 0;
		int individual = c.isIndividual() ? 1 : 0;
		int employee = c.isEmployee() ? 1 : 0;
		String query = "DELETE FROM CONTACTINFO WHERE "
				+ "'" + replaceEscape(c.getName()) + "' = name AND"
				+ "'" + business + "' = is_business AND"
				+ "'" + individual + "' = is_individual AND"
				+ "'" + employee + "' = is_employee";
		try
		{
			stmt = con.createStatement();
			stmt.executeQuery(query);
		}
		catch (final SQLException e)
		{
			JOptionPane.showMessageDialog(null, "SQL EXCEPTION:" + e.toString());
		}
		finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
		}
	}
	
	private static List<Contact> readAllContacts(final Connection con)
			throws SQLException
	{
 		List<Contact> contacts = new ArrayList<>();
		Statement stmt = null;
		final String query = "SELECT * FROM CONTACTINFO";
	 	try
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				Contact c = new Contact();
				c.setName(rs.getString("NAME"));
				c.setBusiness(rs.getBoolean("IS_BUSINESS"));
				c.setIndividual(rs.getBoolean("IS_INDIVIDUAL"));
				c.setEmployee(rs.getBoolean("IS_EMPLOYEE"));
				c.setContactID(rs.getString("CONTACT_ID"));
				c.setAddressOne(rs.getString("ADDRESS1"));
				c.setAddressTwo(rs.getString("ADDRESS2"));
				c.setCity(rs.getString("CITY"));
				c.setState(rs.getString("STATE"));
				c.setProvince(rs.getString("PROVINCE"));
				c.setPostalCode(rs.getString("POSTAL_CODE"));
				c.setCountry(rs.getString("COUNTRY"));
				c.setContactName(rs.getString("CONTACT_NAME"));
				c.setEmail(rs.getString("EMAIL"));
				c.setPhone(rs.getString("PHONE"));
				c.setMobile(rs.getString("MOBILE"));
				c.setFax(rs.getString("FAX"));
				c.setDateTimeEntered(rs.getString("TIMESTAMP_ENTERED"));
				c.setDateTimeModified(rs.getString("TIMESTAMP_MODIFIED"));
				c.setStatus(rs.getString("STATUS"));
				contacts.add(c);
			}
			
		}
		catch (final SQLException e)
		{
			JOptionPane.showMessageDialog(null, "SQL EXCEPTION: " + e.toString());
		}
		finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
		}
	 	return contacts;
	}
	 	
	private static void insertContact(final Connection con, final Contact c)
				throws SQLException
		{
			//Attempts to delete the user first so we don't end up with key conflicts.
			Statement stmt = null;
			int business = c.isBusiness() ? 1 : 0;
			int individual = c.isIndividual() ? 1 : 0;
			int employee = c.isEmployee() ? 1 : 0;
			String query = "DELETE FROM CONTACTINFO WHERE "
					+ "'" + replaceEscape(c.getName()) + "' = name AND"
					+ "'" + business + "' = is_business AND"
					+ "'" + individual + "' = is_individual AND"
					+ "'" + employee + "' = is_employee";
			try
			{
				stmt = con.createStatement();
				stmt.executeQuery(query);
			}
			catch (final SQLException e)
			{
				JOptionPane.showMessageDialog(null, "SQL EXCEPTION: " + e.toString());
			}
			finally
			{
				if (stmt != null)
				{
					stmt.close();
				}
			}
			query = 
					"INSERT INTO CONTACTINFO (name,is_business,is_individual,is_employee," +
							"contact_ID,address1,address2,city,state,province,postal_code," +
							"country,contact_name,email,phone,mobile,fax,timestamp_entered," +
							"timestamp_modified,status) " +
					"VALUES ('" 
							+ replaceEscape(c.getName()) + "',"
							+ "'" + business + "',"
							+ "'" + individual + "',"
							+ "'" + employee + "',"
							+ "'" + replaceEscape(c.getContactID()) + "',"
							+ "'" + replaceEscape(c.getAddressOne()) + "',"
							+ "'" + replaceEscape(c.getAddressTwo()) + "',"
							+ "'" + replaceEscape(c.getCity()) + "',"
							+ "'" + replaceEscape(c.getState()) + "',"
							+ "'" + replaceEscape(c.getProvince()) + "',"
							+ "'" + replaceEscape(c.getPostalCode()) + "',"
							+ "'" + replaceEscape(c.getCountry()) + "',"
							+ "'" + replaceEscape(c.getContactName()) + "',"
							+ "'" + replaceEscape(c.getEmail()) + "',"
							+ "'" + replaceEscape(c.getPhone()) + "',"
							+ "'" + replaceEscape(c.getMobile()) + "',"
							+ "'" + replaceEscape(c.getFax()) + "',"
							+ "TO_DATE('" + c.getDateTimeEntered() + "','YYYY-MM-DD HH:MI:SS'),"
							+ "TO_DATE('" + c.getDateTimeModified() + "','YYYY-MM-DD HH:MI:SS'),"
							+ "'" + replaceEscape(c.getStatus()) + "')";
		 	try
			{
				stmt = con.createStatement();
				stmt.executeQuery(query);
			}
			catch (final SQLException e)
			{
				JOptionPane.showMessageDialog(null, "SQL EXCEPTION: " + e.toString());
			}
			finally
			{
				if (stmt != null)
				{
					stmt.close();
				}
			}
		}
	
	//Removes escape characters to prevent sql errors and injections
	private static String replaceEscape(final String in){
		if(in.contains("'")){
			String newStr = in;
			return newStr.replaceAll("'", "''");
		}
		return in;
	}
}