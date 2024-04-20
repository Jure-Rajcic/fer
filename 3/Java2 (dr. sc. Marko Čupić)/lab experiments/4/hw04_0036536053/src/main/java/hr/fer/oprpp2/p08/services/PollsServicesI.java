package hr.fer.oprpp2.p08.services;

import java.util.List;

import hr.fer.oprpp2.p08.dao.error.DAOException;
import hr.fer.oprpp2.p08.model.Polls;

public interface PollsServicesI {

    public List<Polls> getAllPools() throws DAOException;
    public Polls getPoolForId(Integer id) throws DAOException;
    
}
