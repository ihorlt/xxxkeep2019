package ua.ifit.lms.dao.repository;

import ua.ifit.lms.dao.entity.User;

import java.sql.*;

public class UserRepository {

    /**
     * Get User By Email and Password from User Table
     * @return class User or null
     */
    public User getUserByEmailByPassword(String email, String password) {

        DataSource dataSource = new DataSource();

        String query = "SELECT id, email, password, name, date_created, date_last_entered" +
                " FROM user " +
                " WHERE user.email='" + email + "' AND password='" + password + "'";

        try (
                // get connection with our database
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                )
        {
            if (resultSet.next())  {
                User user =  new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("date_created"),
                        resultSet.getString("date_last_entered")
                );

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveUser(User user) {

        DataSource dataSource = new DataSource();

        try(
                Connection con = dataSource.getConnection();
                PreparedStatement stmt = (user.getId() == 0L) ?
                        con.prepareStatement("INSERT INTO user (email, password, name, date_created, date_last_entered) VALUES (?,?,?,?,?)") :
                        con.prepareStatement("UPDATE user SET email=?, password=?, name=? WHERE id=" + user.getId());
        ) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            if (user.getId() == 0L) {
                stmt.setString(4, user.getDate_created());
                stmt.setString(5, user.getDate_last_entered());
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
