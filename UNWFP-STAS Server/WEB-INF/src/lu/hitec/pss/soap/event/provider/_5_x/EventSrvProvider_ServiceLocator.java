/**
 * EventSrvProvider_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package lu.hitec.pss.soap.event.provider._5_x;

public class EventSrvProvider_ServiceLocator extends org.apache.axis.client.Service implements lu.hitec.pss.soap.event.provider._5_x.EventSrvProvider_Service {

    public EventSrvProvider_ServiceLocator() {
    }


    public EventSrvProvider_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EventSrvProvider_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EventSrvProviderPort
    private java.lang.String EventSrvProviderPort_address = "http://mw-a1.service.emergency.lu/eventservice/in/soap/EventSrvProvider?wsdl";

    public java.lang.String getEventSrvProviderPortAddress() {
        return EventSrvProviderPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EventSrvProviderPortWSDDServiceName = "EventSrvProviderPort";

    public java.lang.String getEventSrvProviderPortWSDDServiceName() {
        return EventSrvProviderPortWSDDServiceName;
    }

    public void setEventSrvProviderPortWSDDServiceName(java.lang.String name) {
        EventSrvProviderPortWSDDServiceName = name;
    }

    public lu.hitec.pss.soap.event.provider._5_x.EventSrvProvider_PortType getEventSrvProviderPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EventSrvProviderPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEventSrvProviderPort(endpoint);
    }

    public lu.hitec.pss.soap.event.provider._5_x.EventSrvProvider_PortType getEventSrvProviderPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            lu.hitec.pss.soap.event.provider._5_x.EventSrvProviderPortBindingStub _stub = new lu.hitec.pss.soap.event.provider._5_x.EventSrvProviderPortBindingStub(portAddress, this);
            _stub.setPortName(getEventSrvProviderPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEventSrvProviderPortEndpointAddress(java.lang.String address) {
        EventSrvProviderPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (lu.hitec.pss.soap.event.provider._5_x.EventSrvProvider_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                lu.hitec.pss.soap.event.provider._5_x.EventSrvProviderPortBindingStub _stub = new lu.hitec.pss.soap.event.provider._5_x.EventSrvProviderPortBindingStub(new java.net.URL(EventSrvProviderPort_address), this);
                _stub.setPortName(getEventSrvProviderPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("EventSrvProviderPort".equals(inputPortName)) {
            return getEventSrvProviderPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://hitec.lu/pss/soap/event/provider/5.x", "EventSrvProvider");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://hitec.lu/pss/soap/event/provider/5.x", "EventSrvProviderPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EventSrvProviderPort".equals(portName)) {
            setEventSrvProviderPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}