package be.helha.crowdfunding.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.SerializationUtils;

@Entity
public class Address implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	private String street;
	private String streetNum;
	private String postalCode;
	private String locality;
	private String country;

	public Address() {
	}

	public Address(String street, String streetNum, String postalCode, String locality, String country) {

		this.street = street;
		this.streetNum = streetNum;
		this.postalCode = postalCode;
		this.locality = locality;
		this.country = country;
	}

	public Address clone() {
		return (Address) SerializationUtils.clone(this);
	}

	public Integer getId() {
		return Id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNum() {
		return streetNum;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locality == null) ? 0 : locality.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((streetNum == null) ? 0 : streetNum.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (locality == null) {
			if (other.locality != null)
				return false;
		} else if (!locality.equals(other.locality))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (streetNum == null) {
			if (other.streetNum != null)
				return false;
		} else if (!streetNum.equals(other.streetNum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return street + ", " + streetNum + ", " + postalCode + " " + locality + ", " + country;
	}

}
