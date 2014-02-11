package com.md.mechevo.game;


/**
 * Defines that the element can notify the EventObserver of it's events.
 */
public interface EventObservable {

	public void registerEventObserver(EventObserver eventObserver);
	
	public void notifyEventObserver(EventData eventData);

}
