<%@ page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="list">
    <c:forEach items="${categories}" var="category">
        <div class="list-item" onclick="update('products/getByCategory?catId=<c:out value="${category.id}" />', 'products')">
            <span class="list-item-txt">
                <c:out value="${category.name}" />
            </span>
        </div>
    </c:forEach>
</div>
