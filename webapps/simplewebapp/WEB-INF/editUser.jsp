<%--@elvariable id="user" type="simplewebapp.User"--%>
<%--@elvariable id="ages" type="java.util.Map<java.lang.String, java.lang.String>"--%>
<%--@elvariable id="genders" type="java.util.Map<java.lang.String, java.lang.String>"--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Information</title>

    <%@include file="include/specialIncludeFiles.jsp" %>
</head>
<body>

<%@include file="include/userBar.jsp" %>

<div class="container">
    <div class="col-xs-12">
        <form class="form-horizontal" id="editUserForm">
            <fieldset>
                <legend>Account Information</legend>

                <div class="col-sm-offset-2 col-sm-10">
                    <%@include file="include/alerts.jsp" %>
                </div>

                <div class="form-group">
                    <label for="input-username" class="col-sm-2 control-label">Username</label>
                    <div class="col-sm-10">
                        <input type="text" id="input-username" class="form-control" name="username" value="${user.username}" disabled><br/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-password" class="col-sm-2 control-label">Password</label>
                    <div class="col-sm-10">
                        <input type="text" id="input-password" name="password" class="form-control" value="Password Hidden" disabled><br/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-firstname" class="col-sm-2 control-label">First Name</label>
                    <div class="col-sm-10">
                        <input type="text" id="input-firstname" name="firstname" class="form-control" value="${user.firstname}"><br/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-lastname" class="col-sm-2 control-label">Last Name</label>
                    <div class="col-sm-10">
                        <input type="text" id="input-lastname" name="lastname" class="form-control" value="${user.lastname}"><br/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" id="ageLabel" for="age">Select an Age Group</label>
                    <div class="col-sm-3">
                        <select class="form-control" id="age" name="age">
                            <c:forEach var="age" items="${ages.keySet()}">
                                <c:choose>
                                    <c:when test="${age.equals(user.age)}">
                                        <option value="${age}" selected>${ages.get(age)}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${age}">${ages.get(age)}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">Gender</label>
                    <div class="col-sm-10">
                        <c:forEach var="gender" items="${genders.keySet()}">
                            <div class="radio">
                                <c:choose>
                                    <c:when test="${gender.equals((user.gender))}">
                                        <label><input type="radio" name="input-gender" value="${gender}" checked>${genders.get(gender)}</label>
                                    </c:when>
                                    <c:otherwise>
                                        <label><input type="radio" name="input-gender" value="${gender}">${genders.get(gender)}</label>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <input type="hidden" name="page" value="editUser">
                <input type="submit" class="btn btn-success pull-right" value="Change details">
                <input type="button" class="btn btn-danger pull-right" value="Delete account" onclick="confirmDelete('editUserForm')">
                <input type="hidden" id="delete" name="delete">
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
