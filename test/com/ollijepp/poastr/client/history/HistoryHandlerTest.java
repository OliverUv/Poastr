package com.ollijepp.poastr.client.history;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.History;
import com.ollijepp.poastr.client.EventBus;
import com.ollijepp.poastr.client.exceptions.history.EventNotHistorizableException;
import com.ollijepp.poastr.client.exceptions.history.EventTypeAlreadyRegisteredException;
import com.ollijepp.poastr.client.exceptions.history.IdUsedException;

public class HistoryHandlerTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.ollijepp.poastr.Poastr";
	}
	
	String id;
	String rep;
	
	@Override
	protected void gwtSetUp() {
		id = "a";
		rep = "asdf";
	}
	
	public void testAppendIdentifier() {

		char sep = HistoryHandler.identifierSeparator;
		String res = HistoryHandler.appendIdentifier(id, rep);
		String expected = id + sep + rep;
		assertEquals(expected, res);
		//The reason we care about the representation of the string
		//is for forward compatibility, if we change representation,
		//old bookmarks will stop working.
		testRemoveIdentifier();
		testGetIdentifier();
	}
	
	public void testRemoveIdentifier() {
		String id = "a";
		String rep = "asdf";
		String res = HistoryHandler.appendIdentifier(id, rep);
		res = HistoryHandler.removeIdentifier(res);
		assertEquals(rep, res);
	}
	
	public void testGetIdentifier() {
		String id = "a";
		String rep = "asdf";
		String res = HistoryHandler.appendIdentifier(id, rep);
		res = HistoryHandler.getIdentifier(res);
		assertEquals(id, res);
	}
	
	class EventTester implements CategoryChangeHandler {
		
		public int timesCalled = 0;
		@Override
		public void onCategoryChange(CategoryChangeEvent event) {
			List<String> l = event.getCategories();
			for (String string : l) {
				System.out.print(string + " ");
				System.out.println();
			}
			switch(timesCalled){
			case 0:
				assertEquals(1, l.size());
				assertEquals("FIRST", l.get(0));
				break;
			case 1:
				assertEquals(2, l.size());
				assertEquals("FIRST", l.get(0));
				assertEquals("SECOND", l.get(1));
				break;
			case 2:
				assertEquals(3, l.size());
				assertEquals("FIRST", l.get(0));
				assertEquals("SECOND", l.get(1));
				assertEquals("THIRD", l.get(2));
				break;
			case 3:
				assertEquals(2, l.size());
				assertEquals("FIRST", l.get(0));
				assertEquals("SECOND", l.get(1));
				break;
			case 4:
				assertEquals(1, l.size());
				assertEquals("FIRST", l.get(0));
				break;
			case 5:
				assertEquals(1, l.size());
				assertEquals("FIRST", l.get(0));
				break;
			}
			timesCalled++;
		}
	}
	
	public void testHistoryHandler(){
		EventBus eventBus = new EventBus();
		EventTester eventHandler = new EventTester();
		eventBus.addHandler(CategoryChangeEvent.TYPE, eventHandler);
		ArrayList<String> l = new ArrayList<String>();
		l.add("FIRST");
		
		HistoryHandler h = new CategoryHistoryHandler(eventBus);
		
		try {
			h.registerEventType(new CategoryChangeEventBuilder(
					CategoryChangeEvent.TYPE, "cc"));
			h.startApplication(new CategoryChangeEvent(l)); //FIRST
		} catch (IdUsedException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (EventNotHistorizableException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (EventTypeAlreadyRegisteredException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
		l.add("SECOND");
		eventBus.fireEvent(new CategoryChangeEvent(l)); //FIRST SECOND
		
		l.add("THIRD");
		eventBus.fireEvent(new CategoryChangeEvent(l)); //FIRST SECOND THIRD
		
		/* These tests fail, presumably because History.back() has not been
		 * implemented in the testing browser. Has been verified to work in
		 * real world.*/
		History.back();//FIRST SECOND
		History.back();//FIRST
		
		assertEquals(5, eventHandler.timesCalled);
	}
}