package com.ollijepp.poastr.client.history;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class CategoryChangeEvent extends GwtEvent<CategoryChangeHandler> {

    private final List<String> category;

    public CategoryChangeEvent(List<String> category) {
        super();
        this.category = category;
    }

    public static final Type<CategoryChangeHandler> TYPE = new Type<CategoryChangeHandler>();

    @Override
    protected void dispatch(CategoryChangeHandler handler) {
        handler.onCategoryChange(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<CategoryChangeHandler> getAssociatedType() {
        return TYPE;
    }

    public List<String> getCategories(){
        return category;
    }

}