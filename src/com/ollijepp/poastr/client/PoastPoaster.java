package com.ollijepp.poastr.client;

import java.util.ArrayList;
import java.util.Stack;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ollijepp.poastr.client.events.PoastPoastedEvent;
import com.ollijepp.poastr.client.events.PoastPoastsEvent;
import com.ollijepp.poastr.client.events.ProcessPoastsEvent;
import com.ollijepp.poastr.shared.socialService.PoastBuilder;
import com.ollijepp.poastr.shared.socialService.ServiceProvider;
import com.ollijepp.poastr.shared.socialService.SocialServiceFactory;

@Singleton
public class PoastPoaster implements
ProcessPoastsEvent.Handler,
PoastPoastsEvent.Handler{
	private EventBus eventBus;
	private Stack<PoastBuilder> poastQueue;

	@Inject
	public PoastPoaster(EventBus eventBus) {
		this.eventBus = eventBus;
		this.eventBus.addHandler(ProcessPoastsEvent.TYPE, this);
		this.eventBus.addHandler(PoastPoastsEvent.TYPE, this);
		this.poastQueue = new Stack<PoastBuilder>();
	}

	/**
	 * Make sure poasts that need shortening or translation get that done,
	 * then somehow delegate on to poasting with a PoastPoastsEvent.
	 */
	@Override
	public void processPoasts(ProcessPoastsEvent event) {
		Log.info("In processPoasts");
		boolean needsProcessing = false;
		for (PoastBuilder i : event.getPoasts()){
			Log.info("In processPoasts for-loop");
			if(i.needsShortening()){
				Log.info("In processPoasts for-loop needs shortening");
				needsProcessing = true;
			}
		}
		if (needsProcessing){
			Log.info("In processPoasts needs processing");
			//TODO: Show processing window (translation checks, shortening),
			//which then fires PoastPoastsEvent when it is done.
		} else {
			Log.info("In processPoasts fire poastpoasts");
			eventBus.fireEvent(new PoastPoastsEvent(event.getPoasts()));
		}
	}

	@Override
	public void poastPoasts(PoastPoastsEvent event) {
		Stack<PoastBuilder> poasts = new Stack<PoastBuilder>();
		poastQueue.addAll(event.getPoasts());
		poastPoasts();
	}
	
	private void poastPoasts(){
		if (poastQueue.empty()){
			Log.info("PoastPoaster::poastPoasts: hit end of list");
			return;
		} else {
			PoastBuilder first = poastQueue.pop();
			Log.info("PoastPoaster::poastPoasts: poasting " + first.getMessage() + " to " + first.getAccount().getServiceProvider());
			try {
				first.poast(new RequestCallback() {
					
					@Override
					public void onResponseReceived(Request request, Response response) {
						poastPoasts();
						// TODO Fix proper success reporting
					}
					
					@Override
					public void onError(Request request, Throwable exception) {
						// TODO Fix proper error handling / reporting
						
					}
				});
			} catch (RequestException e) {
				// TODO Fix proper error handling / reporting
				e.printStackTrace();
			}
		}
	}
	
}
