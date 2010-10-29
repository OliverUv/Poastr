package com.ollijepp.poastr.client.history;

import com.google.gwt.event.shared.EventHandler;

public interface CategoryChangeHandler extends EventHandler {
    void onCategoryChange(CategoryChangeEvent event);
}