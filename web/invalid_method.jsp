{
  "jsonrpc": "2.0",
  "error": {"code": -32601,
            "message": "Method not found"
  },
  "id": <%
    try {

        String id = (String) request.getAttribute("id");
        out.print(id);
    } catch (Exception e) {
        out.print("null");
    }
    %>
}