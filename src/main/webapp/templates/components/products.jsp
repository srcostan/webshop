<%@ page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <c:forEach items="${products}" var="product">
        <div><c:out value="${product.title}" /></div>
    </c:forEach>
</div>
