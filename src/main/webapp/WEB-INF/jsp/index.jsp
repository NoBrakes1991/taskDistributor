<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Welcome</title>
    <%--<link rel="stylesheet" type="text/css"--%>
    <%--href="${pageContext.request.contextPath}/css/style.css"/>--%>
</head>
<body>
<h1>Welcome</h1>

<h1>Tasks List</h1>

<br/><br/>
<div>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Summary</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Assignee</th>
        </tr>
        </tr>
        <c:forEach  items="${tasks}" var ="task">
            <tr>
                <td>${task.id}</td>
                <td>${task.summary}</td>
                <td>${task.startDate.year+1900}-${task.startDate.month+1}-${task.startDate.date}</td>
                <td>${task.endDate.year+1900}-${task.endDate.month+1}-${task.endDate.date}</td>
                <td>${task.assignee}</td>
            </tr>
        </c:forEach>
    </table>
</div>


<a href="${pageContext.request.contextPath}/taskAdder">Add new Task</a>

</body>

</html>