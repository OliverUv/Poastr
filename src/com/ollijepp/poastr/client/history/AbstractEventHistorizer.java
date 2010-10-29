package com.ollijepp.poastr.client.history;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * Historical events are the kind of events a history handler will
 * be able to replicate. To replicate them, they must have a string
 * representation that does not depend on client or server state.
 * That is, all info necessary to build a new copy of the event
 * must be available in string representation. This requirement
 * exists to allow bookmarks to point into the application.
 * 
 * Be aware that any information here will be visible to the website
 * users in the URL, and that they may change any of this information
 * however they want. It is not recommended to make public application
 * logic that may compromise your security, or automatically do things
 * such as posting messages, etc. That is, events with side-effects
 * should not be historized.
 *  
 * @author Oliver Uvman
 *
 */
public abstract class AbstractEventHistorizer {
	
	private GwtEvent.Type eventType;			//used by HistoryManager to subscribe to eventBus
	
	
	private String uniqueIdentifier;			//used by HistoryManager to decode strings with correct decoder
												//MUST BE UNIQUE, changing after first use may break people's bookmarks
												//would have used stringified GwtEvent.Type, but I'm not sure those
												//stay the same over different compilations.
	
	public AbstractEventHistorizer(GwtEvent.Type eventType, String uniqueIdentifier) {
		super();
		this.eventType = eventType;
		this.uniqueIdentifier = uniqueIdentifier;
	}

	
	/**
	 * Implement this to encode the event as a string. You do not need to
	 * encode information about which type of event this is, that is handled
	 * by the HistoryHandler via the uniqueIdentifier.
	 */
	abstract protected String encode(GwtEvent<?> event);
	
	/**
	 * Implement this to take the string you generated in encode,
	 * and return an event of the type you encoded.
	 * 
	 * @param eventRepresentation String representation of your event
	 * @return Copy of event that was represented by string
	 */
	abstract protected GwtEvent decode(String eventRepresentation);

	public GwtEvent.Type getEventType() {
		return eventType;
	}


	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}


	/**
	 * Takes an event and returns a string representation of that event.
	 * @param event to turn into string
	 * @return string representing the event
	 */
	public final String getStringRepresentation(GwtEvent<?> event){
		return HistoryHandler.appendIdentifier(uniqueIdentifier, encode(event));
	}
	
	/**
	 * Takes a string representation of an event created by getStringRepresentation
	 * and turns it into an equivalent event.
	 * @param eventRepresentation
	 * @return
	 */
	public final GwtEvent<?> buildEvent(String eventRepresentation){
		return decode(HistoryHandler.removeIdentifier(eventRepresentation));
	}
	
}
