package com.md.mechevo.game;

import java.util.ArrayList;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * Event Observer is an Observer that register all the happenings that occur.
 * Later this will be used to generate the report.
 */
public class EventObserver {

	/**
	 * Current time for the events.
	 * All events that are added are assigned to the time this variable holds.
	 */
	private float currentTime;
	
	/**
	 * Holds all the EventDatas that have been received.
	 */
	private ArrayList< SimpleImmutableEntry<Float, EventData> > events;
	
	
	/**
	 * Constructor for the EventObserver.
	 */
	public EventObserver() {
		this.currentTime = 0;
		this.events = new ArrayList< SimpleImmutableEntry<Float, EventData> >();
	}
	
	/**
	 * Get notified to add the given EventData.
	 *
	 * @see com.md.mechevo.game.EventData
	 */
	public void notify(EventData event) {
		this.events.add(new SimpleImmutableEntry<>(new Float(this.currentTime), event));
	}
	
	
	/**
	 * Set current time for events added afterwards.
	 */
	public void setCurrentTime(float time) {
		this.currentTime = time;
	}
	
	
}
	
	

	
