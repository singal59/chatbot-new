/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "monitor")
public class Monitor {

  @Id
  @Column(name="name")
  String name;

  @Column(name="brand")
  String brand;

  @Column(name="size")
  String size;

  @Column(name="Type")
  String type;

  public Monitor() {

  }

  public Monitor(String name, String brand, String size, String type) {
    super();
    this.name = name;
    this.brand = brand;
    this.size = size;
    this.type=type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isValid() {
    return isBrandValid() && isNameValid() && isSizeValid() && isTypeValid();
  }

  private boolean isBrandValid() {
    return this.brand != null && this.brand.length() > 0;
  }

  private boolean isNameValid() {
    return this.name != null && this.name.length() > 0;
  }

  private boolean isSizeValid() {
    return this.size != null && this.size.length() > 0;
  }

  private boolean isTypeValid() {
    return this.type != null && this.type.length() > 0;
  }

}