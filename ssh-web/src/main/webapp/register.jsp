<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Register</title>
<s:head/>
</head>
<body>
<div>
<p>Input user info</p>
<s:form namespace="/" action="register">
<s:textfield label="username" name="user.username"/>
<s:password label="password" name="user.password"/>
<s:password label="confirm_password" name="confirmPassword"/>
<s:token />
<s:submit label="submit"/>
<s:reset label="reset"/>
</s:form>
</div>
</body>
</html>