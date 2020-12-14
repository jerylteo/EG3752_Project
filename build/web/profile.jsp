<%@page import="ee.mote.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mõte Inc - Profile</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="css/common.css">
        <script src="js/validation.js"></script>
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
    
        <main class="container my-5 ">
            
            <c:choose>
                <c:when test="${sessionScope.profileMsg != null}">
                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <button class="close" aria-label="Close" type="button" data-dismiss="alert">
                          <span aria-hidden="true">×</span>
                        </button>
                        <strong>Notification!</strong> <%= session.getAttribute("profileMsg") %>
                    </div>
                </c:when>
            </c:choose>
            <c:remove var="profileMsg" />
            
            <section class="shadow p-3">
                <%
                    User u = (User)session.getAttribute("userInfo");
                %>
                <h3 class="text-center">Welcome back <span class="text-success"><%= u.getName() %></span></h3>
                <article class="mt-5">
                    <div class="form-inline">
                        <p class="font-weight-bold mr-2">Email:</p>
                        <p><%= u.getEmail() %></p>
                    </div>
                    <div class="form-inline">
                        <p class="font-weight-bold mr-2">Address Line:</p>
                        <p><%= u.getAdd1()%></p>
                    </div>
                    <c:if test="${sessionScope.userInfo.getAdd2() != null}">
                        <div class="form-inline">
                            <p class="font-weight-bold mr-2">Address Line 2:</p>
                            <p><%= u.getAdd2() %></p>
                        </div>
                    </c:if>
                    <div class="form-inline">
                        <p class="font-weight-bold mr-2">Postal Code:</p>
                        <p><%= u.getPostal() %></p>
                    </div>
                    <div class="form-inline">
                        <p class="font-weight-bold mr-2">Mobile Number:</p>
                        <p><%= u.getMobile() %></p>
                    </div>

                    <div class="form-group">
                        <button class="btn btn-primary" id="pwBtn">CHANGE PASSWORD</button>
                    </div>

                    <hr>

                    <div class="form-inline">
                        <p class="font-weight-bold mr-2">Number of Points obtained so far:</p>
                        <p class="badge badge-success p-2"><%= u.getPoints() %></p>
                    </div>
                </article>
            </section>
            <section class="shadow mt-5 p-3 pwCon" id="pwCon" style="display: none" onsubmit="return validate()">
                <form method="post" action="profile">
                    <label class="font-weight-bold">Change Password</label>
                    <div class="form-group mb-0">
                        <input type="password" name="currentPw" placeholder="Current Password" class="form-control" required>
                    </div>
                    <div class="input-group mt-3">
                        <input type="password" name="pw" id="pw" placeholder="New Password" class="form-control" required>
                        <input type="password" id="cfmPw" placeholder="Confirm new password" class="form-control" required>
                    </div>
                    <span id="pwErr" class="text-danger"></span>
                    <div class="form-group mt-3">
                        <input type="submit" class="btn btn-success" value="CONFIRM">
                    </div>
                </form>
            </section>
        </main>
    </body>
</html>
