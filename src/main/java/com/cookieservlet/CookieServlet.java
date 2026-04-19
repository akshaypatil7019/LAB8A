package com.cookieservlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/CookieServlet")
public class CookieServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("userName");

        String existingUser = null;
        int count = 0;

        // Read cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("user")) {
                    existingUser = c.getValue();
                }
                if (c.getName().equals("count")) {
                    count = Integer.parseInt(c.getValue());
                }
            }
        }

        // If new user entered
        if (userName != null && !userName.isEmpty()) {
            existingUser = userName;
            count = 0;

            Cookie userCookie = new Cookie("user", userName);
            userCookie.setMaxAge(30); // expires in 30 seconds
            response.addCookie(userCookie);
        }

        // If user exists
        if (existingUser != null) {
            count++;

            Cookie countCookie = new Cookie("count", String.valueOf(count));
            countCookie.setMaxAge(30);
            response.addCookie(countCookie);

            out.println("<h2 style='color:blue;'>Welcome back, " + existingUser + "!</h2>");
            out.println("<h3>You have visited this page " + count + " times</h3>");

            // Display all cookies
            out.println("<h3>List of Cookies:</h3>");
            if (cookies != null) {
                for (Cookie c : cookies) {
                    out.println("Name: " + c.getName() + 
                                " | Value: " + c.getValue() + "<br>");
                }
            }
        } 
        else {
            out.println("<h2 style='color:red;'>Welcome Guest! Please enter your name.</h2>");
            out.println("<form action='CookieServlet' method='get'>");
            out.println("Name: <input type='text' name='userName'>");
            out.println("<input type='submit' value='Submit'>");
            out.println("</form>");
        }
    }
}