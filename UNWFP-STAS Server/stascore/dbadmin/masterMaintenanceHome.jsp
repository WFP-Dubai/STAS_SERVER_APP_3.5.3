<%@page import="com.enterprisehorizons.magma.server.util.ServerUtils"%>
<%@page import="com.enterprisehorizons.util.StringUtils"%>
<%@ page import="com.enterprisehorizons.magma.config.dbadmin.* , com.enterprisehorizons.magma.config.dbadmin.bd.*, java.io.*, java.util.*" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<%@ taglib prefix="tiles" uri="/tags/struts-tiles"%>


<html:html locale="true">
<head>
<%@ include file="/common/dojo.jsp"%>


<script type="text/javascript" src="<%=ServerUtils.getContextName(request)%>/js/dijit/form/_testCommon.js"></script>
<script type="text/javascript">
         dojo.require("dojo.parser");
         dojo.require("dijit.form.Form");
         dojo.require("dijit.form.Button");
         dojo.require("dijit.form.ComboBox");
         dojo.require("dijit.form.FilteringSelect");
</script>

<style>
			@import "<%=ServerUtils.getContextName(request)%>/js/dojo/resources/dojo.css";
			@import "<%=ServerUtils.getContextName(request)%>/js/dijit/themes/tundra/tundra.css";
			@import "<%=ServerUtils.getContextName(request)%>/js/dijit/themes/tundra/tundra_rtl.css";
			@import "<%=ServerUtils.getContextName(request)%>/js/dijit/tests/css/dijitTests.css";
			@import "<%=ServerUtils.getContextName(request)%>/js/dojox/form/resources/FileInput.css"; 
			@import "<%=ServerUtils.getContextName(request)%>/css/style.css";

        body .txtareamedium {
            width: 25em;
            height: 5em;
        }

</style>

<title><bean:message key='dbconfig.model.title'/></title>

    <script>
	
     function submitForm(btn) {
            //document.forms[0].submitName.value=btn.value;
            var selectObj = dijit.byId("selectedModelId");

			if(btn.name != 'btnBack' && btn.name != 'btnHome')			
			if(selectObj.value == "" || selectObj.value == null)
			{
				showEmptyDialog("<bean:message key='dbconfig.model.check.value'/>", "<bean:message key='dbconfig.model.check.title'/>");
				return false;
			}

            document.forms[0].submitName.value=btn.name;
			<%if(request.getParameter("fromHeader")!=null){%>
			document.forms[0].action=document.forms[0].action+"?fromHeader=fromParent"
			<%}%>
            document.forms[0].submit();
        }

        
    </script>
</head>

<body class="tundra bodybg" >

<html:form action="/masterMaintenanceHomeAction"  >
<html:hidden property = "<%= ModelConfigConstants.SUBMIT_HOME_SCREEN_PARAM %>" value="false" />
<center>
<table width="101.2%" cellspacing="0" cellpadding="0" align="right" border=0>
<tr>
<td class="pageTitle paddingTitle" >    
	<table>
	<%
            String re = (String)session.getAttribute(ModelConfigConstants.SESSION_SCREEN_CATEGORY);
            List modelList = DBDelegate.getModelListDropDown(re, request);
            
        %>
	 
	 
	 <tr>         
        		 
             <td height="60px" align="left" valign="top"  class="redtitle">
			  <strong class="pageTitle" style="padding-left:0px"><%if("config".equals(re)){%><bean:message key="dbconfig.config"/><%}else if("alerts".equals(re)) { %> <bean:message key="dbconfig.alerts"/><%}else{ %>
			  <bean:message key="dbconfig.platform"/>
			  <%} %>
			  <bean:message key="dbconfig.model"/><br/>
                 <span class="paddingTitleDesc bodytext" style="padding-left:0px"><bean:message key="dbconfig.data.description"/></span></strong>
			  </td>  		  
              <td width="67px">&nbsp;</td>	 
      </tr>		  
    <tr>
    
    <td>		
         
        <span id="ctl00_mainbody_lblError" class="error"><label class="success">
        <html:messages id="msg" message="true" ><bean:write name="msg"/></html:messages>         
        </label></span><br/>
	
	   <% if(request.getAttribute("errormsg") != null ){%>
	  <font color="red" style="font-family: Tahoma;font-size: 12px;"><b> <bean:message key="dbconfig.invalidModel.error"/></b></font>  
       <%}%>
        

        <table id="table2" height="54" cellspacing="2" cellpadding="2" class="border1">
		                    
            <tr>
                <td height="27" align="left" class="tableBgColor">
                    <table class="lfr-table"0>
                        <tbody>
						    <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td ><label class="bodytext"><strong><bean:message key="dbconfig.model"/> : </strong></label></td>

                                <td align="center"><select id = "selectedModelId" name="selectedModelId" dojoType="dijit.form.FilteringSelect">
                                    <% if(modelList != null)
                                    {
                                        for(int modIdx=0;modIdx<modelList.size(); modIdx++)
                                        {
                                            ModelBean modelBean = (ModelBean)modelList.get(modIdx);
                                            if(StringUtils.isNull(modelBean.getModelClassName()))
                                            {%> <option value=''><bean:message key="validation.msg.select" bundle="splchvalidation"/></option>
                                            <%}
                                            else{
                                    %>
                                        <option value="<%=modelBean.getModelClassName()%>"><%=modelBean.getModelName()%></option>
                                    <% } }} %>
                                    </select>
                                </td>

                            </tr>
                           
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                                <html:hidden name="masterMaintenanceHomeForm" property="selectedModelId"/>
                        </tbody>
                    </table>
                </td>
            </tr>
        </table>        
    </td>
	<td>&nbsp;</td>
     </tr>	    
     <tr>
        <td></td>
		<td>&nbsp;</td>
    </tr>
  </table>
 </td>
 </tr>
 
 <tr  height="30" colspan="3" class="barColor" align="right" >
       <td height="30" colspan="2"  width="890" align="right">	   
       <%if(request.getParameter("fromHeader")==null) {%> <button dojoType="dijit.form.Button"  id="idHome" name="btnHome" type="button" onClick="return submitForm(this)"> <bean:message key="dbconfig.home" /> </button>
        <button dojoType="dijit.form.Button"  id="idBack" name="btnBack" type="button" onClick="return submitForm(this)"> <bean:message key="dbconfig.back"/> </button>
        <%}%>
        <button dojoType="dijit.form.Button"  id="idView" name="btnView" type="button" onClick="return submitForm(this)"> <bean:message key="dbconfig.view"/> </button>
        <button dojoType="dijit.form.Button"  id="idCreate" name="btnCreate" type="button" onClick="return submitForm(this)"> <bean:message key="dbconfig.create"/> </button>
        <button dojoType="dijit.form.Button"  id="idImport2Excel" name="btnImport2Excel" type="button" onClick="return submitForm(this)"> <bean:message key="dbconfig.import2Excel"/> </button>
        <button dojoType="dijit.form.Button"  id="idExportFromExcel" name="btnExportFromExcel"   type="button" onClick="return submitForm(this)"> <bean:message key="dbconfig.exportFromExcel"/> </button>        
        </td>
 </tr>    

</table>
</html:form>
<script>
dojo.addOnLoad(loadFormValues); 
function loadFormValues(){
    dijit.byId('selectedModelId').setValue('');
}
</script>
</body>
</html:html>
