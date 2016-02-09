<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>

<c:choose>
    <c:when test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible col-sm-offset-2 col-sm-10" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            ${successMessage}
        </div>
    </c:when>
    <c:when test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible col-sm-offset-2 col-sm-10" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <strong>Error!</strong> ${errorMessage}
        </div>
    </c:when>
</c:choose>