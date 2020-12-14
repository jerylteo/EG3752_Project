<%@page import="java.util.List"%>
<%@page import="ee.mote.Item"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Mõte Inc - Shop</title>
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
                <a class="nav-link active" href="${pageContext.request.contextPath}/shop">Shop</a>
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
        <hr class="mt-0">
        <main class="my-5 container p-3">
            <input type="hidden" id="cartMsg" value="${sessionScope.cartMsg}">
            
            <c:choose>
                <c:when test = "${sessionScope.cartMsg != null}">
                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <button class="close" aria-label="Close" type="button" data-dismiss="alert">
                          <span aria-hidden="true">×</span>
                        </button>
                        <strong>Notification!</strong> <%= session.getAttribute("cartMsg") %>
                    </div>
                </c:when>
            </c:choose>
            <c:remove var="cartMsg"/>

            
            <form id="searchForm" method="post" action="shop">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <select class="form-control">
                            <option selected disabled>Brand</option>
                            <option>Cat 1</option>
                        </select>
                    </div>
                    <input type="text" name="searchTerm" class="form-control" placeholder="Prism X240+">
                    <div class="input-group-append">
                        <button class="btn btn-success" type="submit">SEARCH</button>
                    </div>
                </div>
            </form>
            <c:if test = "${sessionScope.searchTerm != null}">
                <h4 class="my-5">Search results for <%= session.getAttribute("searchTerm") %></h4>
            </c:if>
            <section class="mt-5">
                <%
                    List<Item> itemList = (List<Item>) session.getAttribute("itemList");
                %>
                <c:choose>
                    <c:when test="${itemList == null}">
                        <h3>No items found.</h3>
                    </c:when>
                    <c:otherwise>
                        <div class="row">
                            <c:forEach items="${itemList}" var="item">
                                <article class="col-lg-4 col-md-6 mb-4">
                                    <div class="card h-100 shadow">
                                        <img class="card-img-top" src="${pageContext.request.contextPath}/resources/dummy2.webp" alt="dummy">
                                        <div class="card-body">
                                            <h4 class="card-title">${item.description}</h4>
                                            <h6 class="card-subtitle mb-2 text-muted">${item.brand}</h6>
                                        </div>
                                        <div class="card-footer">
                                            <div class="d-flex justify-content-between align-items-center">
                                                <span class="card-text font-weight-bold">Price: $${item.price}</span>
                                                <span class="badge badge-primary p-2">Points: ${item.points}</span>
                                            </div>
                                            
                                            <button class="btn btn-success btn-block mt-3" type="button" data-toggle="modal" data-target="#itemModal${item.id}">ADD TO CART</button>
                                            
                                            <div class="modal fade" id="itemModal${item.id}" tabindex="-1" role="dialog" aria-labelledby="itemModalLabel" aria-hidden="true">
                                                <div class="modal-dialog" role="document">
                                                  <div class="modal-content">
                                                    <div class="modal-header">
                                                      <h5 class="modal-title" id="itemModalLabel">${item.description}</h5>
                                                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                      </button>
                                                    </div>
                                                    <form action="cart" method="post" onsubmit="return isLoggedIn()">
                                                        <div class="modal-body">
                                                            <h6 class="text-muted mt-2">${item.brand}</h6>
                                                            <div class="d-flex justify-content-between align-items-center">
                                                                <span class="font-weight-bold">Price: $${item.price}</span>
                                                                <span class="badge badge-primary p-2">Points: ${item.points}</span>
                                                            </div>

                                                            <div class="form-group mt-3">
                                                                <label for="itemQty">Quantity</label>
                                                                <input type="number" class="form-control" id="itemQty" name="itemQty" min="1" max="50" value="1" onkeydown="if(event.key==='.'){event.preventDefault();}" required>
                                                            </div>
                                                            <%--<c:set var="itemInfo" value="${item}" scope="session" />--%>
                                                            <input type="hidden" id="itemId" name="itemId" value="${item.id}">
                                                            <input type="hidden" id="userLoggedIn" value="${sessionScope.isLoggedIn}">
                                                            <span class="text-danger" id="cartErr"></span>
                                                        </div>
                                                        <div class="modal-footer">
                                                          <button type="submit" class="btn btn-success btn-block">CONFIRM</button>
                                                        </div>
                                                    </form>
                                                  </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </article>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
                
            </section>
        </main>
    </body>
</html>
