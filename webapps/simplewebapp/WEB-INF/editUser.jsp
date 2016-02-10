<%--@elvariable id="user" type="simplewebapp.User"--%>

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

                <%@include file="include/alerts.jsp" %>

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

                <div>
                    <label for="age">Select your age group</label>
                    </br>
                    <select id="age" name="chooseage" multiple>
                        <option value="0-15">0-15</option>
                        <option value="16-25">16-25</option>
                        <option value="26-35">26-35</option>
                        <option value="36-45">36-45</option>
                        <option value="46-55">46-55</option>
                        <option value="56-65">56-65</option>
                        <option value="66-75">66-75</option>
                        <option value="75 and over">75 and over</option>
                    </select>
                </div>

                <div class = "form-group">
                    <label for="input-gender" class="col-sm-2 control-label">Gender</label>
                    <div class ="col-sm-10">
                        <input type="text" id="input-gender" name=gender" class="form-control" value="${user.gender}"><br/>
                    </div>
                    <%--<div>--%>
                        <%--<label><input type="radio" name="input-gender" value="Male" id="male">Male</label>--%>
                        <%--<label><input type="radio" name="input-gender" value="Female">Female</label>--%>
                    <%--</div>--%>
                    <%--<script>--%>
                        <%--if("${user.gender}"=="Male"){--%>
                            <%--document.getElementById('male').checked = true;--%>
                        <%--}else{--%>
                            <%--document.getElementById("female").checked = true;--%>
                        <%--}--%>
                    <%--</script>--%>
                    </div>

                </div>
                <input type="hidden" name="page" value="editUser">
                <input type="submit" class="btn btn-default pull-right" value="Change details">
                <input type="button" class="btn btn-default pull-right" value="Delete account" onclick="confirmDelete('editUserForm')">
                <input type="hidden" id="delete" name="delete">
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
