package com.md.mechevo.game;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

/**
 * Event Observer is an Observer that register all the happenings that occur. Later this will be
 * used to generate the report.
 */
public class EventObserver {

	/**
	 * Current time for the events. All events that are added are assigned to the time this variable
	 * holds.
	 */
	private float currentTime;

	/**
	 * Holds all the EventDatas that have been received.
	 */
	private ArrayList<SimpleImmutableEntry<Float, EventData>> events;


	/**
	 * Constructor for the EventObserver.
	 */
	public EventObserver() {
		this.currentTime = 0;
		this.events = new ArrayList<SimpleImmutableEntry<Float, EventData>>();
	}

	/**
	 * Get notified to add the given EventData.
	 * 
	 * @see com.md.mechevo.game.EventData
	 */
	public void notify(EventData event) {
		this.events.add(new SimpleImmutableEntry<Float, EventData>(new Float(this.currentTime),
						event));
	}


	/**
	 * Generate the event report.
	 * 
	 * @return The report in GSON object format
	 */
	public JsonElement generateReport() {
		JsonArray head = new JsonArray();
		for (SimpleImmutableEntry<Float, EventData> entry : this.events) {
		
			// build the head of an Event entry
			JsonObject event = new JsonObject();
			event.addProperty("time", entry.getKey());
			event.addProperty("title", entry.getValue().getTitle());
			
			// build its attributes
			JsonObject attributes = new JsonObject();
			for (SimpleImmutableEntry<String, String> attr : entry.getValue().getAttributes()) {
				attributes.addProperty(attr.getKey(), attr.getValue());
			}
			event.add("attrs", attributes);
			
			head.add(event);
		}
		return head;
	}
		
		
	
	
	
	
	/**
	 * Set current time for events added afterwards.
	 */
	public void setCurrentTime(float time) {
		this.currentTime = time;
	}


}
