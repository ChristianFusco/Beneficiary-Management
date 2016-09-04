package com.bottomline.BeneficiaryManagement;

import java.util.Arrays;
import java.util.List;

public class Contact {
	private boolean isBusiness;
	private boolean isIndividual;
	private boolean isEmployee;
	private String name;
	private String contactID;
	private String addressOne;
	private String addressTwo;
	private String city;
	private String state;
	private String province;
	private String postalCode;
	private String country;
	private String contactName;
	private String email;
	private String phone;
	private String mobile;
	private String fax;
	private String status;
	private String dateTimeEntered;
	private String dateTimeModified;
	
	/**
	 * Returns true if the two contacts are "equal"
	 * 
	 * Two contacts are equal if they would cause a key error when inserting into the sql table.
	 * Name, business, employee, and individual are keys.
	 * You can have a business joe and an employee joe, but not two business joes.
	 * 
	 * @param c contact to compare
	 * @return true if equal, false otherwise
	 */
	public boolean equals(Contact c){
		if(this.getName().equals(c.getName()) &&
				this.isBusiness() == c.isBusiness() &&
				this.isEmployee() == c.isEmployee() &&
				this.isIndividual() == c.isIndividual()){
			return true;
		}
		return false;
	}
	
	/**
	 * Converts a list attributes into a new contact object.
	 * 
	 * If
	 * Contact c1 = new Contact(c2.getAsList());
	 * Then
	 * c1 == c2
	 * 
	 * Gets/Sets must be in the same order as the getAsList() function, combo declaration in
	 * ManagementWindow.java's createContents() function, and EditContact's widget list.
	 * 
	 * @param attributes - an arraylist containing the string equivalents of a contact.
	 */
	public Contact(List<String> attributes){
		//This has to match the order that the columns appear in ManagementWindow.java
		int i = 0;
		name = attributes.get(i++);
		isBusiness = "True".equals(attributes.get(i++)) ? true : false;
		isEmployee = "True".equals(attributes.get(i++)) ? true : false;
		isIndividual = "True".equals(attributes.get(i++)) ? true : false;
		contactID = attributes.get(i++);
		addressOne = attributes.get(i++);
		addressTwo = attributes.get(i++);
		city = attributes.get(i++);
		state = attributes.get(i++);
		country = attributes.get(i++);
		province = attributes.get(i++);
		postalCode = attributes.get(i++);
		contactName = attributes.get(i++);
		email = attributes.get(i++);
		phone = attributes.get(i++);
		mobile = attributes.get(i++);
		fax = attributes.get(i++);
		dateTimeEntered = attributes.get(i++);
		dateTimeModified = attributes.get(i++);
		status = attributes.get(i++).equalsIgnoreCase(Utility.getChildValue("status")) ? Utility.getChildValue("combo.null") : attributes.get(i - 1);
	}
	
	/**
	 * Converts a contact object into a list of strings.
	 * 
	 * If
	 * Contact c1 = new Contact(c2.getAsList());
	 * Then
	 * c1 == c2
	 * 
	 * Gets/Sets must be in the same order as the Contact(List<String>) function, combo declaration in
	 * ManagementWindow.java's createContents() function, and EditContact's widget list.
	 * 
	 * @return an arraylist containing the string equivalents of a contact.
	 */
	public List<String> getAsList(){
		//This has to match the order that the columns appear in ManagementWindow.java
		String[] values = new String[] {
				getName(),
				isBusiness() ? "True" : "False",
				isEmployee() ? "True" : "False",
				isIndividual() ? "True" : "False",
				getContactID(),
				getAddressOne(),
				getAddressTwo(),
				getCity(),
				getState(),
				getCountry(),
				getProvince(),
				getPostalCode(),
				getContactName(),
				getEmail(),
				getPhone(),
				getMobile(),
				getFax(),
				getDateTimeEntered(),
				getDateTimeModified(),
				getStatus()
		};
		return Arrays.asList(values);
	}
	
	/*
	 * AUTO GENERATED SETTERS/GETTERS
	 */
	public Contact(boolean isBusiness, boolean isIndividual,
			boolean isEmployee, String name) {
		super();
		this.isBusiness = isBusiness;
		this.isIndividual = isIndividual;
		this.isEmployee = isEmployee;
		this.name = name;
	}
	public boolean isBusiness() {
		return isBusiness;
	}
	public void setBusiness(boolean isBusiness) {
		this.isBusiness = isBusiness;
	}
	public Contact() {
		isBusiness = false;
		isIndividual = false;
		isEmployee = false;
		name = "";
		contactID = "";
		addressOne = "";
		addressTwo = "";
		city = "";
		state = "";
		province = "";
		postalCode = "";
		country = "";
		contactName = "";
		email = "";
		phone = "";
		mobile = "";
		fax = "";
		status = "";
		dateTimeEntered = "";
		dateTimeModified = "";
	}
	public boolean isIndividual() {
		return isIndividual;
	}
	public void setIndividual(boolean isIndividual) {
		this.isIndividual = isIndividual;
	}
	public boolean isEmployee() {
		return isEmployee;
	}
	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		if(name == null)
			this.name = "";
	}
	public String getContactID() {
		return contactID;
	}
	public void setContactID(String contactID) {
		this.contactID = contactID;
		if(contactID == null)
			this.contactID = "";
	}
	public String getAddressOne() {
		return addressOne;
	}
	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
		if(addressOne == null)
			this.addressOne = "";
	}
	public String getAddressTwo() {
		return addressTwo;
	}
	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
		if(addressTwo == null)
			this.addressTwo = "";
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
		if(city == null)
			this.city = "";
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
		if(state == null)
			this.state = "";
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
		if(province == null)
			this.province = "";
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
		if(postalCode == null)
			this.postalCode = "";
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
		if(country == null)
			this.country = "";
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
		if(contactName == null)
			this.contactName = "";
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
		if(email == null)
			this.email = "";
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
		if(phone == null)
			this.phone = "";
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
		if(mobile == null)
			this.mobile = "";
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
		if(fax == null)
			this.fax = "";
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		if(status == null)
			this.status = "";
	}
	public String getDateTimeEntered() {
		return dateTimeEntered;
	}
	public void setDateTimeEntered(String dateTimeEntered) {
		this.dateTimeEntered = dateTimeEntered;
		if(dateTimeEntered == null)
			this.dateTimeEntered = "";
	}
	public String getDateTimeModified() {
		return dateTimeModified;
	}
	public void setDateTimeModified(String dateTimeModified) {
		this.dateTimeModified = dateTimeModified;
		if(dateTimeModified == null)
			this.dateTimeModified = "";
	}
	
}
