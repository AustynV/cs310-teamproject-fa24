package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Badge;
import java.sql.*;

public class BadgeDAO {

    private static final String QUERY_FIND = "SELECT * FROM badge WHERE id = ?";

    private final DAOFactory daoFactory;

    BadgeDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }

    public Badge find(String id) {

        Badge badge = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        String description = rs.getString("description");
                        badge = new Badge(id, description);

                    }

                }

            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return badge;

    }
    
    //V2. Badge Create Method - Austyn V.
    //Create Method: Inserts A New Badge Into The Database
    public boolean create(Badge badge){
        String query = "INSERT INTO badge (id, description) VALUES (?, ?)";
        
        //Establish Connection With DOAFactory
        Connection conn = daoFactory.getConnection();

        //Pulls Together Badge Info To Create A New Badge
        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, badge.getId());
            ps.setString(2, badge.getDescription());
            int rowsAffected = ps.executeUpdate();
            return(rowsAffected == 1);
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //V2. Badge Delete Method - Austyn V.
    //Delete Method: Deletes A Badge From The Database
    public boolean delete(String badgeId){
        String query = "DELETE FROM badge WHERE id = ?";
        
        //Establish Connection With DOAFactory
        Connection conn = daoFactory.getConnection();
        
        //Deletes Badge And Updates The Amount Of Rows Affected To 1
        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, badgeId);
            int rowsAffected = ps.executeUpdate();
            return(rowsAffected == 1);
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}

