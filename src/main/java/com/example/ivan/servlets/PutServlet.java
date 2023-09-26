package com.example.ivan.servlets;

import com.example.ivan.config.DatabaseConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PutServlet extends HttpServlet {

    private static final Connection connection = DatabaseConfig.getConnection();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = resp.getWriter();
        Long id = Long.parseLong(req.getParameter("id"));
        String username = req.getParameter("username");
        updateUser(id, username);

        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Обновление пользователя</title>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h1>Пользователь успешно обновлен!</h1>");
        pw.println("<p>Id пользователя: " + id + " Имя пользователя: " + username + "</p>");
        pw.println("</body>");
        pw.println("</html>");
    }

    public void updateUser(Long id, String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE user_andersen SET username = ? WHERE id =" + id);
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//
}
