package com.svarian.regusers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "ServletSignIn", value = "/SignIn")
public class ServletSignIn extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    private boolean checkingSignIn(User user, Connection connection){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM user_data WHERE login=? AND password=?");
            statement.setString(1, user.login);
            statement.setString(2, user.password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 0) return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    private int addToOnlineUsers(User user,Connection connection){
        int id=-1;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO onlineusers(userLogin) VALUES (?)");
            preparedStatement.setString(1, user.login);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PreparedStatement statement1 = null;
        try {
            statement1 = connection.prepareStatement("SELECT id FROM onlineusers WHERE userLogin=?");
            statement1.setString(1, user.login);
            ResultSet resultSet = statement1.executeQuery();
            resultSet.next();
            id=resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Gson gson=new Gson();
        User user =gson.fromJson(reader, User.class);
        Connection connection = JDBC.connectionToDB();
Message message=new Message();
message.setMessage("there is no registered user with this username and password");
        if(!checkingSignIn(user,connection)) response.getWriter().write
                (gson.toJson(message));
        else {
     response.getWriter().write("{\"id\":"+'\"'+addToOnlineUsers(user,connection)+"\"}");
        }

    }
}
