package hr.fer.oprpp2.p08.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp2.p08.dao.error.DAOException;
import hr.fer.oprpp2.p08.model.PollOptions;
import hr.fer.oprpp2.p08.services.PollOptionsServiceI;

public class SQLPollOptionsServiceImpl implements PollOptionsServiceI{

    @Override
    public List<PollOptions> getPoolOptionsForId(int id) throws DAOException {
        List<PollOptions> result = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
        String query = "" + 
            "select * from PollOptions" +
            " where pollID = " + id + 
            " order by id";
		try (
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
        ){
            while(rs!=null && rs.next()) {
                PollOptions pollOptions = new PollOptions();
                pollOptions.setId(rs.getInt(1));
                pollOptions.setOptionTitle(rs.getString(2));
                pollOptions.setOptionLink(rs.getString(3));
                pollOptions.setPollID(rs.getInt(4));
                pollOptions.setVotesCount(rs.getInt(5));
                result.add(pollOptions);
            }
		} catch(Exception ex) {
			throw new DAOException("Error while getting list of pollOptions.", ex);
		}
		return result;
    }

    @Override
    public void incrementVotesForId(int id) throws DAOException {
        Connection con = SQLConnectionProvider.getConnection();
        String query = "" + 
            "update PollOptions" +
            " set votesCount = votesCount + 1" +
            " where id = " + id;
        try (
            PreparedStatement pst = con.prepareStatement(query);
        ){
            pst.executeUpdate();
        } catch(Exception ex) {
            throw new DAOException("Error while incrementing votes for id " + id, ex);
        }
    }

    @Override
    public List<PollOptions> getAllPollOptions() throws DAOException {
        List<PollOptions> result = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        String query = "" + 
            "select * from PollOptions" +
            " order by votesCount desc";
        try (
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
        ){
            while(rs!=null && rs.next()) {
                PollOptions pollOptions = new PollOptions();
                pollOptions.setId(rs.getInt(1));
                pollOptions.setOptionTitle(rs.getString(2));
                pollOptions.setOptionLink(rs.getString(3));
                pollOptions.setPollID(rs.getInt(4));
                pollOptions.setVotesCount(rs.getInt(5));
                result.add(pollOptions);
            }
        } catch(Exception ex) {
            throw new DAOException("Error while getting list of pollOptions.", ex);
        }
        return result;
    }
    
}
