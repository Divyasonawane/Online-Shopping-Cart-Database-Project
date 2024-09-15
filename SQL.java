package com.divya.project;

import java.sql.*;

public class SQL {

    private Connection connection;
    private Statement statement;

    public SQL() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }

        String url = "jdbc:mysql://localhost:3306/databaseName"; // Replace with your database name
        try {
            connection = DriverManager.getConnection(url, "username", "password"); // Replace with your username and password
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Connected to the database successfully.");
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            e.printStackTrace();
            throw e;
        }
    }

//    public void writeExecute(String sqlCode, Object... params) {
//        try (PreparedStatement pstmt = connection.prepareStatement(sqlCode)) {
//            setParameters(pstmt, params);
//            pstmt.executeUpdate();
//            System.out.println("Executed the update: " + sqlCode);
//        } catch (SQLException e) {
//            logSQLException(e);
//        }
//    }

//    public ResultSet queryExecute(String sqlCode, Object... params) {
//        ResultSet rs = null;
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(sqlCode);
//            setParameters(pstmt, params);
//            rs = pstmt.executeQuery();
//            System.out.println("Executed the query: " + sqlCode);
//        } catch (SQLException e) {
//            logSQLException(e);
//        }
//        return rs;
//    }

    private void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }

    private void logSQLException(SQLException e) {
        int errorCode = e.getErrorCode();
        String sqlState = e.getSQLState();
        System.err.println("SQL Error Code: " + errorCode + " SQL State: " + sqlState);
        e.printStackTrace();
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close the connection.");
            e.printStackTrace();
        }
    }

	public void WriteExcute(String sqlCode , Object... params) {
		try (PreparedStatement pstmt = connection.prepareStatement(sqlCode)) {
            setParameters(pstmt, params);
            pstmt.executeUpdate();
            System.out.println("Executed the update: " + sqlCode);
        } catch (SQLException e) {
            logSQLException(e);
        }
		
	}

	public ResultSet QueryExchte(String sqlCode , Object... params) {
		ResultSet rs = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlCode);
            setParameters(pstmt, params);
            rs = pstmt.executeQuery();
            System.out.println("Executed the query: " + sqlCode);
        } catch (SQLException e) {
            logSQLException(e);
        }
        return rs;
	}
}
