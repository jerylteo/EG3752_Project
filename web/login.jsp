<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Mõte Inc - Register/Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="js/validation.js" type="text/javascript"></script>
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
                        <a class="nav-link active" href="login.jsp">Register/Login</a>
                    </li>
                </c:otherwise>
              </c:choose>

            </ul>
        </nav>
        <hr class="mt-0">
        <section class="container fullheight">

            <p class="text-success text-center" id="infoMsg">            
                <% if (session.getAttribute("infoMsg") != null) out.println(session.getAttribute("infoMsg")); %>
            </p>
            <div class="row h-100">
                <article id="register" class="col-lg-6 my-auto">
                    <div class="mr-lg-2 mb-md-3 mb-lg-0 shadow p-3">
                        <h3 class="text-center">Register</h3>
                        <form class="my-3" id="registerForm" action="validate" method="post" onsubmit="return validate()">
                            <div class="form-group">
                                <label for="name">Full Name</label>
                                <input name="name" id="name" type="text" class="form-control" placeholder="Jeryl Teo" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input name="email" id="email" type="email" class="form-control" placeholder="jerylteo@gmail.com" required>
                                <span class="text-danger">
                                    <% if (session.getAttribute("errMsg") != null) out.println(session.getAttribute("errMsg")); %>
                                </span>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-5 mb-0">
                                    <label for="add1">Address Line 1</label>
                                    <input name="add1" id="add1" type="text" class="form-control" placeholder="NYP Block S" required>
                                </div>
                                <div class="form-group col-4 mb-0">
                                    <label for="add2">Address Line 2</label>
                                    <input name="add2" id="add2" type="text" class="form-control" placeholder="Optional">
                                </div>
                                <div class="form-group col-3 mb-0">
                                    <label for="postal">Postal Code</label>
                                    <input name="postal" id="postal" type="text" class="form-control" placeholder="123456" required>
                                </div>
                            </div>
                            <span class="text-danger" id="posErr"></span>
                            <div class="form-group mt-3">
                                <label for="mobile">Mobile Number</label>
                                <input name="mobile" id="mobile" type="text" class="form-control" placeholder="81234567" required>
                                <span class="text-danger" id="mobErr"></span>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-6">
                                    <label for="pw">Password</label>
                                    <input name="pw" id="pw" type="password" class="form-control" placeholder="********" required>
                                    <span class="text-danger" id="pwErr"></span>
                                </div>
                                <div class="form-group col-6">
                                    <label for="cfmPw">Confirm Password</label>
                                    <input id="cfmPw" type="password" class="form-control" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary btn-block">REGISTER</button>
                            </div>
                        </form>
                    </div>
                </article>
                <article id="login" class="col-lg-6 my-auto">
                    <div class="ml-lg-2 shadow p-3">
                        <h3 class="text-center">Login</h3>
                        <form class="my-3" id="loginForm" action="login" method="post">
                            <div class="form-group">
                                <label for="lemail">Email</label>
                                <input name="lemail" id="lemail" type="email" class="form-control" placeholder="jerylteo@gmail.com" required>
                            </div>
                            <div class="form-group">
                                <label for="lpw">Password</label>
                                <input name="lpw" id="lpw" type="password" class="form-control" placeholder="********" required>
                                <span class="text-danger">
                                    <% if (session.getAttribute("logMsg") != null) out.println(session.getAttribute("logMsg")); %>
                                </span>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-success btn-block">LOGIN</button>
                            </div>
                        </form>
                    </div>
                </article>
            </div>
        </section>
    </body>
</html>
