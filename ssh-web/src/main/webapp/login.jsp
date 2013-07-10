<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>login</title>
<s:head/>
</head>
<body>
<p>Login</p>
<div>
<s:form namespace="/" action="login">
<s:textfield label="username" name="username"/>
<s:password label="password" name="password"/>
<s:token />
<s:submit label="submit"/>
<s:reset label="reset"/>
</s:form>
</div>
</body>
</html>