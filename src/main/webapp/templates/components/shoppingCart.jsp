<%@ page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <div>Total amount: <c:out value="${shoppingCart.totalAmount}" /> </div>
    <c:forEach items="${shoppingCart.products}" var="product">
        <div>
            <c:out value="${product.title}" />
        </div>
    </c:forEach>
</div>
