package com.svarian.regusers;

import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "ServletToDo", value = "/ToDo")
public class ServletToDo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id=request.getIntHeader("key");
        Connection connection=JDBC.connectionToDB();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT COUNT(*) FROM onlineusers WHERE id=?");
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            int count=resultSet.getInt(1);
            if (count!=1){
                Gson gson=new Gson();
                Message message=new Message();
                message.setMessage("you dont have access");
                response.getWriter().write(gson.toJson(message));
            }else{
                BufferedReader reader = request.getReader();
                Gson gson=new Gson();
                ToDo toDo =gson.fromJson(reader,ToDo.class);
                try{
                    preparedStatement =connection.prepareStatement("INSERT INTO  todo(login,id,name,status) VALUES (?,?,?,?)");
                    preparedStatement.setString(1,toDo.getLogin());
                    preparedStatement.setString(2,toDo.getId());
                    preparedStatement.setString(3, toDo.getName());
                    preparedStatement.setBoolean(4, toDo.getStatus());
                    preparedStatement.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
