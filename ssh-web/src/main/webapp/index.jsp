<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Hello</title>
</head>
<body>
<p>Hello JSP</p>
<p>your ip address: <c:out value="${pageContext.request.remoteAddr}" /> port: <c:out value="${pageContext.request.remotePort}" /></p>
<p>cluster address: <c:out value="${pageContext.request.serverName}" /> port: <c:out value="${pageContext.request.serverPort}" /></p>
<p>server  address: <c:out value="${pageContext.request.localAddr}" /> port: <c:out value="${pageContext.request.localPort}" /></p>
</body>
</html>