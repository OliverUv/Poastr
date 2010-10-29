package com.ollijepp.poastr.client.history;

import java.util.HashMap;
import java.util.LinkedList;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.ollijepp.poastr.client.EventBus;
import com.ollijepp.poastr.client.exceptions.history.EventNotHistorizableException;
import com.ollijepp.poastr.client.exceptions.history.EventTypeAlreadyRegisteredException;
import com.ollijepp.poastr.client.exceptions.history.IdUsedException;


/**
 * The history handler automates an application's dealing with browser history.
 * It assumes that all history worthy events are pushed on an event bus, which
 * it takes as argument in its constructor. 
 * 
 * All event types that are to be recognized by the history handler need to be
 * registered with it before they can be historized. This is usually done by the
 * sending an {@link AbstractEventHistorizer} to the registerEventType method. Your
 * HistoryHandler must also implement the handler interfaces for events to be
 * historized. The onEventHappens methods MUST call onHistoricalEvent and pass
 * on the event in the parameter.
 * 
 * @author Oliver Uvman
 *
 */
public abstract class HistoryHandler implements EventHandler, ValueChangeHandler<String> {
	
	public static char identifierSeparator = ':';

	private EventBus eventBus; //the application's eventBus
	
	private HashMap<String, AbstractEventHistorizer> eventsById;				//to infer which decoder to use
	private HashMap<GwtEvent.Type, AbstractEventHistorizer> eventsByType;	//to infer which encoder to use, could have required historical
																			//events to publish same unique eventType as builder, but that would have
																			//caused overhead per event instead of per event type
	
	public HistoryHandler(EventBus eventBus) {
		this.eventBus = eventBus;
		this.eventsById = new HashMap<String, AbstractEventHistorizer>();
		this.eventsByType = new HashMap<GwtEvent.Type, AbstractEventHistorizer>();
		History.addValueChangeHandler(this);
	}
	
	/**
	 * Saves the event builder for later encoding/decoding of events
	 * and registers for this type of events with the eventBus
	 * if no event with this eventType or type has already been registered.
	 * 
	 * @param eventHistorizier The event builder for the type of event to historize
	 */
	public void registerEventType(AbstractEventHistorizer eventHistorizier) throws IdUsedException, EventTypeAlreadyRegisteredException {
		if(eventHistorizier.getUniqueIdentifier().equals("")
			|| eventsById.containsKey(eventHistorizier.getUniqueIdentifier())){
			throw new IdUsedException(eventHistorizier.getUniqueIdentifier());
		} else if (eventsByType.containsKey(eventHistorizier.getEventType())) {
			throw new EventTypeAlreadyRegisteredException(eventHistorizier.getEventType());
		}else{
			eventsById.put(eventHistorizier.getUniqueIdentifier(), eventHistorizier);
			eventsByType.put(eventHistorizier.getEventType(), eventHistorizier);
			eventBus.addHandler(eventHistorizier.getEventType(), this);
		}
	}

	public void setState(String token) {
			eventBus.fireEvent(buildEvent(token));
	}
	
	/**
	 * Starts the application. If no history is provided (eg the user
	 * started the app through a bookmark or by a copied url with history)
	 * the application is started by firing firstEvent, which is also
	 * historized. Otherwise, an event which is built from the history is
	 * stored as the first event and then fired.
	 * 
	 * @param firstEvent First event to fire if the app is "cold started"
	 * @throws EventNotHistorizableException
	 */
	public void startApplication(GwtEvent firstEvent) throws EventNotHistorizableException {
		if(!eventsByType.containsKey(firstEvent.getAssociatedType())){
			throw new EventNotHistorizableException(firstEvent.getAssociatedType().toString());
		}
		
		String initialState = History.getToken();
		if(initialState.length() == 0){
			onHistoricalEvent(firstEvent); //Registers event in history
			eventBus.fireEvent(firstEvent);
		}else{
			onHistoricalEvent(buildEvent(initialState));
			setState(initialState);
		}
	}

	/**
	 * Gets the correct {@link AbstractEventHistorizer} and uses it to build a new event.
	 * @param stringRepresentation
	 * @return
	 */
	private GwtEvent<?> buildEvent(String stringRepresentation) {
		return getHistorizer(stringRepresentation).buildEvent(stringRepresentation);
	}
	
	private AbstractEventHistorizer getHistorizer(String representation){
		return eventsById.get(getIdentifier(representation));
	}
	
	private AbstractEventHistorizer getHistorizer(GwtEvent.Type eventType){
		return eventsByType.get(eventType);
	}

	/**
	 * Is triggered when the eventBus fires an event that should
	 * be added to the browser's history. Uses the event's type to
	 * fetch a builder for it, then uses that builder to encode
	 * a string to put in the history.
	 * @param event to historize
	 */
	public void onHistoricalEvent(GwtEvent<?> event){
		String representation
			= getHistorizer(event.getAssociatedType()).getStringRepresentation(event);
		History.newItem(representation, false);
	}

	public static String appendIdentifier(String uniqueIdentifier, String eventRepresentation) {
		return uniqueIdentifier + identifierSeparator + eventRepresentation;
	}

	public static String getIdentifier(String eventRepresentation){
		return eventRepresentation.substring(0,eventRepresentation.indexOf(identifierSeparator));
	}

	public static String removeIdentifier(String eventRepresentation) {
		return eventRepresentation.substring(eventRepresentation.indexOf(identifierSeparator)+1,
				 eventRepresentation.length());
	}


	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		setState(event.getValue());		
	}



}
