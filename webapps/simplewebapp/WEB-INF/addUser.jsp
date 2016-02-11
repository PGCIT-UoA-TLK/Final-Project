<%--@elvariable id="genders" type="java.util.Map<java.lang.String, java.lang.String>"--%>
<%--@elvariable id="ages" type="java.util.Map<java.lang.String, java.lang.String>"--%>
<%--@elvariable id="images" type="java.util.Map<java.lang.String, java.lang.String>"--%>

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
                    <div class="col-sm-5">
                        <select class="form-control" id="age" name="age">
                            <c:forEach var="age" items="${ages.keySet()}">
                                <option value="${age}">${ages.get(age)}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">Gender</label>
                    <div class="col-sm-10">
                        <c:forEach var="gender" items="${genders.keySet()}">
                            <div class="radio">
                                <label><input type="radio" name="gender" value="${gender}" >${genders.get(gender)}</label>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">Choose an icon</label>
                    <div class="col-sm-10">
                        <c:forEach var="image" items="${images.keySet()}">
                            <div class="radio col-sm-3">
                                <label>
                                    <input title="${image}" type="radio" name="image" value="${images.get(image)}">
                                    <img src="/images/${images.get(image)}" alt="${image} icon" class="img-thumbnail user-image">
                                </label>
                            </div>
                        </c:forEach>
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
