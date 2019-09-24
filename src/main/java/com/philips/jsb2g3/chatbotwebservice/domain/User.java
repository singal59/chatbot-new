/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Column(name = "name")
  String name;

  @Column(name = "contact_no")
  String contactNo;

  @Column(name = "email")
  String email;

  public User() {

  }

  public User(String name, String contactNo, String email) {
    super();
    this.name = name;
    this.contactNo = contactNo;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContactNo() {
    return contactNo;
  }

  public void setContactNo(String contactNo) {
    this.contactNo = contactNo;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object obj) {
    if (baseCheck(obj)) {
      final User other = (User) obj;
      if (isNameSame(other) && isEmailSame(other) && isContactSame(other)) {
        return true;
      }
    }
    return false;
  }

  private boolean isContactSame(User other) {
    if (contactNo == null) {
      if (other.contactNo != null) {
        return false;
      }
    } else if (!contactNo.equals(other.contactNo)) {
      return false;
    }
    return true;
  }

  private boolean isNameSame(User other) {
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  private boolean isEmailSame(User other) {
    if (email == null) {
      if (other.email != null) {
        return false;
      }
    } else if (!email.equals(other.email)) {
      return false;
    }
    return true;
  }

  private boolean baseCheck(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    return (getClass() == obj.getClass());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((contactNo == null) ? 0 : contactNo.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }
}
