package com.svarian.regusers;

import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "ServletRegistration", value = "/Registration")
public class ServletRegistration extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    private boolean pasCheck(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]*$";
        return password.matches(regex);
    }

    private String checkingAndAdding(User user){
        Connection connection =JDBC.connectionToDB();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM user_data WHERE login = ?");
            statement.setString(1, user.login);
            ResultSet resultSet = statement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) != 0) return "user with this login already exists";

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(!user.password.equals(user.repPassword)) return "entered passwords are different";
        if(!pasCheck(user.password)) return "the password must consist only of Latin letters,contain at least one capital letter and at least one number.";

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO user_data(password,login) VALUES (?,?)");
            preparedStatement.setString(1, user.password);
            preparedStatement.setString(2, user.login);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "Registration successfully completed";

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Gson gson=new Gson();
        User user =gson.fromJson(reader, User.class);
        Message message=new Message();
        message.setMessage(checkingAndAdding(user));
        response.getWriter().write(gson.toJson(message));
    }
}
