package com.example.ivan.servlets;

import com.example.ivan.config.DatabaseConfig;
import com.example.ivan.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Connection connection = DatabaseConfig.getConnection();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = getUsers();
        req.setAttribute("users", users);
        PrintWriter pw = resp.getWriter();
        pw.println("<html>");
        for (User user : users) {
            pw.println("<h1>" + user.getId() + " | " + user.getUsername() + "</h1>");

            pw.println("<form action=\"/update\" method=\"POST\">");
            pw.write("<input class=\"form-control\" type=\"hidden\" name=\"id\" value=\"" + user.getId() + "\">");
            pw.write("<input class=\"form-control\" name=\"username\" placeholder=\"Update:\" type=\"text\">");
            pw.println("<button type=\"submit\">Update User</button>");
            pw.println("</form>");

            pw.println("<form action=\"/delete\" method=\"POST\">");
            pw.write("<input class=\"form-control\" type=\"hidden\" name=\"id\" value=\"" + user.getId() + "\">");
            pw.println("<button type=\"submit\">Delete User</button>");
            pw.println("</form>");
        }

        pw.println("<form action=\"\" method=\"POST\">");
        pw.write("<input class=\"form-control\" name=\"username\" placeholder=\"Write the Username:\" type=\"text\">");
        pw.println("<button type=\"submit\">Add User</button>");
        pw.println("</form>");
        pw.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = resp.getWriter();
        String username = req.getParameter("username");
        addUser(username);

        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Добавление пользователя</title>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h1>Пользователь успешно добавлен!</h1>");
        pw.println("<p>Имя пользователя: " + username + "</p>");
        pw.println("</body>");
        pw.println("</html>");
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_andersen ORDER BY id");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                users.add(user);
            }
            connection.commit();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return users;
    }

    public void addUser(String username) {
        try {
            CallableStatement statement = connection.prepareCall("INSERT INTO user_andersen (username) VALUES (?)");
            statement.setString(1, username);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
