<%@page import="ee.mote.User"%>
<%@page import="java.util.List"%>
<%@page import="ee.mote.Item"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 
    Document   : cart
    Created on : 26 May, 2020, 4:58:31 PM
    Author     : klmch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Mõte Inc - Cart</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="css/common.css">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light container py-3">
            <a class="navbar-brand" href="index.jsp">Mõte Inc</a>
            <ul class="navbar-nav ml-auto">
              <li class="nav-item">
                <a class="nav-link" href="index.jsp">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/shop">Shop</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/cart">Cart</a>
              </li>
              <c:choose>
                <c:when test="${sessionScope.isLoggedIn != null}">
                    <c:choose>
                        <c:when test="${sessionScope.isLoggedIn}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                            </li>
                        </c:when>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a class="nav-link" href="login.jsp">Register/Login</a>
                    </li>
                </c:otherwise>
              </c:choose>

            </ul>
        </nav>
        <main class="my-5 container p-3">
            <h3>Mõte - Checkout Status</h3>
            <section class="mt-3">
                <c:choose>
                    <c:when test = "${sessionScope.isLoggedIn == null}">
                        <h4>How did you get here?</h4>
                    </c:when>
                    <c:otherwise>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card shadow">
                                    <div class="card-body w-75 mx-auto">
                                        <% User u = (User)session.getAttribute("userInfo"); %>
                                        
                                        <h4 class="card-title text-center">Receipt</h4>
                                        <h6 class="card-title">Name: <%= u.getName() %></h6>
                                        <p class="card-subtitle my-4">Total Price: $<c:out value="${sessionScope.receiptPrice}" /></p>
                                        <span class="badge badge-primary p-2">Points Earned: <c:out value="${sessionScope.receiptPts}" /></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </section>
        </main>
    </body>
</html>
