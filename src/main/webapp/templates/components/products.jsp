<%@ page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <c:forEach items="${products}" var="product">
        <div class="product-card">
            <div class="product-name">
                <c:out value="${product.title}" />
            </div>
            <div class="product-add-btn">
                <button onclick='update("shoppingCart/addProductToCart?prodid=<c:out value="${product.id}"/>", "shopping-cart")'>Add to cart</button>
            </div>
        </div>
    </c:forEach>
</div>
