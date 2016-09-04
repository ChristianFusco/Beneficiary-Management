package com.bottomline.BeneficiaryManagement;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;

public class ManagementWindow {
	
	static final int BTN_DISTANCE_FROM_EDGE = -250;
	static final int TABLE_DISTANCE_FROM_BOTTOM = -100;
	static final int TABLE_DISTANCE_FROM_BTN = -20;
	
	static final int SIDE_BUTTON_WIDTH = 200;
	static final int SIDE_BUTTON_HEIGHT = 50;
	
	static final int DISPLAY_PERCENT_100 = 96;
	static final int DISPLAY_PERCENT_125 = 120;
	static final int DISPLAY_PERCENT_150 = 144;
	
	static final int FONT_SIZE_100 = 14;
	static final int FONT_SIZE_125 = 11;
	static final int FONT_SIZE_150 = 9;
	static final int FONT_SIZE_DEFAULT = 9;

	public static Shell shell;
	private static Table table;	
	private static Text txtSearch;
	private static Combo combo;
	private static Button btnInsert;
	private static Button btnCopyAsTemplate;
	private static Button btnUpdate;
	private static Button btnSave;
	private static Button btnRemove;
	private static Button btnLoad;
	
	private static List<Button> rightSideControls;
	/*
	 * The open function in this file has been modified to work with the helper functions in Utility.java
	 */
	
	//helper function
	public static void fillRows(){
		Utility.fillRows(table);
	}
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ManagementWindow window = new ManagementWindow();
			window.open();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		Utility.openXML();
		createContents();
		Utility.fillRows(table);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * 
	 * Button clicks and table order located on the bottom.
	 * Combo must match EditContact's widgets list, and functions in Contact.java
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(1280, 820);
		
		int fontSize;
		switch(Display.getCurrent().getDPI().x){
		case DISPLAY_PERCENT_100:
			fontSize = FONT_SIZE_100;
			break;
		case DISPLAY_PERCENT_125:
			fontSize = FONT_SIZE_125;
			break;
		case DISPLAY_PERCENT_150:
			fontSize = FONT_SIZE_150;
			break;
		default:
			fontSize = FONT_SIZE_DEFAULT;
		}
		
		//Buttons	
		btnInsert = new Button(shell, SWT.NONE);
		btnInsert.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		btnInsert.setBounds(753, 42, SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
		btnInsert.setText(Utility.getChildValue("btn.insert"));
		
		btnRemove = new Button(shell, SWT.NONE);
		btnRemove.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		btnRemove.setBounds(753, 201, SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
		btnRemove.setText(Utility.getChildValue("btn.remove"));
		
		btnUpdate = new Button(shell, SWT.NONE);
		btnUpdate.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		btnUpdate.setBounds(753, 148, SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
		btnUpdate.setText(Utility.getChildValue("btn.update"));
		
		btnCopyAsTemplate = new Button(shell, SWT.NONE);
		btnCopyAsTemplate.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		btnCopyAsTemplate.setText(Utility.getChildValue("btn.copy"));
		btnCopyAsTemplate.setBounds(753, 95, SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
		
		btnSave = new Button(shell, SWT.NONE);
		btnSave.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		btnSave.setBounds(new Rectangle(753, 310, SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT));
		btnSave.setText(Utility.getChildValue("btn.saveExcel"));
		
		btnLoad = new Button(shell, SWT.NONE);
		btnLoad.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		btnLoad.setText(Utility.getChildValue("btn.loadExcel"));
		btnLoad.setBounds(new Rectangle(753, 254, SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT));
		
		Button btnSearch = new Button(shell, SWT.NONE);
		btnSearch.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		btnSearch.setBounds(540, 8, 90, 35);
		btnSearch.setText(Utility.getChildValue("btn.search"));
		
		Button btnRefresh = new Button(shell, SWT.NONE);
		btnRefresh.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		btnRefresh.setText(Utility.getChildValue("btn.refresh"));
		btnRefresh.setBounds(636, 8, 90, 35);
		
		//Table and columns
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		table.setBounds(10, 49, 716, 556);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn colName = new TableColumn(table, SWT.NONE);
		colName.setWidth(123);
		colName.setText(Utility.getChildValue("name"));
		
		TableColumn colIsBusiness = new TableColumn(table, SWT.NONE);
		colIsBusiness.setWidth(84);
		colIsBusiness.setText(Utility.getChildValue("column.business"));
		
		TableColumn colIsEmployee = new TableColumn(table, SWT.NONE);
		colIsEmployee.setWidth(100);
		colIsEmployee.setText(Utility.getChildValue("column.employee"));
		
		TableColumn colIsIndividual = new TableColumn(table, SWT.NONE);
		colIsIndividual.setWidth(100);
		colIsIndividual.setText(Utility.getChildValue("column.individual"));
		
		TableColumn colContactID = new TableColumn(table, SWT.NONE);
		colContactID.setWidth(120);
		colContactID.setText(Utility.getChildValue("contactID"));
		
		TableColumn colAddress = new TableColumn(table, SWT.NONE);
		colAddress.setWidth(108);
		colAddress.setText(Utility.getChildValue("address1"));
		
		TableColumn colAddress2 = new TableColumn(table, SWT.NONE);
		colAddress2.setText(Utility.getChildValue("address2"));
		colAddress2.setWidth(112);
		
		TableColumn colCity = new TableColumn(table, SWT.NONE);
		colCity.setWidth(100);
		colCity.setText(Utility.getChildValue("city"));
		
		TableColumn colState = new TableColumn(table, SWT.NONE);
		colState.setWidth(57);
		colState.setText(Utility.getChildValue("state"));
		
		TableColumn colCountry = new TableColumn(table, SWT.NONE);
		colCountry.setWidth(136);
		colCountry.setText(Utility.getChildValue("country"));
		
		TableColumn colProvince = new TableColumn(table, SWT.NONE);
		colProvince.setWidth(110);
		colProvince.setText(Utility.getChildValue("province"));
		
		TableColumn colPostalCode = new TableColumn(table, SWT.NONE);
		colPostalCode.setWidth(100);
		colPostalCode.setText(Utility.getChildValue("postalCode"));
		
		TableColumn colContactName = new TableColumn(table, SWT.NONE);
		colContactName.setWidth(117);
		colContactName.setText(Utility.getChildValue("contact.name"));
		
		TableColumn colEmail = new TableColumn(table, SWT.NONE);
		colEmail.setWidth(203);
		colEmail.setText(Utility.getChildValue("contact.email"));
		
		TableColumn colPhone = new TableColumn(table, SWT.NONE);
		colPhone.setWidth(169);
		colPhone.setText(Utility.getChildValue("contact.phone"));
		
		TableColumn colMobile = new TableColumn(table, SWT.NONE);
		colMobile.setWidth(208);
		colMobile.setText(Utility.getChildValue("contact.mobile"));
		
		TableColumn colFax = new TableColumn(table, SWT.NONE);
		colFax.setWidth(194);
		colFax.setText(Utility.getChildValue("contact.fax"));
		
		TableColumn colEntered = new TableColumn(table, SWT.NONE);
		colEntered.setWidth(222);
		colEntered.setText(Utility.getChildValue("entered"));
		
		TableColumn colModified = new TableColumn(table, SWT.NONE);
		colModified.setWidth(221);
		colModified.setText(Utility.getChildValue("modified"));
		
		TableColumn colStatus = new TableColumn(table, SWT.NONE);
		colStatus.setWidth(100);
		colStatus.setText(Utility.getChildValue("status"));
		
		//text boxes
		
		txtSearch = new Text(shell, SWT.BORDER);
		txtSearch.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		txtSearch.setBounds(299, 8, 235, 35);
		
		//combo items
		
		//Any changes to the order of this field MUST be reflected in Contact.java's Contact(List<String>) and getAsList()
		//Also must match EditContact's widget list.
		combo = new Combo(shell, SWT.NONE);
		combo.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		combo.setItems(new String[] {Utility.getChildValue("name"),
				Utility.getChildValue("column.business"),
				Utility.getChildValue("column.employee"),
				Utility.getChildValue("column.individual"),
				Utility.getChildValue("contactID"),
				Utility.getChildValue("address1"),
				Utility.getChildValue("address2"),
				Utility.getChildValue("city"),
				Utility.getChildValue("state"),
				Utility.getChildValue("country"),
				Utility.getChildValue("province"),
				Utility.getChildValue("postalCode"),
				Utility.getChildValue("contact.name"),
				Utility.getChildValue("contact.email"),
				Utility.getChildValue("contact.phone"),
				Utility.getChildValue("contact.mobile"),
				Utility.getChildValue("contact.fax"),
				Utility.getChildValue("entered"),
				Utility.getChildValue("modified"),
				Utility.getChildValue("status")});
		combo.setBounds(10, 8, 283, 36);
		combo.setText(Utility.getChildValue("combo.prompt"));
		
		//button click events
		btnInsert.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EditContact m = new EditContact();
				m.open();
				Utility.fillRows(table);
			}
		});
		
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Contact c = Utility.rowToContact(table.getSelection(), table);
				if(c == null){
					JOptionPane.showMessageDialog(null, Utility.getChildValue("err.row"));
					return;
				}
				ExcelManagement.removeContact(c);
				Utility.fillRows(table);
			}
		});
		
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Contact c = Utility.rowToContact(table.getSelection(), table);
				if(c == null){
					JOptionPane.showMessageDialog(null, Utility.getChildValue("err.row"));
					return;
				}
				EditContact m = new EditContact();
				m.open(c);
			}
		});
		
		btnCopyAsTemplate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Contact c = Utility.rowToContact(table.getSelection(), table);
				if(c == null){
					JOptionPane.showMessageDialog(null, Utility.getChildValue("err.row"));
					return;
				}
				c.setName("");
				EditContact m = new EditContact();
				m.open(c);
				Utility.fillRows(table);
			}
		});
		
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Utility.search(combo, txtSearch, table);
			}
		});
		
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Utility.fillRows(table);
			}
		});
		
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExcelManagement.saveList();
				Utility.fillRows(table);
			}
		});
		
		btnLoad.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExcelManagement.setFileName();
				Utility.fillRows(table);
			}
		});

		rightSideControls = new ArrayList<>();
		rightSideControls.add(btnInsert);
		rightSideControls.add(btnCopyAsTemplate);
		rightSideControls.add(btnUpdate);
		rightSideControls.add(btnRemove);
		rightSideControls.add(btnSave);
		rightSideControls.add(btnLoad);
		
		shell.addListener (SWT.Resize,  new Listener () {
				@Override
			    public void handleEvent (Event e) {
			    	Point shellSize = shell.getSize();
			    	//get the top right button
			    	Point buttonLocation = rightSideControls.get(0).getLocation();
			    	//find the difference in distance between the two
			    	int diff = rightSideControls.get(1).getLocation().y - rightSideControls.get(0).getLocation().y;
			    	//replace the controls based on that difference
			    	for(int i = 0; i < rightSideControls.size(); i++){
			    		rightSideControls.get(i).setLocation(
			    				new Point(shellSize.x + BTN_DISTANCE_FROM_EDGE, buttonLocation.y + diff * i));
			    	}
			    	//resize the table based on shell and the buttons
			    	buttonLocation = rightSideControls.get(0).getLocation();
			    	table.setSize(buttonLocation.x + TABLE_DISTANCE_FROM_BTN, shellSize.y + TABLE_DISTANCE_FROM_BOTTOM);
			    }
		});
	}
}
