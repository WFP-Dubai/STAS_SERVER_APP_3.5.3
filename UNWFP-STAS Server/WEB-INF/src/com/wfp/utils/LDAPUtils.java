package com.wfp.utils;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.enterprisehorizons.magma.server.admin.LDAPConfigUtils;
import com.enterprisehorizons.magma.server.util.Cache;
import com.enterprisehorizons.util.Logger;
import com.enterprisehorizons.util.PropertyFilesFactory;
import com.enterprisehorizons.util.StringUtils;
import com.spacetimeinsight.admin.server.util.IPropertyFileConstants;
import com.spacetimeinsight.db.model.util.SecurityDBUtils;
import com.wfp.jobs.LDAPServiceJob;
import com.wfp.security.form.DeviceBean;
import com.wfp.security.form.LDAPUserBean;


/**
 * Utility class for connecting & retrieveing the groups/devices/user information  from the LDAP
 * @author sti-user
 *
 */
public class LDAPUtils implements IEPICConstants {
	
	public static String LDAP_PROPERTIES_FILE = IPropertyFileConstants.PROPERTY_DIRECTORY + "ldap.properties";;
	
	public static Properties getLDAProperties() {
		Logger.info("Initializing the ldap properties from ["+LDAP_PROPERTIES_FILE+"]", LDAPUtils.class);
		return PropertyFilesFactory.getInstance().getProperties(LDAP_PROPERTIES_FILE);
	}
	
	public static String getLDAPConfigValue(String configName){
		return getLDAProperties().getProperty(configName);
	}
	
	/**
	 * Static initialization of cache
	 */
	static{
		if(getLDAPUserDtlsMap() == null){			
			Cache.store(CACHE_LDAPUSER_DTLS, new HashMap<String, LDAPUserBean>());
		}
	}

	/**
	 * retrieves the cached ldap user beans
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, LDAPUserBean> getLDAPUserDtlsMap(){
		return (Map<String, LDAPUserBean>) Cache.retrieve(CACHE_LDAPUSER_DTLS);
	}
	
	/**
	 * Retrieves the user bean for a particular device id.
	 * This method, if found retrieves from the cache else it requests the LDAP. Finally, it returns null.
	 * @param deviceId
	 * @return
	 */
	public static LDAPUserBean getLDAPUserBean(String deviceId){
		//check whether the cache is initialized
		if(getLDAPUserDtlsMap() == null ){
			return null;
		}
		
		return  getLDAPUserDtlsMap().get(deviceId);
	}
	
	/**
	 * return ldap context 
	 * Host: <code>WFPConfigUtils</code>
	 * Admin Name: <code>WFPConfigUtils</code>
	 * Password: <code>WFPConfigUtils</code>
	 * @return
	 * @throws NamingException
	 */
	public static LdapContext getLDAPContext() throws NamingException{
		return getLDAPContext(LDAPConfigUtils.getProviderUrl(), 
				LDAPConfigUtils.getSecurityPrincipal(), SecurityDBUtils.getDecreptedPassword(LDAPConfigUtils.getSecurityCredentials()));
	}
	
	/**
	 * overloaded method to return the LDAP Context usinf user id & password
	 * @param adminName
	 * @param adminPassword
	 * @return
	 * @throws NamingException
	 */
	public static LdapContext getLDAPContext(String adminName, String adminPassword ) throws NamingException{
		return getLDAPContext(LDAPConfigUtils.getProviderUrl(), adminName, adminPassword);
	}
	
	/**
	 * Overloaded method for getting the LDAP COntext based on the host,username, password
	 * @param host
	 * @param adminName
	 * @param adminPassword
	 * @return
	 * @throws NamingException
	 */
	@SuppressWarnings("unchecked")
	public static LdapContext getLDAPContext(String host, String adminName, String adminPassword ) throws NamingException{
		Logger.info("Creating LDAP Context", LDAPUtils.class);
		
		Hashtable props = System.getProperties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, LDAPConfigUtils.getInitialContextFactory());
		props.put(Context.SECURITY_AUTHENTICATION, LDAP_SECURITY_AUTHENTICATION);
		
	//	StringBuffer ldapURL = new StringBuffer(STR_LDAP_PROTOCOL).append(host);
		props.put(Context.SECURITY_PRINCIPAL, adminName);
		props.put(Context.SECURITY_CREDENTIALS, adminPassword);
		// connect to my domain controller
		props.put(Context.PROVIDER_URL, host);
		Logger.info("Completed creating LDAP Context for host ["+host+"]", LDAPUtils.class);
		return (new InitialLdapContext(props, null));
	}
	
	
	/**
	 * Search te LDAP based on default inputs. This method searches for <b>memberOf </b>
	 * @return
	 * @throws NamingException
	 */
	@SuppressWarnings("unchecked")
	public static NamingEnumeration getSearchResults(){
		
		// Specify the attributes to return
		String returnedAtts[] = { "memberOf" };
		// Specify the search scope
		SearchControls searchCtls = new SearchControls();
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchCtls.setReturningAttributes(returnedAtts);
		// Search for objects using the filter
		try {
			return  getSearchResults(getLDAPContext(), searchCtls, SEARCH_FILTER, LDAP_BASE);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			Logger.error("Error occured while searching results ["+e.getLocalizedMessage()+"]", LDAPUtils.class);
		}
		return null;
		
	}

	/**
	 * Overloaded method for searching the LDAP based on the searchfilter & searchbase with contraint as "cn"
	 * @param searchFilter
	 * @param searchBase
	 * @return
	 * @throws NamingException
	 */
	@SuppressWarnings("unchecked")
	public static NamingEnumeration getSearchResults(String searchFilter, String searchBase){
		
		// Specify the attributes to return
		String returnedAtts[] = { "cn" };
		// Specify the search scope
		SearchControls searchCtls = new SearchControls();
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchCtls.setReturningAttributes(returnedAtts);
		// Search for objects using the filter
		try {
			return  getSearchResults(getLDAPContext(), searchCtls, searchFilter, searchBase);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			Logger.error("Error occured while searching results ["+e.getLocalizedMessage()+"]", LDAPUtils.class);
		}
		return null;
		
	}
	
	/**
	 * Overloaded method used to search the ldap based on the search controls, search gfilter & search base 
	 * @param searchCtls
	 * @param searchFilter
	 * @param searchBase
	 * @return
	 * @throws NamingException
	 */
	@SuppressWarnings("unchecked")
	public static NamingEnumeration getSearchResults(SearchControls searchCtls, String searchFilter, String searchBase) {
		Logger.info("Peforming search on LDAP =============", LDAPUtils.class);
		// Search for objects using the filter
		try {
			Logger.info("Search Filter ["+searchFilter+"]", LDAPUtils.class);
			Logger.info("Search Base ["+searchBase+"]", LDAPUtils.class);
			return  getSearchResults(getLDAPContext(), searchCtls, searchFilter, searchBase);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			Logger.error("Error occured while searching results ["+e.getLocalizedMessage()+"]", LDAPUtils.class);
		}
		return null;		
	}
	
	/**
	 * Used to search the LDAP based on the follwoing argumets
	 * @param ldapCtx
	 * @param searchCtls
	 * @param searchFilter
	 * @param searchBase
	 * @return
	 * @throws NamingException
	 */
	@SuppressWarnings("unchecked")
	public static NamingEnumeration getSearchResults(LdapContext ldapCtx, SearchControls searchCtls, String searchFilter, String searchBase){
		try{
			
			// Search for objects using the filter
			try {
				return  ldapCtx.search(searchBase, searchFilter, searchCtls);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				Logger.error("Error occured while searching results ["+e.getLocalizedMessage()+"]", LDAPUtils.class);
			}
		}finally{
			if(ldapCtx != null){
				try {
					ldapCtx.close();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					Logger.error("Error occured while closing the connection to LDAP ["+e.getLocalizedMessage()+"]", LDAPUtils.class);
				}
			}	
		}
		return null;
	}
	
	/**
	 * Overloaded method used to search the ldap based on the search constraints, search filter & search base 
	 * @param attrs
	 * @param searchFilter
	 * @param searchBase
	 * @return
	 * @throws NamingException
	 */
	@SuppressWarnings("unchecked")
	public static NamingEnumeration getSearchResults(String[] attrs, String searchFilter, String searchBase) {
		LdapContext ldapCtx = null;
		try{
			try {
				ldapCtx =  getLDAPContext();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				Logger.error("Error occured while creating the connection to LDAP["+e.getLocalizedMessage()+"]", LDAPUtils.class);
			}
			if(ldapCtx == null){
				return null;
			}
			SearchControls searchCtls = getSimpleSearchControls(attrs);
			// Search for objects using the filter
			
			try {
				return  ldapCtx.search(searchBase, searchFilter, searchCtls);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				Logger.error("Error occured while searching results ["+e.getLocalizedMessage()+"]", LDAPUtils.class);
			}
		}finally{
			if(ldapCtx != null){
				try {
					ldapCtx.close();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					Logger.error("Error occured while closing connection to LDAP ["+e.getLocalizedMessage()+"]", LDAPUtils.class);
				}
			}	
		}
		return null;
	}
	
	/**
	 * returns the list of member(s) for which the device is mapped.
	 * @param deviceId
	 * @return
	 */
	public static List getGroupMembers(String deviceId){
		Logger.info("Retrieving members from groups for a device id ["+deviceId+"]", LDAPUtils.class);
		return parseDataAsList(getSearchResults(CONSTRAINT_MEMBER,FILTER_GRP_NAMES.replace(REPLACE_DEVICE_TOKEN, deviceId), DOMAIN_GRP_NAMES));
	}
	
	/**
	 * Returns the user domain for a device id
	 * @param deviceId
	 * @return
	 */
	public static String getUserDomain(String deviceId){
		try {
			Logger.info("Retrieving user for a device id ["+deviceId+"]", LDAPUtils.class);
			List<String> userDeviceList = getGroupMembers(deviceId);
			
			if(userDeviceList != null){
				for(int i=0; i<userDeviceList.size();i++){
					if(userDeviceList.get(i).startsWith(PARAM_UID)){
						return userDeviceList.get(i);
					}
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Logger.error("Error occured while retrieving the user domain for device id =  ["+deviceId+"] ["+e.getLocalizedMessage()+"]", LDAPUtils.class);
		}
		
		return null;
	}
	
	/**
	 * returns all the devices
	 * @return
	 */
	public static List getDevices(){
		Logger.info("Retrieving all devices from LDAP", LDAPUtils.class);
		return parseDataAsList(getSearchResults(FILTER_MW_DEVICES, DEVICES_SEARCHBASE));
	}
	
	
	/**
	 * returns all the users
	 * @return
	 */
	public static List getUsers(){
		Logger.info("Retrieving all users from LDAP", LDAPUtils.class);
		return parseDataAsList(getSearchResults(FILTER_LDAP_USERS, USERS_SEARCHBASE));
	}
	
	/**
	 * returns map of all user attributes
	 * @param userDomain
	 * @return
	 */
	public static Map<String, Object> getUserAttributes(String userDomain, String node){		
		if(StringUtils.isNull(node)){
			node = FILTER_LDAP_USERS;
		}
		Logger.info("Retrieving User ["+userDomain+"] attributes from Node ["+node+"]", LDAPUtils.class);
		return parseDataAsMap(getSearchResults(CONSTRAINT_ATTR_USERS, node, userDomain), "mail,communicationUri,telephoneNumber");
	}
	
	/**
	 * setting the user bean details after requesting the LDAP
	 * @param deviceId
	 */
	public static void setLDAPUserDtls(String deviceId, String type){
		List<String> missionsList = null;
		if(KEY_STAFF.equalsIgnoreCase(type)){
			String userDomain = getUserDomain(deviceId);
			if(userDomain != null){
				/*if("trackMe9766".equalsIgnoreCase(deviceId)){
					//System.out.println("jk");
				}*/
				Logger.info("Caching started for device ["+deviceId+"]", LDAPUtils.class);
				cacheLDAPUserDtls(deviceId, getUserAttributes(userDomain, null));
				missionsList = getTrackMeMissionsList(deviceId);
				Logger.info("Available Mission from  ["+deviceId+"] are ["+missionsList+"]", LDAPUtils.class);
			}
		}else  if(KEY_VEHICLE.equalsIgnoreCase(type) || KEY_AIRPLANE.equalsIgnoreCase(type)){
			/*if("EMS-DST93D148".equalsIgnoreCase(deviceId)){
				System.out.println("jk");
			}*/
			String allGroupNamesFilter = getLDAPConfigValue("device.search.base").replace("<replacestr>", deviceId);//"cn="+deviceId+",ou=devices,dc=emergency,dc=lu";
			String allGroupsSearchBase = getLDAPConfigValue("groups.search.base");//"ou=groups,dc=emergency,dc=lu";
			List<String> membersList = parseDataAsList(getSearchResults(new String[] {"member"},"(member="+allGroupNamesFilter+")", allGroupsSearchBase));
						
			if(membersList != null){				
				for(String member:membersList){					
					if(!allGroupNamesFilter.equalsIgnoreCase(member)){
						cacheLDAPUserDtls(deviceId, getUserAttributes(member, FILTER_LDAP_VEHICLES));						
					}	
				}				
				
			}
			missionsList = getVehiclesMissionsList(deviceId);
			Logger.info("Available Mission from  ["+deviceId+"] are ["+missionsList+"] based on " +
					"Search Filter = ["+"(member="+allGroupNamesFilter+")"+"] " +
							"Search Base = ["+allGroupsSearchBase+"]", LDAPUtils.class);
		}
		LDAPUserBean userbean = LDAPUtils.getLDAPUserBean(deviceId);
		if(userbean != null){
			userbean.setDeviceId(deviceId);	
			userbean.setAuthorizedGroupsList(missionsList);
		}
	}
	/**
	 * setting the user bean details after requesting the LDAP
	 * @param deviceId
	 */
	public static void setLDAPUserDtls(DeviceBean deviceBean, String type){
		if(KEY_STAFF.equalsIgnoreCase(type)){
			String userDomain = getUserDomain(deviceBean.getName());
			if(userDomain != null){
				retrieveLDAPUserDtls(getUserAttributes(userDomain, null), deviceBean);
			}
		}else  if(KEY_VEHICLE.equalsIgnoreCase(type) || KEY_AIRPLANE.equalsIgnoreCase(type)){
			/*if("EMS-DST2C59A1".equalsIgnoreCase(deviceBean.getName())){
				System.out.println("found ");
			}*/
			String allGroupNamesFilter = getLDAPConfigValue("device.search.base").replace("<replacestr>", deviceBean.getName());//"cn="+deviceBean.getName()+",ou=devices,dc=emergency,dc=lu";
			String allGroupsSearchBase = getLDAPConfigValue("groups.search.base");
			List<String> membersList = parseDataAsList(getSearchResults(new String[] {"member"},"(member="+allGroupNamesFilter+")", allGroupsSearchBase));
			
			if(membersList != null){				
				for(String member:membersList){					
					if(!allGroupNamesFilter.equalsIgnoreCase(member)){
						retrieveLDAPUserDtls(getUserAttributes(member, FILTER_LDAP_VEHICLES), deviceBean);
					}						
					
				}				
				
			}
		}
		
		//cacheLDAPUserDtls(deviceId, null);
		
	}
	
	public static void setLDAPUserDtls(DeviceBean deviceBean){
		if(deviceBean != null){
			
			LDAPUserBean userbean = getLDAPUserBean(deviceBean.getName());			
			if(userbean != null){
				deviceBean.setCn(userbean.getCn());
				deviceBean.setHomePhone(userbean.getHomePhone());
				deviceBean.setMail(userbean.getMail());
				deviceBean.setMobilesList(userbean.getMobilesList());
				deviceBean.setOrganization(userbean.getOrganization());
				deviceBean.setSkypePager(userbean.getSkypePager());
				deviceBean.setUid(userbean.getUid());
				deviceBean.setSn(userbean.getSn());
				deviceBean.setDescription(userbean.getDescription());
				deviceBean.setLicensePlate(userbean.getLicensePlate());
				deviceBean.setTitle(userbean.getTitle()); //()
			}
		}		
	}
	
	/**
	 * Caching the user bean details based on the device id
	 * @param deviceId
	 * @param userAttributes
	 */
	public static void retrieveLDAPUserDtls(Map<String, Object> userAttributes,
			DeviceBean deviceBean) {
		if(userAttributes != null){
			deviceBean.setCn(userAttributes.get(PROPERTY_CN) == null?"":userAttributes.get(PROPERTY_CN).toString());
			deviceBean.setHomePhone(userAttributes.get(PROPERTY_HOME_PHONE) == null?"":userAttributes.get(PROPERTY_HOME_PHONE).toString());
			deviceBean.setMail((userAttributes.get(PROPERTY_MAIL) == null?null: (List<String>) userAttributes.get(PROPERTY_MAIL)));
			deviceBean.setMobilesList(userAttributes.get(PROPERTY_MOBILE) == null?null: (List<String>) userAttributes.get(PROPERTY_MOBILE) );
			deviceBean.setOrganization(userAttributes.get(PROPERTY_ORGANIZATION)== null?"":userAttributes.get(PROPERTY_ORGANIZATION).toString());
			deviceBean.setSkypePager(userAttributes.get(PROPERTY_PAGER)== null?null:(List<String>) userAttributes.get(PROPERTY_PAGER));
			deviceBean.setUid(userAttributes.get(PROPERTY_UID)== null?"":userAttributes.get(PROPERTY_UID).toString());
			deviceBean.setSn(userAttributes.get(PROPERTY_SN)== null?"":userAttributes.get(PROPERTY_SN).toString());
			deviceBean.setDescription(userAttributes.get(PROPERTY_DESCRIPTION)== null?"":userAttributes.get(PROPERTY_DESCRIPTION).toString());
			deviceBean.setLicensePlate(userAttributes.get(PROPERTY_LICENSE_PLATE)== null?"":userAttributes.get(PROPERTY_LICENSE_PLATE).toString());
			deviceBean.setTitle(userAttributes.get("title")== null?"":userAttributes.get("title").toString());
		}
		
	}
	
	/**
	 * Caching the user bean details based on the device id
	 * @param deviceId
	 * @param userAttributes
	 */
	public static void cacheLDAPUserDtls(String deviceId,
			Map<String, Object> userAttributes) {
		Logger.info("LDAP User Details: ["+deviceId+"]  ["+userAttributes+"]", LDAPUtils.class);
		getLDAPUserDtlsMap().put(deviceId, getLDAPUserBean(userAttributes));
	}
	
	/**
	 * populates the user bean
	 * @param map
	 * @return
	 */
	private static LDAPUserBean getLDAPUserBean(Map<String, Object> userAttributes){
		LDAPUserBean userBean = null; 
		if(userAttributes != null){
			
			userBean = new LDAPUserBean();
			userBean.setCn(userAttributes.get(PROPERTY_CN) == null?"":userAttributes.get(PROPERTY_CN).toString());
			userBean.setHomePhone(userAttributes.get(PROPERTY_HOME_PHONE) == null?"":userAttributes.get(PROPERTY_HOME_PHONE).toString());
			userBean.setMail( (userAttributes.get(PROPERTY_MAIL) == null?null:(List<String>)userAttributes.get(PROPERTY_MAIL)));
			userBean.setMobilesList(userAttributes.get(PROPERTY_MOBILE) == null?null:(List<String>)userAttributes.get(PROPERTY_MOBILE) );
			if(userAttributes.get(PROPERTY_ORGANIZATION)!= null && userAttributes.get(PROPERTY_ORGANIZATION) instanceof List){
				userBean.setOrganization(userAttributes.get(PROPERTY_ORGANIZATION)== null?"":((List<String>)userAttributes.get(PROPERTY_ORGANIZATION)).get(0));
			}else {
				userBean.setOrganization("");
			}
			
			userBean.setSkypePager(userAttributes.get(PROPERTY_PAGER)== null?null:(List<String>)userAttributes.get(PROPERTY_PAGER));
			userBean.setUid(userAttributes.get(PROPERTY_UID)== null?"":userAttributes.get(PROPERTY_UID).toString());
			userBean.setSn(userAttributes.get(PROPERTY_SN)== null?"":userAttributes.get(PROPERTY_SN).toString());
			userBean.setDescription(userAttributes.get(PROPERTY_DESCRIPTION)== null?"":userAttributes.get(PROPERTY_DESCRIPTION).toString());
			userBean.setLicensePlate(userAttributes.get(PROPERTY_LICENSE_PLATE)== null?"":userAttributes.get(PROPERTY_LICENSE_PLATE).toString());
			userBean.setTitle(userAttributes.get("title")== null?"":userAttributes.get("title").toString());
			
			return userBean;
			
		}
		
		return null;
	}
	
	/**
	 * Prints all users in LDAP
	 */
	public static void getAllUsersData(){
		//Logger.info("R", LDAPUtils.class);
		String allUserFilter = "(objectClass=person)";
		// Specify the Base for the search
		String allPersonsSearchBase = getLDAPConfigValue("users.search.base");//"ou=users,ou=people,dc=emergency,dc=lu";
		
		parseData(getSearchResults(allUserFilter, allPersonsSearchBase));
		
	}
	
	/**
	 * prints all devices in LDAP
	 */
	public static void getAllDevicesData(){
		String allMWDevicesFilter = "(objectClass=mwdevice)";
		// Specify the Base for the search
		String allDevicesSearhBase = getLDAPConfigValue("devices.search.base");//"ou=devices,dc=emergency,dc=lu";
		
		parseData(getSearchResults(allMWDevicesFilter, allDevicesSearhBase));
	}
	
	/**
	 * Prints user assigned to a device
	 */
	public static void getUser4rDevice(){
		String allGroupNamesFilter = "(member=cn=trackMe9750,ou=devices,dc=emergency,dc=lu)";
		String allGroupsSearchBase = "ou=groups,dc=emergency,dc=lu";
		parseData(getSearchResults(new String[] {"member"},allGroupNamesFilter, allGroupsSearchBase));
	}
	
	/**
	 * prints all groups in LDAP
	 */
	public static void getAllGroupData(){
		String allUserFilter = "(objectClass=groupOfNames)";
		// Specify the Base for the search
		String allPersonsSearchBase = getLDAPConfigValue("profiles.search.base");//"ou=acl_profiles,ou=people,dc=emergency,dc=lu";
		
		parseData(getSearchResults(allUserFilter, allPersonsSearchBase));
		
	}
	
	/**
	 * Prints all groups for a user
	 */
	public static void getAllGroups4rUser(){
		String allGroupNamesFilter = "(member=uid=sti-user,ou=users,ou=people,dc=emergency,dc=lu)";
		String allGroupsSearchBase = "ou=acl_profiles,ou=people,dc=emergency,dc=lu";
		parseData(getSearchResults(new String[] {"cn"},allGroupNamesFilter, allGroupsSearchBase));
	}
	
	/**
	 * prints all missions in LDAP
	 */
	public static List<String> getAllMissions(){
		
		String allGroupNamesFilter = "(objectClass=mission)";
		String allGroupsSearchBase = getLDAPConfigValue("missions.search.base");//"ou=missions,dc=emergency,dc=lu";
		Logger.info("Retrieve all missions from ldap with Search Filter = ["+allGroupNamesFilter+"] " +
				"and Search Base = ["+allGroupsSearchBase+"] on Constraint = [uniqueId]", LDAPUtils.class);
		return  parseDataAsList(getSearchResults(new String[] {"uniqueId"},allGroupNamesFilter, allGroupsSearchBase));
	}
	
	
	/**
	 * Retrieves all the missions as List <String> from LDAP
	 * @return
	 */
	public static List<String> getAllMissionsAsList(){
		String allGroupNamesFilter = "(objectClass=mission)";
		String allGroupsSearchBase = getLDAPConfigValue("missions.search.base");//"ou=missions,dc=emergency,dc=lu";
		Logger.info("Retrieve all taskforces for missions from ldap with Search Filter = ["+allGroupNamesFilter+"] " +
				"and Search Base = ["+allGroupsSearchBase+"] on Constraint = [tfMember]", LDAPUtils.class);
		return parseDataAsList(getSearchResults(new String[] {"tfMember"},allGroupNamesFilter, allGroupsSearchBase));
	}
	
	/**
	 * Prints all the task forces for a User in  LDAP
	 */
	public static void getAllTaskForces4rUser(){
		String allGroupNamesFilter = "(member=uid=avelala,ou=users,ou=people,dc=emergency,dc=lu)";
		String allGroupsSearchBase = getLDAPConfigValue("missions.search.base");//"ou=missions,dc=emergency,dc=lu";
		parseData(getSearchResults(new String[] {"uniqueId"},allGroupNamesFilter, allGroupsSearchBase));
	}
	
	/**
	 * Retrieves all the task forces as List <String> for an User in LDAP
	 * @return
	 */
	public static List<String> getAllTaskForces4rUser(String userId){
		String allGroupNamesFilter = getLDAPConfigValue("user.member.identifier").replace("<replacestr>", userId);//"(member=uid="+userId+",ou=users,ou=people,dc=emergency,dc=lu)";
		String allGroupsSearchBase = getLDAPConfigValue("missions.search.base");//"ou=missions,dc=emergency,dc=lu";
		Logger.info("Retrieve all taskforces for a user ["+userId+"] from ldap with Search Filter = ["+allGroupNamesFilter+"] " +
				"and Search Base = ["+allGroupsSearchBase+"] on Constraint = [tfMember]", LDAPUtils.class);
		return  parseDataAsList(getSearchResults(new String[] {"tfMember"},allGroupNamesFilter, allGroupsSearchBase));
	}
	
	/**
	 * Prints all the domains for a User in  LDAP
	 */
	public static void getAllDomainsOnTaskForces4rUser(){
		String allGroupNamesFilter = "(uniqueID=Pakistan_support)";
		String allGroupsSearchBase = "ou=taskForces,dc=emergency,dc=lu";
		parseData(getSearchResults(new String[] {"member"},allGroupNamesFilter, allGroupsSearchBase));
	}
	
	/**
	 * Retrieves all the task forces as List <String> for an Task Force in LDAP
	 * @return
	 */
	public static List<String>  getAllDomainsOnTaskForces(String taskForceId){
		taskForceId = taskForceId.substring(taskForceId.indexOf("=")+1, taskForceId.indexOf(","));
		//System.out.println(taskForceId);
		String allGroupNamesFilter = "(uniqueID="+taskForceId+")";
		String allGroupsSearchBase = getLDAPConfigValue("taskforces.search.base");//"ou=taskForces,dc=emergency,dc=lu";
		Logger.info("Retrieve all domains for a taskforce ["+taskForceId+"] from ldap with Search Filter = ["+allGroupNamesFilter+"] " +
				"and Search Base = ["+allGroupsSearchBase+"] on Constraint = [member]", LDAPUtils.class);
		return parseDataAsList(getSearchResults(new String[] {"member"},allGroupNamesFilter, allGroupsSearchBase));
	}
	
	/**
	 * prints  all the devices for a domain  in LDAP
	 * @return
	 */
	public static void getAllDeviceInDomain(){
		String allGroupNamesFilter = "(domain=cn=default,cn=mw-A1-service,ou=middlewares,dc=emergency,dc=lu)";
		String allGroupsSearchBase =  getLDAPConfigValue("devices.search.base");//"ou=devices,dc=emergency,dc=lu";
		parseData(getSearchResults(new String[] {"cn"},allGroupNamesFilter, allGroupsSearchBase));
	}
	
	/**
	 * Retrieves all the devices in a domain  as List <String> for an domain id in LDAP
	 * @return
	 */
	public static List<String>  getAllDeviceInDomain(String domainId){
		String allGroupNamesFilter = "(domain="+domainId+")";
		String allGroupsSearchBase = getLDAPConfigValue("devices.search.base");//"ou=devices,dc=emergency,dc=lu";
		Logger.info("Retrieve all devices for a domain ["+domainId+"] from ldap with Search Filter = ["+allGroupNamesFilter+"] " +
				"and Search Base = ["+allGroupsSearchBase+"] on Constraint = [cn]", LDAPUtils.class);
		return parseDataAsList(getSearchResults(new String[] {"cn"},allGroupNamesFilter, allGroupsSearchBase));
	}
	
	/**
	 * Map<taskforceId, List<domainIds>>
	 * @return
	 */
	public static Map<String, List<String>> getAllDomainsOnMissionMap(){
		Map<String, List<String>> allMissionDevicesMap = null;
		List<String> allMissionsList = getAllMissionsAsList();
		if(allMissionsList != null){
			allMissionDevicesMap = new HashMap();
			for (String taskforceId:allMissionsList){
				if(!StringUtils.isNull(taskforceId)){
					List<String> allDomainsList = getAllDomainsOnTaskForces(taskforceId);
					if(allDomainsList != null){
						allMissionDevicesMap.put(taskforceId, allDomainsList);
						//System.out.println("Mission Name: " + taskforceId + " Total Devices: "+allDomainsList.size());
					}
				}
				
				
			}
			return allMissionDevicesMap;
		}
		return null;
	}
	
	/**
	 * Map<domainId, List<deviceId>>
	 * @return
	 */
	public static Map<String, List<String>> getAllDevicesOnDomainMap(){
		Map<String, List<String>> allDomainsOnMissionList = getAllDomainsOnMissionMap();
		Map<String, List<String>> allDevicesOnDomainMap = null;
		if(allDomainsOnMissionList != null){
			allDevicesOnDomainMap = new HashMap<String, List<String>>();
			for(Map.Entry<String, List<String>> entry: allDomainsOnMissionList.entrySet()){
				List<String> allDomains = entry.getValue();
				if(allDomains != null){
					for(String domainId:allDomains){
						if(allDevicesOnDomainMap.get(domainId) == null){
							List<String> allDevicesList = getAllDeviceInDomain(domainId);
							allDevicesOnDomainMap.put(domainId, allDevicesList);
							//System.out.println("Domain Name: " + domainId + " Total Devices: "+allDevicesOnDomainMap.get(domainId).size());
						}
					}
				}
			}
		}
	
		return allDevicesOnDomainMap;
	}
	
	/**
	 * Map<placeid, Map<attrName, attrValue>>
	 * @return
	 */
	public static Map getAllPlaces(){
		String allGroupNamesFilter = "(objectClass=place)";
		String allGroupsSearchBase = getLDAPConfigValue("places.search.base");//"ou=places,ou=resources,dc=emergency,dc=lu";
		String[] attrArray = new String[] {"cn","compasId", "description", "postalAddress", "type", "coord-latitude","coord-longitude", "ocs", "skype" };
		Logger.info("Retrieve all places from ldap with Search Filter = ["+allGroupNamesFilter+"] " +
				"and Search Base = ["+allGroupsSearchBase+"] on Constraint = [cn,compasId, description, postalAddress, type, coord-latitude,coord-longitude, ocs, skype ]", LDAPUtils.class);
		return  parseDataAsMap(getSearchResults(attrArray,allGroupNamesFilter, allGroupsSearchBase), "compasId", "cn", attrArray);	
	}
	
	public static boolean validatePlanes(String planeId, String[] types){

		String allGroupNamesFilter = getLDAPConfigValue("planes.search.base").replace("<replacestr>", planeId);//"cn="+planeId+",ou=devices,dc=emergency,dc=lu";
		String allGroupsSearchBase = getLDAPConfigValue("groups.search.base");//"ou=groups,dc=emergency,dc=lu";
		Logger.info("Validating Planes with Search Filter = ["+allGroupNamesFilter+"] " +
				"and Search Base = ["+allGroupsSearchBase+"] on Constraint = [member ]", LDAPUtils.class);
		List<String> membersList = parseDataAsList(getSearchResults(new String[] {"member"},"(member="+allGroupNamesFilter+")", allGroupsSearchBase));
		Logger.info("Valid memebers List ["+membersList+"]", LDAPUtils.class);
		
		if(membersList != null){
			if(types != null ){
				for(String member:membersList){
					for(String type:types){
						String allVehiclesNamesFilter = getLDAPConfigValue("vehicle.type.identifier").replace("<replacestr>", type);//"(type=cn="+type+",ou=vehicleTypes,ou=resourcesType,dc=emergency,dc=lu)";
						Logger.info("Validating on each member with Search Filter = ["+allVehiclesNamesFilter+"] " +
								"and Search Base = ["+member+"] on Constraint = [type ]", LDAPUtils.class);						
						List placeDtls = parseDataAsList(getSearchResults(new String[] {"type"},allVehiclesNamesFilter, member));
						if(placeDtls != null && placeDtls.size() > 0){
							return true;
						}
					}
				}				
			}
		}
		
		/*//String allVehiclesSearchBase = "cn="+planeId+",ou=vehicles,dc=emergency,dc=lu";

		 
		if(types != null ){
			for(String type:types){
				String allVehiclesNamesFilter = "(type=cn="+type+",ou=resourcesType,dc=emergency,dc=lu)";
				List placeDtls = parseDataAsList(getSearchResults(new String[] {"type"},allVehiclesNamesFilter, allVehiclesSearchBase));
				if(placeDtls != null && placeDtls.size() > 0){
					return true;
				}
			}
		}*/
		
		return false;
	}
	
	public static boolean validateVehicles(String vehicleId, String[] types){
		
		
		String allGroupNamesFilter = getLDAPConfigValue("device.search.base").replace("<replacestr>", vehicleId);;//"cn="+vehicleId+",ou=devices,dc=emergency,dc=lu";
		String allGroupsSearchBase = getLDAPConfigValue("groups.search.base");//"ou=groups,dc=emergency,dc=lu";
		Logger.info("Validating Vehicles with Search Filter = ["+allGroupNamesFilter+"] " +
				"and Search Base = ["+allGroupsSearchBase+"] on Constraint = [member ]", LDAPUtils.class);		
		List<String> membersList = parseDataAsList(getSearchResults(new String[] {"member"},"(member="+allGroupNamesFilter+")", allGroupsSearchBase));
		///String allVehiclesSearchBase = "cn="+vehicleId+",ou=vehicles,dc=emergency,dc=lu";
		Logger.info("Valid memebers List ["+membersList+"]", LDAPUtils.class);
		if(membersList != null){
			if(types != null ){
				for(String member:membersList){
					for(String type:types){
						if(!allGroupNamesFilter.equalsIgnoreCase(type)){
							String allVehiclesNamesFilter = getLDAPConfigValue("vehicle.type.identifier").replace("<replacestr>", type);//"(type=cn="+type+",ou=vehicleTypes,ou=resourcesType,dc=emergency,dc=lu)";
							Logger.info("Validating on each member with Search Filter = ["+allVehiclesNamesFilter+"] " +
									"and Search Base = ["+member+"] on Constraint = [type ]", LDAPUtils.class);						
							
							List placeDtls = parseDataAsList(getSearchResults(new String[] {"type"},allVehiclesNamesFilter, member));
							if(placeDtls != null && placeDtls.size() > 0){
								return true;
							}
						}						
					}
				}				
			}
		}
		
		
		return false;
	}
	
	public static boolean validateStaff(String deviceId, String[] types){
		@SuppressWarnings("unused")
		
		List<String> groupMembers = getGroupMembers(deviceId);
		Logger.info("Valid memebers List ["+groupMembers+"]", LDAPUtils.class);
		if(types != null && groupMembers != null){
			for(String type:types){
				for(String member:groupMembers){
					if(member.indexOf(type) > -1){
						return true;						
					}
				}
			}
		}
		
		
		return false;
	}
	
	public static void main(String args[]){
		/*getAllUsersData();
		getAllDevicesData();
		getUser4rDevice();
		getAllGroupData();*/
		
		//getAllGroups4rUser();
		//getAllTaskForces4rUser();
		//getAllDomainsOnTaskForces4rUser();
		//getAllDeviceInDomain();
		//getAllMissions();
		getAllDevicesOnDomainMap();
		
	}
	

	
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String,String>>  parseDataAsMap(NamingEnumeration searchResults, String key, String optionalKey, String[] attrArray){
		Logger.info("Formatting the data as MAP", LDAPUtils.class
				);
		Map<String, Map<String,String>> resultMap = null;
		int totalResultLogger = 0;
		if(searchResults == null){
			return null;
		}
		// Loop through the search results
		while (searchResults.hasMoreElements()) {
			
			SearchResult sr = null;
			try {
				sr = (SearchResult) searchResults.next();
			} catch (NamingException e1) {
				Logger.error("No Search results on LDAP ", LDAPUtils.class);
			}
			if(sr == null ){
				Logger.error("No Search results on LDAP ", LDAPUtils.class);
				return null;
			}
			
			Attributes attrs = sr.getAttributes();
			if (attrs != null) {
				if(resultMap == null){
					resultMap = new HashMap<String, Map<String, String>>();
				}
				try {
					Map<String, String> resultAttrMap = new HashMap();
					for(String attr:attrArray){
						if(resultAttrMap.get(attr) == null){
							attrs.get(attr);
							resultAttrMap.put(attr, "");
						}
					}
					for (NamingEnumeration ae = attrs.getAll(); ae
							.hasMore();) {
						Attribute attr = (Attribute) ae.next();
						//System.out.println("Attribute: " + attr.getID());
						for (NamingEnumeration e = attr.getAll(); e
								.hasMore(); totalResultLogger++) {
							String attrValue = (String) e.next();
							
							resultAttrMap.put(attr.getID(), attrValue);
							//System.out.println(" " + totalResultLogger + ". "
							//		+ attrValue);
						}

					}
					
					
					if(!StringUtils.isNull(resultAttrMap.get(key))){
						resultMap.put(resultAttrMap.get(key), resultAttrMap);
					}else {
						resultAttrMap.put("compasId", "");
						resultMap.put(resultAttrMap.get(optionalKey), resultAttrMap);
					}
					
				} catch (NamingException e) {
					Logger.error("Error ocuring while reading the attributes ", LDAPUtils.class, e);
				}

			}else {
				Logger.info("No attributes found on LDAP", LDAPUtils.class);
			}
		}
		
		return resultMap;
	}
	
	public static SearchControls getSimpleSearchControls(String[] attrIDS) {
	    SearchControls searchControls = new SearchControls();
	    searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    searchControls.setTimeLimit(30000);
	    if(attrIDS != null){
	    	// String[] attrIDs = {"objectGUID"};
	 	    searchControls.setReturningAttributes(attrIDS);
	    }
	    
	    return searchControls;
	}
	
	public static SearchControls getSimpleSearchControls() {
	    SearchControls searchControls = new SearchControls();
	    searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    searchControls.setTimeLimit(30000);
	    //String[] attrIDs = {"objectGUID"};
	    //searchControls.setReturningAttributes(attrIDs);
	    return searchControls;
	}

	@SuppressWarnings("unchecked")
	public static void parseData(NamingEnumeration searchResults){
		
		int totalResultLogger = 0;
		if(searchResults == null){
			return;
		}
		// Loop through the search results
		while (searchResults.hasMoreElements()) {
			SearchResult sr = null;
			try {
				sr = (SearchResult) searchResults.next();
			} catch (NamingException e1) {
				Logger.error("No Search results on LDAP ", LDAPUtils.class);
			}
			if(sr == null ){
				Logger.error("No Search results on LDAP ", LDAPUtils.class);
				return;
			}
			
			Attributes attrs = sr.getAttributes();
			if (attrs != null) {

				try {
					for (NamingEnumeration ae = attrs.getAll(); ae
							.hasMore();) {
						Attribute attr = (Attribute) ae.next();
						//System.out.println("Attribute: " + attr.getID());
						for (NamingEnumeration e = attr.getAll(); e
								.hasMore(); totalResultLogger++) {

							/*System.out.println(" " + totalResultLogger + ". "
									+ e.next());*/
						}

					}

				} catch (NamingException e) {
					Logger.error("Error ocuring while reading the attributes ", LDAPUtils.class, e);
				}

			}else {
				Logger.info("No attributes found on LDAP", LDAPUtils.class);
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static List parseDataAsList(NamingEnumeration searchResults){
		Logger.info("Formatting the data as List", LDAPUtils.class
		);
		List<String> resultAttr = null;
		int totalResultLogger = 0;
		if(searchResults == null){
			return null;
		}
		// Loop through the search results
		while (searchResults.hasMoreElements()) {
			
			SearchResult sr = null;
			try {
				sr = (SearchResult) searchResults.next();
			} catch (NamingException e1) {
				Logger.error("No Search results on LDAP ", LDAPUtils.class);
			}
			if(sr == null ){
				Logger.error("No Search results on LDAP ", LDAPUtils.class);
				return null;
			}
			
			Attributes attrs = sr.getAttributes();
			if (attrs != null) {
				if(resultAttr == null){
					resultAttr = new ArrayList();
				}
				try {
					for (NamingEnumeration ae = attrs.getAll(); ae
							.hasMore();) {
						Attribute attr = (Attribute) ae.next();
						//System.out.println("Attribute: " + attr.getID());
						for (NamingEnumeration e = attr.getAll(); e
								.hasMore(); totalResultLogger++) {
							String attrValue = (String) e.next();
							
							resultAttr.add(attrValue);
							//System.out.println(" " + totalResultLogger + ". "
							//		+ attrValue);
						}

					}

				} catch (NamingException e) {
					Logger.error("Error ocuring while reading the attributes ", LDAPUtils.class, e);
				}

			}else {
				Logger.info("No attributes found on LDAP", LDAPUtils.class);
			}
		}
		
		return resultAttr;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String>  parseDataAsMap(NamingEnumeration searchResults){
		Map<String, String> resultAttrMap = null;
		int totalResultLogger = 0;
		if(searchResults == null){
			return null;
		}
		// Loop through the search results
		while (searchResults.hasMoreElements()) {
			
			SearchResult sr = null;
			try {
				sr = (SearchResult) searchResults.next();
			} catch (NamingException e1) {
				Logger.error("No Search results on LDAP ", LDAPUtils.class);
			}
			if(sr == null ){
				Logger.error("No Search results on LDAP ", LDAPUtils.class);
				return null;
			}
			
			Attributes attrs = sr.getAttributes();
			if (attrs != null) {
				if(resultAttrMap == null){
					resultAttrMap = new HashMap<String, String>();
				}
				try {
					for (NamingEnumeration ae = attrs.getAll(); ae
							.hasMore();) {
						Attribute attr = (Attribute) ae.next();
						//System.out.println("Attribute: " + attr.getID());
						for (NamingEnumeration e = attr.getAll(); e
								.hasMore(); totalResultLogger++) {
							String attrValue = (String) e.next();
							
							resultAttrMap.put(attr.getID(), attrValue);
							//System.out.println(" " + totalResultLogger + ". "
							//		+ attrValue);
						}

					}

				} catch (NamingException e) {
					Logger.error("Error ocuring while reading the attributes ", LDAPUtils.class, e);
				}

			}else {
				Logger.info("No attributes found on LDAP", LDAPUtils.class);
			}
		}
		
		return resultAttrMap;
	}
	public static Map<String, Object>  parseDataAsMap(NamingEnumeration searchResults, String listValues){
		Logger.info("Formatting the data as MAP", LDAPUtils.class
		);
		Map<String, Object> resultAttrMap = null;
		int totalResultLogger = 0;
		if(searchResults == null){
			return null;
		}
		// Loop through the search results
		while (searchResults.hasMoreElements()) {
			
			SearchResult sr = null;
			try {
				sr = (SearchResult) searchResults.next();
			} catch (NamingException e1) {
				Logger.error("No Search results on LDAP ", LDAPUtils.class);
			}
			if(sr == null ){
				Logger.error("No Search results on LDAP ", LDAPUtils.class);
				return null;
			}
			
			Attributes attrs = sr.getAttributes();
			if (attrs != null) {
				if(resultAttrMap == null){
					resultAttrMap = new HashMap<String, Object>();
				}
				try {
					for (NamingEnumeration ae = attrs.getAll(); ae
							.hasMore();) {
						Attribute attr = (Attribute) ae.next();
						//System.out.println("Attribute: " + attr.getID());
						for (NamingEnumeration e = attr.getAll(); e
								.hasMore(); totalResultLogger++) {
							String attrValue = (String) e.next();
							List<String> attrValuesList = null;
							if(listValues.indexOf(attr.getID()) >=0){
								attrValuesList =  resultAttrMap.get(attr.getID())== null?null:(List<String>) resultAttrMap.get(attr.getID());
								if(attrValuesList == null){
									attrValuesList = new ArrayList<String>();
								}
								attrValuesList.add(attrValue);
								resultAttrMap.put(attr.getID(), attrValuesList);
							}else {
								resultAttrMap.put(attr.getID(), attrValue);
							}
							
							//System.out.println(" " + totalResultLogger + ". "
							//		+ attrValue);
						}

					}

				} catch (NamingException e) {
					Logger.error("Error ocuring while reading the attributes ", LDAPUtils.class, e);
				}

			}else {
				Logger.info("No attributes found on LDAP", LDAPUtils.class);
			}
		}
		
		return resultAttrMap;
	}
	
	public static boolean validatePlaceByUser(String placeId, String userId){
		Logger.info("Validating Places ["+placeId+"] by user Id ["+userId+"]", LDAPUtils.class);
		@SuppressWarnings("unused")
		List<String> missionsList = getAllMisisons4rUser(userId);
		String allGroupNamesFilter = getLDAPConfigValue("place.member.identifier").replace("<replacestr>", placeId);//"(placeMember=cn="+placeId+",ou=places,ou=resources,dc=emergency,dc=lu)";
		//String allUserFilter1 = "(objectClass=person)";
		// Specify the Base for the search
		//String allGroupsSearchBase = "ou=users,ou=people,dc=emergency,dc=lu";
		if(missionsList != null){
			for(String mission:missionsList){
				String allGroupsSearchBase = getLDAPConfigValue("mission.unique.identifier").replace("<replacestr>", mission);//"uniqueId="+mission+",ou=missions,dc=emergency,dc=lu";
				//parseData(getSearchResults(new String[] {"mail"},allUserFilter1, allGroupsSearchBase));	
				List placeDtls = parseDataAsList(getSearchResults(new String[] {"placeMember"},allGroupNamesFilter, allGroupsSearchBase));
				if(placeDtls != null && placeDtls.size() > 0){
					return true;
				}
			}
			
		}
		return false;
	}
	
	public static boolean validateVehiclesByUser(String vehicleId, String userId){

		String allGroupNamesFilter = getLDAPConfigValue("device.member.identifier").replace("<replacestr>", vehicleId);//"(member=cn="+vehicleId+",ou=devices,dc=emergency,dc=lu)";
		String allGroupsSearchBase = getLDAPConfigValue("groups.search.base");//"ou=groups,dc=emergency,dc=lu";
		List<String> membersList = parseDataAsList(getSearchResults(new String[] {"member"},allGroupNamesFilter, allGroupsSearchBase));
		
		List<String> missionsList = getAllMisisons4rUser(userId);
		
		/*if("EMS-DST2C59A1".equalsIgnoreCase(vehicleId)){
			System.out.println("object found ");
		}*/
		if(missionsList != null){
			if(membersList != null){
				for(String member:membersList){
					for(String mission:missionsList){
						String allVehiclesSearchBase = getLDAPConfigValue("mission.unique.identifier").replace("<replacestr>", mission);//"uniqueId="+mission+",ou=missions,dc=emergency,dc=lu";
						
						List vehicleDtls = parseDataAsList(getSearchResults(new String[] {"vehicleMember"},"vehicleMember="+member, allVehiclesSearchBase));
						if(vehicleDtls != null && vehicleDtls.size() > 0){
							return true;
						}
					}
				}
			}
			
			
		}
		return false;
	}
	
	
	
	public static List  getAllMisisons4rUser(String userId){
		String allGroupNamesFilter = getLDAPConfigValue("user.member.identifier").replace("<replacestr>", userId);//"(member=uid="+userId+",ou=users,ou=people,dc=emergency,dc=lu)";
		//String allUserFilter1 = "(objectClass=person)";
		// Specify the Base for the search
		//String allGroupsSearchBase = "ou=users,ou=people,dc=emergency,dc=lu";
		String allGroupsSearchBase = getLDAPConfigValue("missions.search.base");//"ou=missions,dc=emergency,dc=lu";
		//parseData(getSearchResults(new String[] {"mail"},allUserFilter1, allGroupsSearchBase));	
		return parseDataAsList(getSearchResults(new String[] {"uniqueId"},allGroupNamesFilter, allGroupsSearchBase));
	}
	
	public static boolean validateDeviceByUser(String deviceId, String userId){
		if(StringUtils.isNull(deviceId)){
			return false;
		}
		
		/*Map<String, List<String>> allDomainsOnMissionList = (Map<String, List<String>>) LDAPServiceJob.getInstance().getLDAPCacheData("allDomainsOnMissionList");
		Map<String, List<String>> allDevicesDomainsMap = (Map<String, List<String>>) LDAPServiceJob.getInstance().getLDAPCacheData("allDevicesDomains");
		List<String> allTaskForces = getAllTaskForces4rUser(userId);
		
		if(allTaskForces != null){
			for(String taskforceId:allTaskForces){
				List<String> allDomains = allDomainsOnMissionList.get(taskforceId);
				if(allDomains !=null){
					for(String domainId: allDomains){
						List<String> allDevices = allDevicesDomainsMap.get(domainId);
						//System.out.println(deviceId+": "+allDevices.contains(deviceId));
						if( allDevices !=null && allDevices.contains(deviceId) ) {
							return true;
						}
					}
				}
				
			}
		}
			
		
		return false;*/
		
		String allGroupNamesFilter =  getLDAPConfigValue("device.member.identifier").replace("<replacestr>", deviceId);// "(member=cn="+deviceId+",ou=devices,dc=emergency,dc=lu)";
		String allGroupsSearchBase = getLDAPConfigValue("groups.search.base");// "ou=groups,dc=emergency,dc=lu";
		List<String> membersList = parseDataAsList(getSearchResults(new String[] {"member"},allGroupNamesFilter, allGroupsSearchBase));
		
		List<String> missionsList = getAllMisisons4rUser(userId);
		
		/*if("EMS-DST2C59A1".equalsIgnoreCase(vehicleId)){
			System.out.println("object found ");
		}*/
		if(missionsList != null){
			if(membersList != null){
				for(String member:membersList){
					for(String mission:missionsList){
						String allStaffSearchBase = getLDAPConfigValue("mission.unique.identifier").replace("<replacestr>", mission);//"uniqueId="+mission+",ou=missions,dc=emergency,dc=lu";
						
						List staffDtls = parseDataAsList(getSearchResults(new String[] {"member"},"member="+member, allStaffSearchBase));
						if(staffDtls != null && staffDtls.size() > 0){
							return true;
						}
					}
				}
			}
			
			
		}
		return false;
	}
	
	public static List<String> getTrackMeMissionsList(String deviceId){
		if(StringUtils.isNull(deviceId)){
			return null;
		}
		String allGroupNamesFilter = getLDAPConfigValue("device.member.identifier").replace("<replacestr>", deviceId);//"(member=cn="+deviceId+",ou=devices,dc=emergency,dc=lu)";
		String allGroupsSearchBase = getLDAPConfigValue("groups.search.base");//"ou=groups,dc=emergency,dc=lu";
		/*if("trackMe9012".equalsIgnoreCase(deviceId)){
			System.out.println("hi");
		}*/
		List<String> membersList = parseDataAsList(getSearchResults(new String[] {"member"},allGroupNamesFilter, allGroupsSearchBase));
		
		
		
		List<String> missionsList = getAllMissions();
		List<String> allMissionsList = null;
		
		if(missionsList != null){
			if(membersList != null){
				allMissionsList = new ArrayList<String>();
				for(String member:membersList){
					for(String mission:missionsList){
						String allStaffSearchBase = getLDAPConfigValue("mission.unique.identifier").replace("<replacestr>", mission);//"uniqueId="+mission+",ou=missions,dc=emergency,dc=lu";
						
						List staffDtls = parseDataAsList(getSearchResults(new String[] {"member"},"member="+member, allStaffSearchBase));
						if(staffDtls != null && staffDtls.size() > 0){
							allMissionsList.add(mission);
						}
					}
				}
			}
			
			
		}
		return allMissionsList;
	}
	
	public static List<String> getVehiclesMissionsList(String vehicleId){

		String allGroupNamesFilter = getLDAPConfigValue("device.member.identifier").replace("<replacestr>", vehicleId);//"(member=cn="+vehicleId+",ou=devices,dc=emergency,dc=lu)";
		String allGroupsSearchBase = getLDAPConfigValue("groups.search.base");//"ou=groups,dc=emergency,dc=lu";
		List<String> membersList = parseDataAsList(getSearchResults(new String[] {"member"},allGroupNamesFilter, allGroupsSearchBase));
		
		List<String> missionsList = getAllMissions();
		List<String> allMissionsList = null;

		if(missionsList != null){
			if(membersList != null){
				allMissionsList = new ArrayList<String>();
				for(String member:membersList){
					for(String mission:missionsList){
						String allVehiclesSearchBase = getLDAPConfigValue("mission.unique.identifier").replace("<replacestr>", mission);//"uniqueId="+mission+",ou=missions,dc=emergency,dc=lu";
						
						List vehicleDtls = parseDataAsList(getSearchResults(new String[] {"vehicleMember"},"vehicleMember="+member, allVehiclesSearchBase));
						if(vehicleDtls != null && vehicleDtls.size() > 0){
							allMissionsList.add(mission);
						}
					}
				}
			}
			
			
		}
		return allMissionsList;
	}
	 
	public static boolean authorizeDevices(String deviceId, String userId){
		if(getLDAPUserDtlsMap() != null ){
			/*if("trackMe9013".equalsIgnoreCase(deviceId)){
				System.out.println("hi");
			}*/
			LDAPUserBean ldapUserBean = getLDAPUserDtlsMap().get(deviceId);			
			Map map  = LDAPServiceJob.getInstance().getLDAPUsersMissionsList();
			List<String> missionsList = null; 
			if(map != null ){	
				missionsList =  (List<String>) map.get(userId);
				if(missionsList == null){
					LDAPServiceJob.getInstance().getLDAPUsersMissionsList().put(userId, getAllMisisons4rUser(userId));
					missionsList = (List<String>) map.get(userId);
				}
			} 
			Logger.info("Got the userbean for device ["+deviceId+"] as ["+ldapUserBean+"]", LDAPUtils.class);
			List<String> deviceMissionsList = ldapUserBean.getAuthorizedGroupsList();
			if(deviceMissionsList != null){
				if(missionsList != null){
					for(String member:missionsList){
						if(deviceMissionsList.contains(member)){
							return true;
						}
					}
				}
			}
		}
		
		
		return false;
	}

}