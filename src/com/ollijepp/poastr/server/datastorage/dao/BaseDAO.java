package com.ollijepp.poastr.server.datastorage.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Embedded;
import javax.persistence.Transient;

import com.allen_sauer.gwt.log.client.Log;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.helper.DAOBase;
import com.ollijepp.poastr.server.oauth.OAuthToken;
import com.ollijepp.poastr.shared.socialService.AccountOwner;
import com.ollijepp.poastr.shared.socialService.Facebook;
import com.ollijepp.poastr.shared.socialService.ServiceProvider;
import com.ollijepp.poastr.shared.socialService.SocialService;
import com.ollijepp.poastr.shared.socialService.SocialServiceFactory;
import com.ollijepp.poastr.shared.socialService.Twitter;

/**
 * Is extended for each class that we want to put into 
 * the datastore using objectify.
 * 
 * @author Oliver Uvman
 *
 * @param <T>
 */
public class BaseDAO<T> extends DAOBase {
	static final int BAD_MODIFIERS = Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT;
	static { //Makes sure these are initialized
		ObjectifyService.register(AccountOwner.class);
		ObjectifyService.register(OAuthToken.class);
		ObjectifyService.register(SocialService.class);
		//Register Facebook, Twitter, etc.
		for(ServiceProvider sp : SocialServiceFactory.getProviders()){
			ObjectifyService.register(sp.getSocialServiceClass());
		}
	}
	protected Class<T> clazz;

	/**
	 * We've got to get the associated domain class somehow
	 * 
	 * @param clazz
	 */
	protected BaseDAO(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Key<T> put(T entity) {
		return ofy().put(entity);
	}

	public Map<Key<T>, T> putAllTransaction(Iterable<T> entities) {
		return ofy().put(entities);
	}

	public void delete(T entity) {
		ofy().delete(entity);
	}

	public void deleteKey(Key<T> entityKey) {
		ofy().delete(entityKey);
	}

	public void deleteAll(Iterable<T> entities) {
		ofy().delete(entities);
	}

	public void deleteKeys(Iterable<Key<T>> keys) {
		ofy().delete(keys);
	}

	public T get(Long id) throws EntityNotFoundException {
		return ofy().get(this.clazz, id);
	}

	public T get(Key<T> key) throws EntityNotFoundException {
		return ofy().get(key);
	}
	
	public List<T> getDescendants(Key ancestorKey){
		Query<T> q = ofy().query(clazz);
		q = q.ancestor(ancestorKey);
		return q.list();
	}

	/**
	 * Lots of methods from the sixfixmix project, just useful
	 * as reference material.
	 ******
	public T getByProperty(String propName, Object propValue) {
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return q.get();
	}

	public List<T> listAllForPage(int offset, int limit, String order) {
		Query<T> q = ofy().query(clazz);
		q.order(order);
		q.offset(offset);
		q.limit(limit);
		return asList(q.fetch());
	}

	public List<T> listByProperty(String propName, Object propValue) {
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return asList(q.fetch());
	}

	public List<Key<T>> listKeysByProperty(String propName, Object propValue) {
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return asKeyList(q.fetchKeys());
	}

	public T getByExample(T exampleObj) {
		Query<T> queryByExample = buildQueryByExample(exampleObj);
		Iterable<T> iterableResults = queryByExample.fetch();
		Iterator<T> i = iterableResults.iterator();
		T obj = i.next();
		if (i.hasNext())
			throw new RuntimeException("Too many results");
		return obj;
	}

	public List<T> listByExample(T exampleObj) {
		Query<T> queryByExample = buildQueryByExample(exampleObj);
		return asList(queryByExample.fetch());
	}

	private List<T> asList(Iterable<T> iterable) {
		ArrayList<T> list = new ArrayList<T>();
		for (T t : iterable) {
			list.add(t);
		}
		return list;
	}

	private List<Key<T>> asKeyList(Iterable<Key<T>> iterableKeys) {
		ArrayList<Key<T>> keys = new ArrayList<Key<T>>();
		for (Key<T> key : iterableKeys) {
			keys.add(key);
		}
		return keys;
	}

	private Query<T> buildQueryByExample(T exampleObj) {
		Query<T> q = ofy().query(clazz);

		// Add all non-null properties to query filter
		for (Field field : clazz.getDeclaredFields()) {
			// Ignore transient, embedded, array, and collection properties
			if (field.isAnnotationPresent(Transient.class) || (field.isAnnotationPresent(Embedded.class))
					|| (field.getType().isArray()) || (Collection.class.isAssignableFrom(field.getType()))
					|| ((field.getModifiers() & BAD_MODIFIERS) != 0))
				continue;

			field.setAccessible(true);

			Object value;
			try {
				value = field.get(exampleObj);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			if (value != null) {
				q.filter(field.getName(), value);
			}
		}

		return q;
	}
	*/
}
