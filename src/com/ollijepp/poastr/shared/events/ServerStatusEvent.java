package com.ollijepp.poastr.shared.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ServerStatusEvent extends GwtEvent<ServerStatusEvent.Handler> {
	public interface Handler extends EventHandler {
		public void onServerStatusChange(ServerStatusEvent event);
	}
	
	public static Type<ServerStatusEvent.Handler> TYPE = new Type<ServerStatusEvent.Handler>();

	public enum ServerStatus {
		Unknown, Available, Unavailable, Error
	}

	private ServerStatus status = ServerStatus.Unknown;

	public ServerStatusEvent(ServerStatus status) {
		this.status = status;
	}

	public ServerStatus getStatus() {
		return status;
	}

	@Override
	protected void dispatch(ServerStatusEvent.Handler handler) {
		handler.onServerStatusChange(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ServerStatusEvent.Handler> getAssociatedType() {
		return TYPE;
	}
}
