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



                <div >
                    <label class="col-sm-2 control-label" >Gender</label>
                    <label class="col-sm-offset-1"><input type="radio" name="input-gender" value="Male" checked>Male</label>
                    <label class="col-sm-offset-1"><input type="radio" name="input-gender" value="Female">Female</label>
                </div><br/>
                <label for="input-icon_name" class="col-sm-2 control-label">Choose a user icon</label> <br/>
                <div class="radio">
                </div>
                <div class="radio" id="input-icon_name">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
                        <img src="/Images/IMG1.jpg" alt="Donald icon" class="img-thumbnail" width=89 height=89>
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
                        <img src="/Images/IMG2.jpg" alt="Fresco icon" class="img-thumbnail" width=89 height=89>
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3">
                        <img src="/Images/IMG3.jpg" alt="Mupeet icon" class="img-thumbnail" width=89 height=89>
                    </label>
                </div>



                        <div class="g-recaptcha" data-sitekey="6Lfl2xcTAAAAAKC4PYbk_0AVGlMFaCFl8hP7getE"></div>
                        <br/>




                <input type="hidden" name="page" value="addUser">
                <input type="submit" class="btn btn-default pull-right" value="Register">
            </fieldset>

        </form>

    </div>
</div>


</body>
</html>
