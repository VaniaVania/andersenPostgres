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

public class DeleteServlet extends HttpServlet {

    private static final Connection connection = DatabaseConfig.getConnection();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = resp.getWriter();
        Long id = Long.parseLong(req.getParameter("id"));
        deleteUser(id);

        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Удаление пользователя</title>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h1>Пользователь успешно удален!</h1>");
        pw.println("<p>Id пользователя: " + id + "</p>");
        pw.println("</body>");
        pw.println("</html>");
    }

    public void deleteUser(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM user_andersen WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
