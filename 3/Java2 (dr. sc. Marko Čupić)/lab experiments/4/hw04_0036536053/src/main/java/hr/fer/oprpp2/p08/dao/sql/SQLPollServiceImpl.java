package hr.fer.oprpp2.p08.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp2.p08.dao.error.DAOException;
import hr.fer.oprpp2.p08.model.Polls;
import hr.fer.oprpp2.p08.services.PollsServicesI;

public class SQLPollServiceImpl implements PollsServicesI {

    @Override
    public List<Polls> getAllPools() throws DAOException {
        List<Polls> result = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        String query = "select * from Polls order by id";
        try (
                PreparedStatement pst = con.prepareStatement(query);
                ResultSet rs = pst.executeQuery();) {
            while (rs != null && rs.next()) {
                Polls polls = new Polls();
                polls.setId(rs.getLong(1));
                polls.setTitle(rs.getString(2));
                polls.setMessage(rs.getString(3));
                result.add(polls);
            }
        } catch (Exception ex) {
            throw new DAOException("Error while getting list of polls.", ex);
        }
        return result;
    }

    @Override
    public Polls getPoolForId(Integer id) throws DAOException {
        Polls polls = new Polls();
        Connection con = SQLConnectionProvider.getConnection();
        String query = "" +
                "select * from Polls" +
                " where id = " + id;
        try (
                PreparedStatement pst = con.prepareStatement(query);
                ResultSet rs = pst.executeQuery();

        ) {
            if (rs != null && rs.next()) {
                polls.setId(rs.getLong(1));
                polls.setTitle(rs.getString(2));
                polls.setMessage(rs.getString(3));
            }
        } catch (Exception ex) {
            throw new DAOException("Error while getting poll for id.", ex);
        }
        return polls;
    }

}
