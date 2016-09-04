package com.bottomline.BeneficiaryManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;


public class ExcelManagement {
	
	private static String fileName;
	private static List<Contact> contacts;
	
	public static String getFileName(){
		return fileName;
	}
	
	public static List<Contact> getContacts(){
		if(contacts == null){
			contacts = new ArrayList<>();
		}
		return contacts;
	}
	
	/**
	 * Sets the file name and current contacts list.
	 * 
	 * @return true if the user selected a file
	 */
	public static boolean setFileName(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		//filter out any non .xls files
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel/XLS Files", "xls");
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			//get file
		    fileName = fileChooser.getSelectedFile().toString();
		    contacts = readAllContacts();
		    return true;
		}
		return false;
	}
	
	/**
	 * Removes the contact and returns true if it was removed, false if it could not be found.
	 * equals is overloaded for the object contact
	 * @param c - contact to delete
	 * @return true if contact was deleted.
	 */
	public static boolean removeContact(Contact c){
		for(int i = 0; i < contacts.size(); i++){
			if(contacts.get(i).equals(c)){
				contacts.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public static void addContact(Contact c){
		contacts.add(c);
	}

	private static List<Contact> readAllContacts(){
		List<Contact> contacts = new ArrayList<>();
		try {
	 		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));
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
					contacts.add(c);
				}
			}
			wb.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contacts;
	}
	
	public static void saveList(){
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
				Workbook wb = new HSSFWorkbook();
			    Sheet sheet = wb.createSheet("new sheet");
				for(int i = 0; i < contacts.size(); i++){
					List<String> attributes = contacts.get(i).getAsList();
					Row row = sheet.createRow((short)i);
					for(int j = 0; j < attributes.size(); j++){
						row.createCell(j).setCellValue(attributes.get(j));
					}
				}
				FileOutputStream fileOut = new FileOutputStream(selectedFile);
				wb.write(fileOut);
				wb.close();
				fileOut.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}