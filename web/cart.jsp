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
                <a class="nav-link active" href="${pageContext.request.contextPath}/cart">Cart</a>
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
            <h3>Mõte - My Cart</h3>
            <section class="mt-3">
                <c:choose>
                    <c:when test = "${sessionScope.isLoggedIn == null}">
                        <h4>Please log in to view your cart</h4>
                    </c:when>
                    <c:when test = "${sessionScope.odMap == null}">
                        <h4>No items in cart.</h4>
                        <p class="font-italic text-muted">Psst. Head on to shop and add some items!</p>
                    </c:when>
                    <c:otherwise>
                        <div class="row">
                            <div class="col-md-8">
                                <c:forEach items = "${odMap}" var="item">
                                    <article class="col-md-12 mb-4">
                                        <div class="card h-100 shadow">
                                            <div class="row">
                                                <div class="col-md-4 d-flex justify-content-center align-items-center">
                                                    <img src="resources/dummy2.webp" class="w-100">
                                                </div>
                                                <div class="col-md-8">
                                                    <div class="card-block px-5 my-5">
                                                        <h4 class="card-title">${item.value.description}</h4>
                                                        <h6 class="card-subtitle mb-2 text-muted">${item.value.brand}</h6>
                                                        <div class="d-flex justify-content-between align-items-center">
                                                            <span class="card-text">Price/unit: $${item.value.price}</span>
                                                            <span class="badge badge-primary p-2">Points/unit: ${item.value.points}</span>
                                                        </div>
                                                        <p class="card-text">Item Quantity: ${item.value.qty}</p>
                                                        <p class="card-text font-weight-bold">Total Price: $${item.value.price * item.value.qty}</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </article>
                                </c:forEach>
                            </div>
                            <div class="col-md-4">
                                <div class="card shadow">
                                    <div class="card-body">
                                        <h4 class="card-title font-weight-bold">Total Price: $<%= session.getAttribute("totalPrice") %></h4>
                                        <h6 class="card-subtitle my-4">Total Items: <%= session.getAttribute("totalQty") %></h6>
                                        <span class="badge badge-primary p-2">Total Points: <%= session.getAttribute("totalPts") %></span>
                                    </div>
                                    <div class="card-footer">
                                        <form action="checkout" method="get">
                                            <button class="btn btn-success btn-block" type="submit">CHECKOUT</button>
                                        </form>
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
