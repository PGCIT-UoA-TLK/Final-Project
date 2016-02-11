<%--@elvariable id="ages" type="java.util.List<java.lang.String>"--%>
<%--@elvariable id="gender" type="java.util.List<java.lang.String>"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>User Registration</title>

    <%@ include file="include/specialIncludeFiles.jsp" %>

    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>
<body>

<%@include file="include/userBar.jsp" %>

<div class="container">
    <div class="col-xs-12">
        <form class="form-horizontal" method="post">
            <fieldset>
                <legend>User Registration</legend>

                <div class="col-sm-offset-2 col-sm-10">
                    <%@include file="include/alerts.jsp" %>
                </div>

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
                        <input type="text" class="form-control" id="input-firstname" name="firstname" required><br/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-lastname" class="col-sm-2 control-label">Last Name</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="input-lastname" name="lastname" required><br/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" id="ageLabel" for="age">Select an Age Group</label>
                    <div class="col-sm-3">
                        <select class="form-control" id="age" name="age">
                            <c:forEach var="age" items="${ages}">
                                <option value="${age}">${age}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">Gender</label>
                    <div class="col-sm-10">
                        <c:forEach var="gender" items="${gender}">
                            <div class="radio">
                                <label><input type="radio" name="input-gender" value="${gender}" >${gender}</label>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="form-group">
                    <label for="input-icon_name" class="col-sm-2 control-label">Choose an icon</label>
                    <div class="radio col-sm-offset-1 col-sm-3" id="input-icon_name">
                        <input title="Trump" type="radio" name="optionsRadios" id="optionsRadios1" value="option1"
                               checked>
                        <img src="/images/IMG1.jpg" alt="Donald icon" class="img-thumbnail" width=89 height=89>
                    </div>
                    <div class="radio col-sm-3">
                        <input title="DIYFresco" type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
                        <img src="/images/IMG2.jpg" alt="Fresco icon" class="img-thumbnail" width=89 height=89>
                    </div>
                    <div class="radio col-sm-3">
                        <input title="Muppet" type="radio" name="optionsRadios" id="optionsRadios3" value="option3">
                        <img src="/images/IMG3.jpg" alt="Muppet icon" class="img-thumbnail" width=89 height=89>
                    </div>
                </div>

                <div class="reaptcha col-sm-12">
                    <div class="g-recaptcha" data-sitekey="6Lfl2xcTAAAAAKC4PYbk_0AVGlMFaCFl8hP7getE"></div>
                </div>

                <div class="submit col-sm-12">
                    <input type="hidden" name="page" value="addUser">
                    <input type="submit" class="btn btn-success pull-right" value="Register">
                </div>
            </fieldset>

        </form>

    </div>
</div>


</body>
</html>
