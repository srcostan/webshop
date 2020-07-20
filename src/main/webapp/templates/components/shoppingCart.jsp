<%@ page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <div>Total amount: <fmt:formatNumber value="${shoppingCart.totalAmount}" type="currency" /> </div>
    <c:forEach items="${shoppingCart.products}" var="product">
        <div>
            <c:out value="${product.title}" />
        </div>
    </c:forEach>
</div>
