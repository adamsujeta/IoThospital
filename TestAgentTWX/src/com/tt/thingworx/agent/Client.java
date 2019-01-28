package com.tt.thingworx.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.relationships.RelationshipTypes.ThingworxEntityTypes;
import com.thingworx.types.collections.ValueCollection;
import com.tt.thingworx.thing.Pacjent;
import com.tt.thingworx.thing.Pacjent2_template;

public class Client extends ConnectedThingClient{

	
	private static final Logger LOG = LoggerFactory.getLogger(Client.class);
	
	public Client(ClientConfigurator config) throws Exception {
		super(config);
	}
	
	public static void startConnection(){
		ClientConfigurator config = new ClientConfigurator();
		
		LOG.info("START");
		//TODO
		config.setUri("ws://localhost:8081/Thingworx/WS");
		config.setAppKey("df97bb28-dc05-451f-96fa-94596311d5c6");
		config.ignoreSSLErrors(true);
		try {
			Client client = new Client(config);
			client.start();
			
			while(!client.getEndpoint().isConnected()) {
				Thread.sleep(1000);
				LOG.info("WAIT");
			}
			
			
			for(Pacjent car : Pacjent.values()){
				ValueCollection params = new ValueCollection();
				params.SetStringValue("ThingName",car.name);
				client.invokeService(ThingworxEntityTypes.Things, "Helper_pacjent", "CreateThing", params, 5000);
				
			}
			
			for(Pacjent car : Pacjent.values()){
				Pacjent2_template thing1 = new Pacjent2_template(car.name, "", client);
				client.bindThing(thing1);
			}
	
			
			
			while(!client.isShutdown()) {
				if(client.isConnected()) {
					LOG.info("SEND");
					for(VirtualThing thing : client.getThings().values()) {
						try {
							thing.processScanRequest();
						}
						catch(Exception eProcessing) {
							System.out.println("Error Processing Scan Request for [" + thing.getName() + "] : " + eProcessing.getMessage());
						}
					}
					LOG.info("SLEEP");
					Thread.sleep(1000 * 60);
				}
			}
			LOG.info("END");
			
			
		} catch (Exception e) {
			LOG.info("ERROR");
			e.printStackTrace();
		}
		
	}
	

}
