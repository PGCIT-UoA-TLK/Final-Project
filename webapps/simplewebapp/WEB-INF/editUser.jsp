<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Information</title>
</head>
<body>

<form>
    <fieldset>
        <legend>Account Information</legend>
        <label for="input-username">Username</label><input type="text" id="input-username" name="username" disabled><br/>
        <label for="input-password">Password</label><input type="password" id="input-password" name="password" disabled><br/>
        <label for="input-firstname">First Name</label><input type="text" id="input-firstname" name="firstname"><br/>
        <label for="input-lastname">Last Name</label><input type="text" id="input-lastname" name="lastname"><br/>
        <input type="hidden" name="page" value="registerUser">
        <input type="submit" value="Register">
    </fieldset>
</form>
</body>
</html>
