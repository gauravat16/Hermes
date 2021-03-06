package com.hermes.cloudmessaging.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

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

	List<RESP> read(REQ req);

	RESP update(REQ req);

	List<RESP> delete(REQ req);

	List<RESP> find(REQ req);

}
