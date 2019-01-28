package com.tt.thingworx.thing;



import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;

import java.util.Random;

import org.slf4j.Logger;


import com.thingworx.metadata.annotations.ThingworxPropertyDefinition;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinitions;
import com.thingworx.metadata.annotations.ThingworxServiceDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceParameter;
import com.thingworx.metadata.annotations.ThingworxServiceResult;
import com.thingworx.relationships.RelationshipTypes.ThingworxEntityTypes;
import com.thingworx.types.collections.ValueCollection;
import com.thingworx.types.primitives.StringPrimitive;

@ThingworxPropertyDefinitions(properties = {	
		@ThingworxPropertyDefinition(name="Pacjent_id", description="", baseType="STRING",
		aspects={"dataChangeType:ALWAYS",
                "dataChangeThreshold:0",
                "cacheTime:0", 
                "isPersistent:FALSE", 
                "isReadOnly:FALSE", 
                "pushType:ALWAYS", 
                "isFolded:FALSE",
                "defaultValue:0"}),		
		@ThingworxPropertyDefinition(name="Temperatura", description="", baseType="NUMBER",
		aspects={"dataChangeType:ALWAYS",
                        "dataChangeThreshold:0",
                        "cacheTime:0", 
                        "isPersistent:FALSE", 
                        "isReadOnly:FALSE", 
                        "pushType:ALWAYS", 
                        "isFolded:FALSE",
                        "defaultValue:0"}),
		@ThingworxPropertyDefinition(name="CisnienieNad", description="", baseType="NUMBER",
		aspects={"dataChangeType:ALWAYS",
                        "dataChangeThreshold:0",
                        "cacheTime:0", 
                        "isPersistent:FALSE", 
                        "isReadOnly:FALSE", 
                        "pushType:ALWAYS", 
                        "isFolded:FALSE",
                        "defaultValue:0"}),
		@ThingworxPropertyDefinition(name="CisnieniePod", description="", baseType="NUMBER",
		aspects={"dataChangeType:ALWAYS",
                "dataChangeThreshold:0",
                "cacheTime:0", 
                "isPersistent:FALSE", 
                "isReadOnly:FALSE", 
                "pushType:ALWAYS", 
                "isFolded:FALSE",
                "defaultValue:0"})
})


public class Pacjent2_template extends VirtualThing implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(Pacjent2_template.class);
	
	private final static String PACJENT_FIELD = "Pacjent_id";
	private final static String TEMPERATURA_FIELD = "Temperatura";
	private final static String CISNIENIENAD_FIELD = "CisnienieNad";
	private final static String CISNIENIEPOD_FIELD = "CisnieniePod";
	private String pacjent_id;
	private Double temperatura;
	private Double cisnienieNad;
	private Double cisnieniePod;
	private String thingName = null;
	
	public Pacjent2_template(String name, String description, ConnectedThingClient client) {
		super(name, description, client);
		this.getThingShape();
		this.getBindingName();
		thingName = name;
		// Populate the thing shape with the properties, services, and events that are annotated in this code
		try {
			super.initializeFromAnnotations();
		} catch (Exception ex) {
			LOG.error("Not work", ex);
		}
		this.init();
	}
	
	// From the VirtualThing class
		// This method will get called when a connect or reconnect happens
		// Need to send the values when this happens
		// This is more important for a solution that does not send its properties on a regular basis
	public void synchronizeState() {
		// Be sure to call the base class
		super.synchronizeState();
		// Send the property values to ThingWorx when a synchronization is required
		super.syncProperties();
	}

	
	private void init() {
		pacjent_id = "2";
		temperatura = new Double(41);
		cisnienieNad = new Double(190);
		cisnieniePod = new Double(80);
	}
	
	@Override
	public void run() {
		try {
			// Delay for a period to verify that the Shutdown service will return
			Thread.sleep(1000);
			// Shutdown the client
			this.getClient().shutdown();
		} catch (Exception ex) {
			LOG.error("Error " + thingName, ex);
		}
		
	}
	
	@Override
	public void processScanRequest() throws Exception {
		temperatura = temperatura + (Math.random()*20 - 10);
		if(temperatura < 0){
			temperatura = new Double(0);
		}
		if(temperatura > 39){
			temperatura = new Double(39);
		}
		
		cisnieniePod = cisnieniePod + (Math.random()*20 - 10);
		if(cisnieniePod < 0){
			cisnieniePod = new Double(0);
		}
		if(cisnieniePod > 200){
			cisnieniePod = new Double(200);
		}
		
		cisnienieNad = cisnienieNad + (Math.random()*20 - 10);
		if(cisnienieNad < 0){
			cisnienieNad = new Double(0);
		}
		if(cisnienieNad > 200){
			cisnienieNad = new Double(200);
		}
		
		pacjent_id="jeden";
		
		setPacjentID();
		setTemperatura();
		setCisnienieNad();
		setCisnieniePod();
		this.updateSubscribedProperties(5000);
	}

	public void setPacjentID() throws Exception {
		setProperty(PACJENT_FIELD, this.pacjent_id);
	}

	public void setTemperatura() throws Exception {
		setProperty(TEMPERATURA_FIELD, this.temperatura);
	}

	public void setCisnienieNad() throws Exception {
		setProperty(CISNIENIENAD_FIELD, this.cisnienieNad);
	}
	public void setCisnieniePod() throws Exception {
		setProperty(CISNIENIEPOD_FIELD, this.cisnieniePod);
	}
		
	/**
	@ThingworxServiceDefinition(name="AddTwoNumbers", description="add operation")
	@ThingworxServiceResult(name="result", description="Result", baseType="NUMBER")
	public Double AddTwoNumbers( 
		@ThingworxServiceParameter( name="num1", description="Value 1", baseType="NUMBER") Double num1,
		@ThingworxServiceParameter( name="num2", description="Value 2", baseType="NUMBER") Double num2) throws Exception {
		LOG.info("Add two numbers: " + num1.doubleValue() + " and " + num2.doubleValue());
		
		return num1 + num2;
	}*/
	
}

