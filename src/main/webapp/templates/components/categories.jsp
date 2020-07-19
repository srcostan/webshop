<%@ page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>

    <ul>
        <c:forEach items="${categories}" var="category">
            <li onclick="update('products/getByCategory?catId=<c:out value="${category.id}" />', 'products')">
                <c:out value="${category.name}" />
            </li>
        </c:forEach>
    </ul>
</div>
