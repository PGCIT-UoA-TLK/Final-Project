<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Registration</title>

    <%@ include file="include/specialIncludeFiles.jsp" %>
</head>
<body>

<%@include file="include/userBar.jsp" %>

<div class="container">
    <div class="col-xs-12">
        <form class="form-horizontal">
            <fieldset>
                <legend>User Registration</legend>

                <%@include file="include/alerts.jsp" %>

                <div class="form-group">
                    <label for="input-username" class="col-sm-2 control-label">Username</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="input-username" name="username" required
                               pattern="^[a-zA-Z0-9._%+-]{1,15}"><br/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-password" class="col-sm-2 control-label">Password</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="input-password" name="password" required
                               pattern="^[a-zA-Z0-9._%+-]{1,15}"><br/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-firstname" class="col-sm-2 control-label">First Name</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="input-firstname" name="firstname"><br/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-lastname" class="col-sm-2 control-label">Last Name</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="input-lastname" name="lastname"><br/>
                    </div>
                </div>

                <div>
                    <label><input type="radio" name="input-gender" value="Male" checked>Male</label>
                    <label><input type="radio" name="input-gender" value="Female">Female</label>
                </div>
                <div class="radio">
                    <label for="input-icon_name" class="col-sm-2 control-label">Choose a user icon</label> <br/>
                </div>
                <div class="radio" id="input-icon_name">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
                        <img src="/images/IMG1.jpg" alt="Donald icon" class="img-thumbnail" width=89 height=89>
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
                        <img src="/images/IMG2.jpg" alt="Fresco icon" class="img-thumbnail" width=89 height=119>
                    </label>
                </div>

                <input type="hidden" name="page" value="addUser">
                <input type="submit" class="btn btn-default pull-right" value="Register">
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
