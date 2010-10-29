package com.ollijepp.poastr.client.history;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.junit.client.GWTTestCase;

public class HistoricalEventBuilderTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.ollijepp.poastr.Poastr";
	}
		
	//Let's build some events and stuff to test this thing,
	//we'll use a hypothetical category change event to do this.
	//It is suitable because then we can make use of encoding
	//and decoding.
	
	//The event contains a list of categories (strings), which we
	//will represent as strings separated by commas. For our testing
	//purposes no commas will appear in the strings, of course.
	
	String id;
	ArrayList<String> l;
	CategoryChangeEventBuilder builder;
	String rep;
	CategoryChangeEvent event;
	@Override
	protected void gwtSetUp() {
		id = "cc";
		builder = new CategoryChangeEventBuilder(CategoryChangeEvent.TYPE, id);
		l = new ArrayList<String>();
		l.add("aab");
		l.add("bbc");
		l.add("cc");
		l.add("dd");
		l.add(" ");
		l.add("e");
		rep = "cc" + HistoryHandler.identifierSeparator
			+ "aab,bbc,cc,dd, ,e";
		event = new CategoryChangeEvent(l);
	}
	
	
	public void testHistoricalEventBuilder(){
		assertEquals(id, builder.getUniqueIdentifier());
		assertEquals(CategoryChangeEvent.TYPE, builder.getEventType());
	}
	
	public void testGetStringRepresentation(){
		assertEquals(rep, builder.getStringRepresentation(event));
	}
	
	/**
	 * We test if the new event has been built correctly by making
	 * sure the information we encoded has ended up in there correctly.
	 * No extra elements, no elements to few.
	 */
	public void testBuildEvent(){
		CategoryChangeEvent e = (CategoryChangeEvent) builder.buildEvent(rep);
		List<String> newL = e.getCategories();
		assertEquals(l.size(), newL.size());
		assertTrue(newL.containsAll(l));
		assertTrue(l.containsAll(newL));
	}


}
