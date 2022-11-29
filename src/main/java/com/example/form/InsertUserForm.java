package com.example.form;

/**
 * ユーザー情報を登録する際のフォームクラス.
 * 
 * @author hongo
 *
 */
public class InsertUserForm {
	/** 名 */
	private String firstName;

	/** 姓 */
	private String lastName;

	/** メールアドレス */
	private String email;

	/** パスワード */
	private String password;

	/** 確認用パスワード */
	private String confimationPassword;

	/** 郵便番号 */
	private String zipcode;

	/** 住所 */
	private String address;

	/** 電話番号 */
	private String telephone;

	/** GetterとSetter */

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfimationPassword() {
		return confimationPassword;
	}

	public void setConfimationPassword(String confimationPassword) {
		this.confimationPassword = confimationPassword;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	
	/** toString */
	@Override
	public String toString() {
		return "InsertUserForm [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password="
				+ password + ", confimationPassword=" + confimationPassword + ", zipcode=" + zipcode + ", address="
				+ address + ", telephone=" + telephone + "]";
	}

}
