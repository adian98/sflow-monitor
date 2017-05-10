<%@ page import="org.apache.commons.lang.StringUtils" language="java"%>
<% String id = (String) request.getAttribute("id"); %>

{
  "jsonrpc": "2.0",
  "error": {"code": -32601,
            "message": "Method not found"
  },
  "id": <%
        if (id == null || StringUtils.isNumeric(id)) {
            out.print(id);
        } else {
            out.print("\"" + id + "\"");
        }
        %>
}