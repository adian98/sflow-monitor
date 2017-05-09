{
  "jsonrpc": "2.0",
   "error": {"code": ${requestScope.error.code},
             "message": ${requestScope.error.message}
   },
   "id": ${requestScope.error.id < 0 ? "null" : requestScope.error.id}
}