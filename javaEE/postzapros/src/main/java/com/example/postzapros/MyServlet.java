package com.example.postzapros;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mysql.cj.MysqlConnection;
import com.mysql.cj.MysqlType;
import com.mysql.jdbc.Driver;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;


public class MyServlet extends HttpServlet {
    private static class MyClass {
        private String id;
        private String name;
        private Boolean status;

        public MyClass() {}
        public MyClass(String id,String name,Boolean status) {
            this.id=id;
            this.name=name;
            this.status=status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "id="+id+" name="+name+" status="+status+'\n';
        }
    }
        public Connection connectionToDB() {
            String jdbcUrl = "jdbc:mysql://localhost:3306/test";
            String username = "root";
            String password = "root";
            Connection connection;
            Driver driver = null;
            try {
                driver = new Driver();
                DriverManager.registerDriver(driver);
                connection = DriverManager.getConnection(jdbcUrl, username, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return connection;

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Connection connection=connectionToDB();
        try {
            Statement statement = connection.createStatement();
            ResultSet resSet = statement.executeQuery("select * from users");
            PrintWriter out = response.getWriter();
            List<String> myClassList=new LinkedList<>();
            Gson gson=new Gson();
            MyClass elem=new MyClass();
            while(resSet.next()) {
                elem.setId(resSet.getString(1));
                elem.setName(resSet.getString(2));
                elem.setStatus(resSet.getBoolean(3) );
                myClassList.add(gson.toJson(elem));
            }
            out.write(myClassList.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonBody = sb.toString();
        Gson GSON =new Gson();
        MyClass myObject=GSON.fromJson(jsonBody,MyClass.class);
        Connection connection=connectionToDB();
        try{
            Statement statement=connection.createStatement();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO users(id,name,status) VALUES (?,?,?)");
            preparedStatement.setString(1,myObject.getId());
            preparedStatement.setString(2, myObject.getName());
            preparedStatement.setBoolean(3, myObject.getStatus());
             preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonBody = sb.toString();
        Gson GSON =new Gson();
        MyClass myObject=GSON.fromJson(jsonBody,MyClass.class);
        Connection connection=connectionToDB();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setString(1, myObject.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Такого user нет");;
        }

    }

}
