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

@WebServlet(name = "ServletSignOut", value = "/SignOut")
public class ServletSignOut extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = JDBC.connectionToDB();
        BufferedReader reader = request.getReader();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(reader).getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        System.out.println(id);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("DELETE  FROM onlineusers WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println("Error");
        }
    }
}
