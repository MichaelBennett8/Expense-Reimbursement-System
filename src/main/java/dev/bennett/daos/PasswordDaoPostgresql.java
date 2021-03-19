package dev.bennett.daos;

import dev.bennett.utils.ConnectionsUtil;
import org.apache.log4j.Logger;

import java.sql.*;

public class PasswordDaoPostgresql implements PasswordDAO{

    private Logger logger = Logger.getLogger(PasswordDaoPostgresql.class.getName());

    @Override
    public String createPassword(int employeeID, String password) {
        try(Connection conn = ConnectionsUtil.createConnection()){

            String sql = "insert into pswd (employee_id, pass) values (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, employeeID);
            ps.setString(2, password);

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            String dbPassword = rs.getString("pass");

            return dbPassword;
        }
        catch (SQLException e){
            e.printStackTrace();
            logger.error("unable to create account", e);
            return null;
        }
    }

    @Override
    public String getPasswordByID(int employeeID) {
        try(Connection conn = ConnectionsUtil.createConnection()){
            String sql = "select * from pswd where employee_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, employeeID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getString("pass");
            }
            return null;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to get account", sqlException);
            return null;
        }
    }

    @Override
    public String updatePassword(int employeeID, String password) {
        try(Connection conn = ConnectionsUtil.createConnection()){
            String sql = "update pswd set pass = ? where employee_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(2, employeeID);
            ps.setString(1, password);

            ps.executeUpdate();
            return password;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to update account", sqlException);
            return null;
        }
    }

    @Override
    public boolean deletePassword(int employeeID) {
        try(Connection conn = ConnectionsUtil.createConnection()){
            String sql = "delete from pswd where employeeID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,employeeID);
            ps.execute();
            return true;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to delete account", sqlException);
            return false;
        }
    }
}
