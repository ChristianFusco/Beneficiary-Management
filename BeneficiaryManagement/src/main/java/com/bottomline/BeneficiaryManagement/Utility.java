package com.bottomline.BeneficiaryManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.ibm.icu.text.SimpleDateFormat;

public class Utility {
	//The constant that decides whether or not a dateTime object contains a date or a time
	//BECAUSE HAVING THEM GIVE ME A CONSTANT WOULD BE TOO EASY, WOULDNT IT
	static final int SWT_DATE_STYLE = 33620000;
	//static final int SWT_TIME_STYLE = 33620096;
	private static Element root;
	
	/**
	 * Converts current data located on a series of widgets into a contact object.
	 * 
	 * See ManagementWindow.fillRows() and SqlServices.lookUpContact()
	 * 
	 * @param mode - a string value that determines whether or not to save the contact.
	 * Should be "insert"
	 * @param widgets - a list of attributes in contact form 
	 */
	static void convertToContact(final String mode, final List<Widget> widgets){
		List<String> attributes = new ArrayList<>();
		boolean dateTimeReady = false;
		DateTime dt1 = null;
		DateTime dt2 = null;
		for(int i = 0; i < widgets.size(); i++){
			if(widgets.get(i) instanceof DateTime){
				if(dt1 == null){
					dt1 = (DateTime) widgets.get(i);
				}
				else{
					dt2 = (DateTime) widgets.get(i);
					dateTimeReady = true;
				}
				//unless a date and a time object have been added, don't add an attribute
				if(dateTimeReady){
					attributes.add(dateTimeToString(dt1, dt2));
					dateTimeReady = false;
					dt1 = null;
				}
			}
			else{
				attributes.add(widgetToString(widgets.get(i)));
			}
		}
		Contact c = new Contact(attributes);
		if("".equals(c.getName())){
			JOptionPane.showMessageDialog(null, "A name must be entered.");
			return;
		}
		if (!c.isBusiness() && !c.isEmployee() && !c.isIndividual()){
			JOptionPane.showMessageDialog(null, "A contact type must be selected.");
			return;
		}
		try {
			SqlServices.lookUpContact(mode, c);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQL EXCEPTION: " + e.toString());
		}
		//reloads other ui
		if(!ManagementWindow.shell.isDisposed())
			ManagementWindow.fillRows();
	}
	
	private static String dateTimeToString(DateTime dt1, DateTime dt2){
		Calendar instance = Calendar.getInstance();
		//checks to see if order was messed up
		if(dt1.getStyle() == dt2.getStyle()){
			return null;
		}
		if(dt1.getStyle() == SWT_DATE_STYLE){
			instance.set(Calendar.YEAR, dt1.getYear());
			instance.set(Calendar.MONTH, dt1.getMonth());
			instance.set(Calendar.DAY_OF_MONTH, dt1.getDay());
			instance.set(Calendar.HOUR, dt2.getHours());
			instance.set(Calendar.MINUTE, dt2.getMinutes());
			instance.set(Calendar.SECOND, dt2.getSeconds());
		}
		else{
			instance.set(Calendar.YEAR, dt2.getYear());
			instance.set(Calendar.MONTH, dt2.getMonth());
			instance.set(Calendar.DAY_OF_MONTH, dt2.getDay());
			instance.set(Calendar.HOUR, dt1.getHours());
			instance.set(Calendar.MINUTE, dt1.getMinutes());
			instance.set(Calendar.SECOND, dt1.getSeconds());
		}
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(instance.getTime());
	}
	
	/** 
	 * Fills the rows contained in table with contact information.
	 * 
	 * Sets the selection to the first element of the table.  This is to prevent
	 * nulls when deleting or copying a contact.
	 * @param table - the table that contains each contact object, broken up into strings
	 */
	public static void fillRows(Table table){
		//check to see if window is open
		if(ManagementWindow.shell.isDisposed())
			return;
		List<Contact> contacts = new ArrayList<>();
		//clears the table of previous values
		table.removeAll();
		try {
			//collects contact information
			contacts = SqlServices.lookUpContact("readall", null);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}
		for(int i = 0; i < contacts.size(); i++){
			Contact c = contacts.get(i);
			//saves each contact as a table item 
			TableItem tableItem = new TableItem(table, SWT.NONE);
			String[] list = c.getAsList().toArray(new String[0]);
			tableItem.setText(list);
		}	
		//selects the first row
		table.setSelection(0);
	}
	
	/**
	 * Collects information from an xml table based on the name of a label.
	 * 
	 * see applicationresources.xml
	 * 
	 * @param key - the name attribute of an xml label
	 * @return the value attribute of an xml label
	 */
	public static String getChildValue(String key){
		List<Element> children = root.getChildren();
		for(int i = 0; i < children.size(); i++){
			if(children.get(i).getChildText("name").equals(key)){
				return children.get(i).getChildText("value");
			}
		}
		return "??" + key + "??";
	}
	
	/**
	 * Collects an array of information from an xml table based on the name of some labels
	 * 
	 * uses .contains() to find all labels with a specific string in their name and returns their values
	 * See applicationresources.xml
	 * 
	 * @param key - the name attribute of an xml label
	 * @return an array of value attributes of some xml labels
	 */
	static List<String> getListOfCodes(String key){
		List<Element> children = root.getChildren();
		List<String> codes = new ArrayList<>();
		for(int i = 0; i < children.size(); i++){
			if(children.get(i).getChildText("name").contains(key)){
				codes.add(children.get(i).getChildText("value"));
			}
		}
		return codes;
	}
	
	/** 
	 * Collects all the information from applicationresources.xml and saves it to root.
	 */
	public static void openXML(){
		SAXBuilder builder = new SAXBuilder();
		Document doc;
		try {
			doc = builder.build(SqlServices.class.getClassLoader().getResource("applicationresources.xml").toString());
			root = doc.getRootElement();
		} catch (JDOMException e) {
			JOptionPane.showMessageDialog(null, e.toString());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}
	}
	
	/**
	 * Converts a table row into an equivalent contact.
	 * 
	 * @param items each row on the table
	 * @param table - the table that contains each contact object, broken up into strings
	 * @return a single contact
	 */
	static Contact rowToContact(TableItem[] items, Table table){
		if(items.length != 1)
			return null;//silly error checking
		//table items are returned in an array.
		//this should ALWAYS be size 1
		List<String> attributes = new ArrayList<>();
		TableItem item = items[0];
		for(int i = 0; i < table.getColumnCount(); i++){
			attributes.add(item.getText(i));
		}
		return new Contact(attributes);
	}
	
	/**
	 * Saves current table to an excel file.
	 * 
	 * The order is the same as in the table object.  See Contact(List<String>)
	 * http://stackoverflow.com/questions/1516144/how-to-read-and-write-excel-file-in-java
	 * @param table - values to be saved
	 */
	public static void saveToExcel(Table table){
		try {
			//creates and sets data for file chooser
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel/XLS Files", "xls");
			fileChooser.setFileFilter(filter);
			int result = fileChooser.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				//adds .xls extension if it doesn't already exist
				File selectedFile;
				if(!fileChooser.getSelectedFile().toString().contains(".xls")){
					selectedFile = new File(fileChooser.getSelectedFile() + ".xls");
				}
				else{
					selectedFile = fileChooser.getSelectedFile();
				}
				//create table and write data
			    TableItem[] t = table.getItems();
				Workbook wb = new HSSFWorkbook();
			    Sheet sheet = wb.createSheet("new sheet");
				for(int i = 0; i < table.getItemCount(); i++){
					Row row = sheet.createRow((short)i);
					for(int j = 0; j < table.getColumnCount(); j++){
						row.createCell(j).setCellValue(t[i].getText(j));
					}
				}
				FileOutputStream fileOut = new FileOutputStream(selectedFile);
				wb.write(fileOut);
				wb.close();
				fileOut.close();
				JOptionPane.showMessageDialog(null, getChildValue("savedExcel"));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads contacts from an excel file, adds to the sql database, calls fillRows().
	 * 
	 * The order is the same as in the table object.  See Contact(List<String>)
	 * http://stackoverflow.com/questions/1516144/how-to-read-and-write-excel-file-in-java
	 * @param table - kept to pass into fillRows
	 */
	public static void loadFromExcel(Table table){
		try {
			//create file chooser
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			//filter out any non .xls files
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel/XLS Files", "xls");
			fileChooser.setFileFilter(filter);
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				//get file
			    File selectedFile = fileChooser.getSelectedFile();
			    //create worksheet
			    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(selectedFile));
				HSSFWorkbook wb = new HSSFWorkbook(fs);
			    Sheet sheet = wb.getSheetAt(0);
			    int rows = sheet.getPhysicalNumberOfRows();
				for(int i = 0; i < rows; i++){
					//collect information and insert, row by row
					Row row = sheet.getRow(i);
					if(row != null){
						List<String> attributes = new ArrayList<>();
						int columns = row.getPhysicalNumberOfCells();
						for(int j = 0; j < columns; j++){
							attributes.add(row.getCell(j).getStringCellValue());
						}
						Contact c = new Contact(attributes);
						SqlServices.lookUpContact("insert", c);
					}
				}
				wb.close();
				fillRows(table);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Filters rows from the selection based on the values of combo and txtSearch
	 * 
	 * Case doesn't matter.
	 * Uses contains() method.
	 * @param combo - text contains the field to search
	 * @param text - contains the value to search for
	 * @param table - the table that contains each contact object, broken up into strings
	 */
	public static void search(Combo combo, Text text, Table table){
		int index = combo.getSelectionIndex();
		TableItem[] t = table.getItems();
		String currentText = text.getText().toLowerCase();
		for(int i = table.getItemCount() - 1; i >= 0; i--){
			String rowText = t[i].getText(index).toLowerCase();
			if(!rowText.contains(currentText)){
				table.remove(i);
			}
		}
		if(table.getItemCount() == 0){
			JOptionPane.showMessageDialog(null, getChildValue("err.empty"));
			fillRows(table);
		}
	}
	
	/**
	 * Fills screen with data from some contact.
	 * 
	 * On new contacts, this is empty.
	 * On new templates, only the name is empty.
	 * When editing an existing contact, all values should be filled.
	 * 
	 * @param c the contact that the user is manipulating
	 * @param widgets - each of the editable fields located in EditContact
	 */
	static void updateScreen(Contact c, List<Widget> widgets){
		List<String> contactInfo = c.getAsList();
		int j = 0; //counter for contactInfo.  widgets uses i
		for(int i = 0; i < widgets.size() && j < contactInfo.size(); i++, j++){
			//converts a datetime to a useable value
			if(widgets.get(i) instanceof DateTime){
				SimpleDateFormat f;
				Date theDate;
				Calendar cal;
				try {
					f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					theDate = f.parse(contactInfo.get(j));
					cal = Calendar.getInstance();
					cal.setTime(theDate);
					//current widget converted to time
					((DateTime) widgets.get(i)).setHours(cal.get(Calendar.HOUR));
					((DateTime) widgets.get(i)).setMinutes(cal.get(Calendar.MINUTE));
					((DateTime) widgets.get(i)).setSeconds(cal.get(Calendar.SECOND));
					//next widget converted to date
					//skips to next widget
					i++;
					((DateTime) widgets.get(i)).setDay(cal.get(Calendar.DAY_OF_MONTH));
					((DateTime) widgets.get(i)).setYear(cal.get(Calendar.YEAR));
					((DateTime) widgets.get(i)).setMonth(cal.get(Calendar.MONTH));
					
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, "Parse error: " + e.toString());
				}
			}
			else if(widgets.get(i) instanceof Button){
				((Button) widgets.get(i)).setSelection(contactInfo.get(i).equals("True") ? true : false);
			}
			else if(widgets.get(i) instanceof Combo){
				Widget w = widgets.get(i);
				((Combo) w).setText(contactInfo.get(j));
			}
			else
			{
				Widget w = widgets.get(i);
				((Text) w).setText(contactInfo.get(j));
			}
		}
	}
	
	private static String widgetToString(Widget w){
		if(w instanceof Button){
			return ((Button) w).getSelection() ? "True" : "False";
		}
		else if(w instanceof Combo){
			 return ((Combo) w).getText();				
		}
		else{
			return ((Text) w).getText();				
		}
	}
}
