package com.packt.webstore.domain;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;




    @XmlRootElement
	public class Product implements Serializable{
    	
    	private static final long serialVersionUID = 3678147792576131001L;

      @Pattern(regexp="P[0-9]+", message="{Pattern.Product.productId.validation}")
	  private String productId;
      @Size(min=4, max=50, message="{Size.Product.name.validation}")
	  private String name;
      @Min(value=0, message="Min.Product.unitPrice.validation}")
      @Digits(integer=8, fraction=2, message="{Digits.Product.unitPrice.validation}")
      @NotNull(message= "{NotNull.Product.unitPrice.validation}")
	  private BigDecimal unitPrice;
	  private String description;
	  private String manufacturer;
	  private String category;
	  private long unitsInStock;
	  private long unitsInOrder;
	  private boolean discontinued;
	  private String condition;
	  @JsonIgnore
	  private MultipartFile productImage;
	  @JsonIgnore
	  private MultipartFile productManual;

	  public Product() {
		  super();
	  }

	  public Product(String productId, String name, BigDecimal unitPrice) {
	    this.productId = productId;
	    this.name = name;
	    this.unitPrice = unitPrice;
	  }

	  // add setters and getters for all the fields here
	  @XmlTransient 
	  public MultipartFile getProductImage(){
		  return this.productImage;
	  }
	  public void setProductImage(MultipartFile image){
		  this.productImage = image;
	  }
	  @XmlTransient
	  public MultipartFile getProductManual(){
		  return this.productManual;
	  }
	  public void setProductManual(MultipartFile manual){
		  this.productManual = manual;
	  }
	  public long getUnitsInStock(){
		  return this.unitsInStock;
	  }
	  public void setUnitsInStock(long unitsInStock){
		  this.unitsInStock = unitsInStock;
	  }
	  public long getUnitsInOrder(){
		  return this.unitsInOrder;
	  }
	  public void setUnitsInOrder(long unitsInOrder){
		  this.unitsInOrder = unitsInOrder;
	  }
	  public boolean getDiscontinued(){
		  return this.discontinued;
	  }
	  public void setDiscontinued(boolean discontinued){
		  this.discontinued = discontinued;
	  }
	  public String getProductId(){
		  return this.productId;
	  }
	  public void setProductId(String id){
		  this.productId = id;
	  }
	  public String getCondition(){
		  return this.condition;
	  }
	  public void setCondition(String condition){
		  this.condition = condition;
	  }
	  public String getName(){
		  return this.name;
	  }
	  public void setName(String name){
		  this.name = name;
	  }
	  public BigDecimal getUnitPrice(){
		  return this.unitPrice;
	  }
	  public void setUnitPrice(BigDecimal unitPrice){
		  this.unitPrice = unitPrice;
	  }
	  public String getManufacturer(){
		  return this.manufacturer;
	  }
	  public void setManufacturer(String manufacturer){
		  this.manufacturer = manufacturer;
	  }
	  public String getDescription(){
		  return this.description;
	  }
	  public void setDescription(String description){
		  this.description = description;
	  }
	  public String getCategory(){
		  return category;
	  }
	  public void setCategory(String category){
		  this.category = category;
	  }
	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    Product other = (Product) obj;
	    if (productId == null) {
	      if (other.productId != null)
	        return false;
	    } else if (!productId.equals(other.productId))
	      return false;
	    return true;
	  }

	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result
	        + ((productId == null) ? 0 : productId.hashCode());
	    return result;
	  }

	  @Override
	  public String toString() {
	    return "Product [productId=" + productId + ", name=" + name + "]";
	  }
	  public static long getSerialversionuid() {
			return serialVersionUID;
		}
}
