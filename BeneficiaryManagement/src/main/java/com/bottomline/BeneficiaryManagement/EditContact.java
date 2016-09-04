package com.bottomline.BeneficiaryManagement;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.wb.swt.SWTResourceManager;

public class EditContact {
	//see create contents for more info on widgets list
	private List<Widget> widgets;
	protected Shell shell;
	private Button btnEmployee;
	private Button btnIndividual;
	private Button btnBusiness;
	
	static final int DISPLAY_PERCENT_100 = 96;
	static final int DISPLAY_PERCENT_125 = 120;
	static final int DISPLAY_PERCENT_150 = 144;
	
	static final int FONT_SIZE_100 = 14;
	static final int FONT_SIZE_125 = 11;
	static final int FONT_SIZE_150 = 9;
	static final int FONT_SIZE_DEFAULT = 9;

	
	/*
	 * The open function in this file has been modified to work with the helper functions in Utility.java
	 */
	
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	/**
	 * Open the Window.
	 * User generated.
	 * @param c some contact that will be displayed on the screen.
	 */
	public void open(Contact c) {		
		Display display = Display.getDefault();
		createContents();
		Utility.updateScreen(c, widgets);
		shell.open();
		shell.layout();
		if(btnBusiness.getSelection()){
			btnEmployee.setEnabled(false);
			btnIndividual.setEnabled(false);
		}
		else{
			btnBusiness.setEnabled(false);
		}
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	/**
	 * Create contents of the window.
	 * 
	 * Button click events are located at the bottom.
	 * Any changes to the UI must be reflected in the widgets list.
	 * Order of the widgets list MUST match ManagementWindow.createContents() in the combo declaration,
	 * Contact.Contact(List<String>), and Contact.getAsList().
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shell.setSize(1280, 820);
		shell.setLayout(new FormLayout());
		
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
				
		//generic UI stuff
				
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblName = new FormData();
		lblName.setLayoutData(fd_lblName);
		lblName.setText(Utility.getChildValue("prompt.name"));
		
		Text txtName = new Text(shell, SWT.BORDER);
		fd_lblName.bottom = new FormAttachment(txtName, -6);
		txtName.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtName = new FormData();
		fd_txtName.left = new FormAttachment(0, 156);
		txtName.setLayoutData(fd_txtName);
		
		Label lblContactID = new Label(shell, SWT.NONE);
		fd_txtName.bottom = new FormAttachment(lblContactID);
		lblContactID.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblContactID = new FormData();
		fd_lblContactID.top = new FormAttachment(0, 162);
		fd_lblContactID.left = new FormAttachment(lblName, 0, SWT.LEFT);
		lblContactID.setLayoutData(fd_lblContactID);
		lblContactID.setText(Utility.getChildValue("contactID"));
		
		Text txtContactID = new Text(shell, SWT.BORDER);
		txtContactID.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtContactID = new FormData();
		fd_txtContactID.right = new FormAttachment(txtName, 0, SWT.RIGHT);
		fd_txtContactID.top = new FormAttachment(0, 193);
		txtContactID.setLayoutData(fd_txtContactID);
		
		Label txtContactType = new Label(shell, SWT.NONE);
		txtContactType.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtContactType = new FormData();
		txtContactType.setLayoutData(fd_txtContactType);
		txtContactType.setText(Utility.getChildValue("prompt.contactType"));
		
		btnBusiness = new Button(shell, SWT.CHECK);
		fd_lblName.left = new FormAttachment(btnBusiness, 0, SWT.LEFT);
		fd_txtContactType.bottom = new FormAttachment(100, -749);
		btnBusiness.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_btnBusiness = new FormData();
		fd_btnBusiness.left = new FormAttachment(0, 118);
		fd_btnBusiness.top = new FormAttachment(txtContactType, 6);
		btnBusiness.setLayoutData(fd_btnBusiness);
		btnBusiness.setText(Utility.getChildValue("business"));
		
		btnEmployee = new Button(shell, SWT.CHECK);
		btnEmployee.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_btnEmployee = new FormData();
		fd_btnEmployee.top = new FormAttachment(btnBusiness, 0, SWT.TOP);
		fd_btnEmployee.left = new FormAttachment(btnBusiness, 6);
		btnEmployee.setLayoutData(fd_btnEmployee);
		btnEmployee.setText(Utility.getChildValue("employee"));
		
		btnIndividual = new Button(shell, SWT.CHECK);
		btnIndividual.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		
		FormData fd_btnIndividual = new FormData();
		fd_btnIndividual.top = new FormAttachment(btnBusiness, 0, SWT.TOP);
		fd_btnIndividual.left = new FormAttachment(btnEmployee, 6);
		btnIndividual.setLayoutData(fd_btnIndividual);
		btnIndividual.setText(Utility.getChildValue("individual"));
		
		Combo comboStatus = new Combo(shell, SWT.NONE);
		comboStatus.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		comboStatus.setItems(new String[] {Utility.getChildValue("combo.approved"),
				Utility.getChildValue("combo.deleted"), 
				Utility.getChildValue("combo.entered"),
				Utility.getChildValue("combo.null")});
		FormData fd_comboStatus = new FormData();
		comboStatus.setLayoutData(fd_comboStatus);
		comboStatus.setText(Utility.getChildValue("status"));
		
		Label lblDateEntered = new Label(shell, SWT.NONE);
		lblDateEntered.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblDateEntered = new FormData();
		fd_lblDateEntered.top = new FormAttachment(lblName, 0, SWT.TOP);
		lblDateEntered.setLayoutData(fd_lblDateEntered);
		lblDateEntered.setText(Utility.getChildValue("entered"));
		
		DateTime dateEntered = new DateTime(shell, SWT.BORDER);
		fd_txtName.right = new FormAttachment(dateEntered, -285);
		dateEntered.setEnabled(false);
		dateEntered.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_dateEntered = new FormData();
		fd_dateEntered.top = new FormAttachment(txtName, 0, SWT.TOP);
		fd_dateEntered.left = new FormAttachment(comboStatus, 0, SWT.LEFT);
		dateEntered.setLayoutData(fd_dateEntered);
		
		DateTime timeEntered = new DateTime(shell, SWT.BORDER | SWT.TIME);
		timeEntered.setEnabled(false);
		timeEntered.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_timeEntered = new FormData();
		fd_timeEntered.top = new FormAttachment(txtName, 0, SWT.TOP);
		fd_timeEntered.left = new FormAttachment(dateEntered, 6);
		timeEntered.setLayoutData(fd_timeEntered);
		
		Label lblDateModified = new Label(shell, SWT.NONE);
		fd_lblDateEntered.left = new FormAttachment(lblDateModified, 0, SWT.LEFT);
		lblDateModified.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblDateModified = new FormData();
		fd_lblDateModified.top = new FormAttachment(lblContactID, 0, SWT.TOP);
		lblDateModified.setLayoutData(fd_lblDateModified);
		lblDateModified.setText(Utility.getChildValue("modified"));
		
		DateTime dateModified = new DateTime(shell, SWT.BORDER);
		dateModified.setEnabled(false);
		dateModified.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_dateModified = new FormData();
		fd_dateModified.bottom = new FormAttachment(txtContactID, 0, SWT.BOTTOM);
		fd_dateModified.left = new FormAttachment(comboStatus, 0, SWT.LEFT);
		dateModified.setLayoutData(fd_dateModified);
		
		DateTime timeModified = new DateTime(shell, SWT.BORDER | SWT.TIME);
		timeModified.setEnabled(false);
		timeModified.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_timeModified = new FormData();
		fd_timeModified.bottom = new FormAttachment(txtContactID, 0, SWT.BOTTOM);
		fd_timeModified.left = new FormAttachment(timeEntered, 0, SWT.LEFT);
		timeModified.setLayoutData(fd_timeModified);
		
		Label lblAddressInfo = new Label(shell, SWT.NONE);
		fd_txtContactType.left = new FormAttachment(lblAddressInfo, 0, SWT.LEFT);
		lblAddressInfo.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblAddressInfo = new FormData();
		lblAddressInfo.setLayoutData(fd_lblAddressInfo);
		lblAddressInfo.setText(Utility.getChildValue("prompt.address"));
		
		Label lblAddress1 = new Label(shell, SWT.NONE);
		fd_lblAddressInfo.bottom = new FormAttachment(lblAddress1, -6);
		fd_lblAddressInfo.left = new FormAttachment(lblAddress1, -50, SWT.LEFT);
		lblAddress1.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblAddress1 = new FormData();
		fd_lblAddress1.left = new FormAttachment(0, 118);
		lblAddress1.setLayoutData(fd_lblAddress1);
		lblAddress1.setText(Utility.getChildValue("address1"));
		
		Text txtAddress1 = new Text(shell, SWT.BORDER);
		fd_txtContactID.left = new FormAttachment(txtAddress1, 0, SWT.LEFT);
		fd_lblAddress1.bottom = new FormAttachment(txtAddress1, -6);
		txtAddress1.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtAddress1 = new FormData();
		fd_txtAddress1.right = new FormAttachment(100, -761);
		txtAddress1.setLayoutData(fd_txtAddress1);
		
		Label lblAddress2 = new Label(shell, SWT.NONE);
		fd_lblAddress1.right = new FormAttachment(lblAddress2, 0, SWT.RIGHT);
		fd_txtAddress1.bottom = new FormAttachment(lblAddress2, -6);
		lblAddress2.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblAddress2 = new FormData();
		fd_lblAddress2.left = new FormAttachment(0, 118);
		lblAddress2.setLayoutData(fd_lblAddress2);
		lblAddress2.setText(Utility.getChildValue("address2"));
		
		Text txtAddress2 = new Text(shell, SWT.BORDER);
		fd_txtAddress1.left = new FormAttachment(txtAddress2, 0, SWT.LEFT);
		fd_lblAddress2.bottom = new FormAttachment(txtAddress2, -6);
		txtAddress2.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtAddress2 = new FormData();
		fd_txtAddress2.left = new FormAttachment(0, 156);
		fd_txtAddress2.right = new FormAttachment(100, -761);
		txtAddress2.setLayoutData(fd_txtAddress2);
		
		Label lblCity = new Label(shell, SWT.NONE);
		fd_txtAddress2.bottom = new FormAttachment(lblCity, -6);
		lblCity.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblCity = new FormData();
		fd_lblCity.left = new FormAttachment(0, 118);
		lblCity.setLayoutData(fd_lblCity);
		lblCity.setText(Utility.getChildValue("city"));
		
		Text txtCity = new Text(shell, SWT.BORDER);
		fd_lblCity.bottom = new FormAttachment(txtCity, -6);
		txtCity.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtCity = new FormData();
		fd_txtCity.left = new FormAttachment(0, 156);
		fd_txtCity.right = new FormAttachment(100, -761);
		txtCity.setLayoutData(fd_txtCity);
		
		Label lblState = new Label(shell, SWT.NONE);
		fd_txtCity.bottom = new FormAttachment(lblState, -6);
		lblState.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblState = new FormData();
		fd_lblState.left = new FormAttachment(0, 118);
		lblState.setLayoutData(fd_lblState);
		lblState.setText(Utility.getChildValue("state"));
		
		Text txtState = new Text(shell, SWT.BORDER);
		fd_lblState.bottom = new FormAttachment(txtState, -6);
		txtState.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtState = new FormData();
		fd_txtState.left = new FormAttachment(0, 156);
		fd_txtState.right = new FormAttachment(100, -761);
		txtState.setLayoutData(fd_txtState);
		
		Label lblProvince = new Label(shell, SWT.NONE);
		fd_txtState.bottom = new FormAttachment(lblProvince, -6);
		lblProvince.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblProvince = new FormData();
		fd_lblProvince.left = new FormAttachment(0, 118);
		lblProvince.setLayoutData(fd_lblProvince);
		lblProvince.setText(Utility.getChildValue("province"));
		
		Text txtProvince = new Text(shell, SWT.BORDER);
		fd_lblProvince.bottom = new FormAttachment(txtProvince, -6);
		txtProvince.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtProvince = new FormData();
		fd_txtProvince.left = new FormAttachment(0, 156);
		fd_txtProvince.right = new FormAttachment(100, -761);
		txtProvince.setLayoutData(fd_txtProvince);
		
		Label lblPostalCode = new Label(shell, SWT.NONE);
		fd_txtProvince.bottom = new FormAttachment(lblPostalCode, -6);
		lblPostalCode.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblPostalCode = new FormData();
		fd_lblPostalCode.left = new FormAttachment(0, 118);
		lblPostalCode.setLayoutData(fd_lblPostalCode);
		lblPostalCode.setText(Utility.getChildValue("postalCode"));
		
		Text txtPostalCode = new Text(shell, SWT.BORDER);
		fd_comboStatus.top = new FormAttachment(txtPostalCode, 0, SWT.TOP);
		fd_lblPostalCode.bottom = new FormAttachment(txtPostalCode, -6);
		txtPostalCode.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtPostalCode = new FormData();
		fd_txtPostalCode.right = new FormAttachment(100, -761);
		fd_txtPostalCode.left = new FormAttachment(0, 156);
		txtPostalCode.setLayoutData(fd_txtPostalCode);
		
		Label lblCountry = new Label(shell, SWT.NONE);
		fd_txtPostalCode.bottom = new FormAttachment(lblCountry, -6);
		lblCountry.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblCountry = new FormData();
		fd_lblCountry.left = new FormAttachment(0, 118);
		lblCountry.setLayoutData(fd_lblCountry);
		lblCountry.setText(Utility.getChildValue("country"));
		
		Combo txtCountry = new Combo(shell, SWT.BORDER);
		fd_lblCountry.bottom = new FormAttachment(txtCountry, -6);
		txtCountry.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_Country = new FormData();
		fd_Country.right = new FormAttachment(100, -761);
		fd_Country.left = new FormAttachment(0, 156);
		fd_Country.bottom = new FormAttachment(100, -39);
		txtCountry.setLayoutData(fd_Country);
		List<String> codes = Utility.getListOfCodes("country.");
		java.util.Collections.sort(codes);
		txtCountry.setItems(codes.toArray(new String[]{}));
		
		Label lblContactName = new Label(shell, SWT.NONE);
		fd_lblDateModified.left = new FormAttachment(lblContactName, 0, SWT.LEFT);
		lblContactName.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblContactName = new FormData();
		fd_lblContactName.top = new FormAttachment(lblAddress1, 0, SWT.TOP);
		lblContactName.setLayoutData(fd_lblContactName);
		lblContactName.setText(Utility.getChildValue("contact.name"));
		
		Text txtContactName = new Text(shell, SWT.BORDER);
		fd_comboStatus.right = new FormAttachment(txtContactName, 0, SWT.RIGHT);
		fd_comboStatus.left = new FormAttachment(txtContactName, 0, SWT.LEFT);
		fd_lblContactName.left = new FormAttachment(txtContactName, -40, SWT.LEFT);
		txtContactName.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtContactName = new FormData();
		fd_txtContactName.left = new FormAttachment(txtAddress1, 285);
		fd_txtContactName.right = new FormAttachment(100, -119);
		fd_txtContactName.top = new FormAttachment(txtAddress1, 0, SWT.TOP);
		txtContactName.setLayoutData(fd_txtContactName);
		
		Label lblEmail = new Label(shell, SWT.NONE);
		lblEmail.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblEmail = new FormData();
		fd_lblEmail.top = new FormAttachment(lblAddress2, 0, SWT.TOP);
		fd_lblEmail.left = new FormAttachment(lblContactName, 0, SWT.LEFT);
		lblEmail.setLayoutData(fd_lblEmail);
		lblEmail.setText(Utility.getChildValue("contact.email"));
		
		Text txtEmail = new Text(shell, SWT.BORDER);
		txtEmail.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtEmail = new FormData();
		fd_txtEmail.bottom = new FormAttachment(txtAddress2, 0, SWT.BOTTOM);
		fd_txtEmail.left = new FormAttachment(txtAddress2, 285);
		fd_txtEmail.right = new FormAttachment(100, -119);
		fd_txtEmail.top = new FormAttachment(txtAddress2, 0, SWT.TOP);
		txtEmail.setLayoutData(fd_txtEmail);
		
		Label lblPhone = new Label(shell, SWT.NONE);
		lblPhone.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblPhone = new FormData();
		fd_lblPhone.top = new FormAttachment(lblCity, 0, SWT.TOP);
		fd_lblPhone.left = new FormAttachment(lblContactName, 0, SWT.LEFT);
		lblPhone.setLayoutData(fd_lblPhone);
		lblPhone.setText(Utility.getChildValue("contact.phone"));
		
		Text txtPhone = new Text(shell, SWT.BORDER);
		txtPhone.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtPhone = new FormData();
		fd_txtPhone.left = new FormAttachment(txtCity, 285);
		fd_txtPhone.right = new FormAttachment(100, -119);
		fd_txtPhone.top = new FormAttachment(txtCity, 0, SWT.TOP);
		txtPhone.setLayoutData(fd_txtPhone);
		
		Label lblMobile = new Label(shell, SWT.NONE);
		lblMobile.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblMobile = new FormData();
		fd_lblMobile.bottom = new FormAttachment(lblState, 0, SWT.BOTTOM);
		fd_lblMobile.top = new FormAttachment(lblState, 0, SWT.TOP);
		fd_lblMobile.left = new FormAttachment(lblContactName, 0, SWT.LEFT);
		lblMobile.setLayoutData(fd_lblMobile);
		lblMobile.setText(Utility.getChildValue("contact.mobile"));
		
		Text txtMobile = new Text(shell, SWT.BORDER);
		txtMobile.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtMobile = new FormData();
		fd_txtMobile.bottom = new FormAttachment(txtState, 0, SWT.BOTTOM);
		fd_txtMobile.left = new FormAttachment(txtState, 285);
		fd_txtMobile.right = new FormAttachment(100, -119);
		fd_txtMobile.top = new FormAttachment(txtState, 0, SWT.TOP);
		txtMobile.setLayoutData(fd_txtMobile);
		
		Label lblFax = new Label(shell, SWT.NONE);
		lblFax.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblFax = new FormData();
		fd_lblFax.top = new FormAttachment(lblProvince, 0, SWT.TOP);
		fd_lblFax.left = new FormAttachment(lblContactName, 0, SWT.LEFT);
		lblFax.setLayoutData(fd_lblFax);
		lblFax.setText(Utility.getChildValue("contact.fax"));
		
		Text txtFax = new Text(shell, SWT.BORDER);
		txtFax.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_txtFax = new FormData();
		fd_txtFax.bottom = new FormAttachment(txtProvince, 0, SWT.BOTTOM);
		fd_txtFax.left = new FormAttachment(txtProvince, 285);
		fd_txtFax.right = new FormAttachment(100, -119);
		fd_txtFax.top = new FormAttachment(txtProvince, 0, SWT.TOP);
		txtFax.setLayoutData(fd_txtFax);
		
		Label lblContactInfo = new Label(shell, SWT.NONE);
		lblContactInfo.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_lblContactInfo = new FormData();
		fd_lblContactInfo.left = new FormAttachment(lblContactName, -50, SWT.LEFT);
		fd_lblContactInfo.top = new FormAttachment(lblAddressInfo, 0, SWT.TOP);
		lblContactInfo.setLayoutData(fd_lblContactInfo);
		lblContactInfo.setText(Utility.getChildValue("prompt.contact"));
		
		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.setFont(SWTResourceManager.getFont("Segoe UI", fontSize, SWT.NORMAL));
		FormData fd_btnSave = new FormData();
		fd_btnSave.top = new FormAttachment(txtContactType, 0, SWT.TOP);
		fd_btnSave.right = new FormAttachment(100, -10);
		fd_btnSave.height = 50;
		fd_btnSave.width = 150;
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText(Utility.getChildValue("btn.save"));
		
		//BUTTON LISTENERS
		
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Utility.convertToContact("insert", widgets);
			}
		});
		
		btnBusiness.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//prevents impossible combinations of check boxes
				if(btnBusiness.getSelection()){
					btnIndividual.setSelection(false);
					btnEmployee.setSelection(false);
					btnIndividual.setEnabled(false);
					btnEmployee.setEnabled(false);
				}
				else{
					btnIndividual.setEnabled(true);
					btnEmployee.setEnabled(true);
				}
			}
		});
		
		btnEmployee.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//prevents impossible combinations of check boxes
				if(btnEmployee.getSelection() || btnIndividual.getSelection()){
					btnBusiness.setSelection(false);
					btnBusiness.setEnabled(false);
				}
				else{
					btnBusiness.setEnabled(true);
				}
			}
		});
		
		btnIndividual.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//prevents impossible combinations of check boxes
				if(btnEmployee.getSelection() || btnIndividual.getSelection()){
					btnBusiness.setSelection(false);
					btnBusiness.setEnabled(false);
				}
				else{
					btnBusiness.setEnabled(true);
				}
			}
		});		
		
		//Saves all the objects in a widget list for later use
		Widget[] w = new Widget[]{
				txtName, btnBusiness, btnEmployee, btnIndividual, txtContactID, txtAddress1, txtAddress2, 
				txtCity, txtState, txtCountry, txtProvince, txtPostalCode, txtContactName, txtEmail, 
				txtPhone, txtMobile, txtFax, timeEntered, dateEntered, timeModified, dateModified, comboStatus
		};
		widgets = Arrays.asList(w);
	}
}
