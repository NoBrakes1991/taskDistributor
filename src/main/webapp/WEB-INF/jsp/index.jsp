<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Welcome</title>
    <%--<link rel="stylesheet" type="text/css"--%>
    <%--href="${pageContext.request.contextPath}/css/style.css"/>--%>
</head>
<body>
<h1>Welcome</h1>
<h1>Tasks List</h1>

<%--<form method="post" action="filterByAssignee">--%>
<%--<input type="text" name="assignee">--%>
<%--<button type="submit">Find By Assignee</button>--%>
<%--</form>--%>

<%--<form method="post" action="filterByDate">--%>
<%--<input type="date" name="startDate">--%>
<%--<input type="date" name="endDate">--%>
<%--<button type="submit">Find By Date</button>--%>
<%--</form>--%>

<%--<form method="post" action="filterByDateAndAssignee">--%>
<%--<input type="date" name="startDate">--%>
<%--<input type="date" name="endDate">--%>
<%--<input type="text" name="assignee">--%>
<%--<button type="submit">Find By Date and Assignee</button>--%>
<%--</form>--%>

<form method="post" action="filterByDateAndAssignee">
    <input type="date" name="startDate">
    <input type="date" name="endDate">
    <select name="period" itemtype="text">
        <option value="" label="------Set Period------"/>
        <option value="lastQuarter" label="Last Quarter"/>
        <option value="lastMonth" label="Last Month"/>
        <option value="lastWeek" label="Last Week"/>
        <option value="currentQuarterToDate" label="Current Quarter to Date"/>
        <option value="currentMonthToDate" label="Current Month to Date"/>
        <option value="currentWeekToDate" label="Current Week to Date"/>
    </select>
    <select name="assignee" itemtype="text">
        <option value="" label="All assignees"/>
        <c:forEach items="${uniqAssignee}" var="assignee">
            <option value="${assignee}"><c:out value="${assignee}"/></option>
        </c:forEach>
    </select>
    <button type="submit">Find</button>
</form>


<h4>${messageSelectedFilter}</h4>
<h3>${messageNotFound}</h3>
<br>
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
        <c:forEach items="${tasks}" var="task">
            <tr>
                <td>${task.id}</td>
                <td>${task.summary}</td>
                <fmt:setLocale value="en_US"/>
                <td><fmt:formatDate pattern="dd/MMM/yyyy" value="${task.startDate}"/></td>
                <td><fmt:formatDate pattern="dd/MMM/yyyy" value="${task.endDate}"/></td>
                <td>${task.assignee}</td>
            </tr>
        </c:forEach>
    </table>
</div>


<a href="${pageContext.request.contextPath}/taskAdder">Add new Task</a>

</body>

</html>