package com.wfp.security.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lu.hitec.pss.soap.sensor.client._5_x.LocationValue;

import com.enterprisehorizons.magma.designtime.artifact.GeoArtifact;
import com.enterprisehorizons.magma.designtime.artifact.IArtifact;

public class DeviceBean extends GeoArtifact implements Serializable,IArtifact, Comparable<DeviceBean>{


	private static final long serialVersionUID = 0L;
	private String latitude;
	private String longitude;
	private String time; 
	private String name;
	private LocationValue locationValue;
	private String coordStr = null;
	private Date datetime;
	private List<String> mail = null;
	private String homePhone = null;
	private List<String> mobilesList = null;
	private String deviceId = null;
	private String uid= null;
	private String cn = null; //common name
	private List<String> skypePager = null;
	private String sn = null;
	private String organization = null;
	private String fleet = null;
	private String unit = null;
	private String callSign = null;
	private String vehicleType= null;
	private String vehicleReg = null;
	private String description = null;
	private String licensePlate = null;
	private String title = "";
	private boolean startPoint = false;
	private boolean endPoint = false;
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocationValue getLocationValue() {
		return locationValue;
	}
	public void setLocationValue(LocationValue locationValue) {
		this.locationValue = locationValue;
	}
	public String getCoordStr() {
		return coordStr;
	}
	public void setCoordStr(String coordStr) {
		this.coordStr = coordStr;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	public int compareTo(DeviceBean o) {
		if(this.getDatetime().after(o.getDatetime()) ){
			return 1;
		}else if(this.getDatetime().before(o.getDatetime())){
			return -1;
		}
		return 0;
	}
	
	
	
	public List<String> getMail() {
		return mail;
	}
	public void setMail(List<String> mail) {
		this.mail = mail;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public List<String> getMobilesList() {
		return mobilesList;
	}
	public void setMobilesList(List<String> mobilesList) {
		this.mobilesList = mobilesList;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	
	public List<String> getSkypePager() {
		return skypePager;
	}
	public void setSkypePager(List<String> skypePager) {
		this.skypePager = skypePager;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getFleet() {
		return fleet;
	}
	public void setFleet(String fleet) {
		this.fleet = fleet;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCallSign() {
		return callSign;
	}
	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getVehicleReg() {
		return vehicleReg;
	}
	public void setVehicleReg(String vehicleReg) {
		this.vehicleReg = vehicleReg;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isStartPoint() {
		return startPoint;
	}
	public void setStartPoint(boolean startPoint) {
		this.startPoint = startPoint;
	}
	public boolean isEndPoint() {
		return endPoint;
	}
	public void setEndPoint(boolean endPoint) {
		this.endPoint = endPoint;
	}
	
	
}