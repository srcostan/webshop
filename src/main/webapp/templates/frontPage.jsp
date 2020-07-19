<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Web Shop</title>
        <link rel="stylesheet" type="text/css" href="../resources/style.css" />
        <script type="text/javascript" src="../resources/scripts.js"></script>
    </head>
    <body>
        <div id="header">
            <span id="title">Webshop</span>
        </div>
        <div class="container">
            <div id="categories" class="panel">
                <c:import url="/templates/components/categories.jsp"/>
            </div>
            <div id="products" class="panel">
                <c:import url="/templates/components/products.jsp"/>
            </div>
            <div id="shopping-cart" class="panel">
                <c:import url="/templates/components/shoppingCart.jsp"/>
            </div>
        </div>
        <div id="footer">
            <div id="trademark">
                <p>Webshop</p>
            </div>
            <div id="about-us">
                <a href="https://www.google.com">About Us</a>
                <a href="https://www.google.com">Contact</a>
            </div>
        </div>
    </body>
</html>
