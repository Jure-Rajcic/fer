package hr.fer.oprpp2.p08.services;

import java.util.List;

import hr.fer.oprpp2.p08.dao.error.DAOException;
import hr.fer.oprpp2.p08.model.PollOptions;

public interface PollOptionsServiceI {

    public List<PollOptions> getPoolOptionsForId(int id) throws DAOException;
    public void incrementVotesForId(int id) throws DAOException;
    public List<PollOptions> getAllPollOptions() throws DAOException;
    
}
