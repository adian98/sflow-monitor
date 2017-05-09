<%@ page import="servlet.controller.Result" language="java"%>
<% Result r = (Result) request.getAttribute("result"); %>
{
  "jsonrpc": "2.0",
  "result": ${requestScope.result.result},
  "id": ${requestScope.result.id}
}