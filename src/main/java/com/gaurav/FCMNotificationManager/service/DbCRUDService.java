package com.gaurav.FCMNotificationManager.service;

/**
 * 
 * @author gaurav
 *
 * @param <E>
 * @param <REQ>
 * @param <RESP>
 * @param <ID>
 */
public interface DbCRUDService<E, REQ, RESP, ID> {

	E mapRequestToEntity(REQ request);

	RESP mapEntityToResponse(E entity);

	RESP create(REQ req);

	RESP read(REQ req);

	RESP update(REQ req);

	RESP delete(REQ req);

	RESP find(REQ req);

}
