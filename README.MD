slow-monitor
============


接收，储存 sFlow 数据报，查询 KVM 节点和虚拟机集群运行状态

sFlow data format, see:[http://www.sflow.org](http://www.sflow.org)

JSON-RPC specification, see:[http://json-rpc.org/wiki/specification](http://json-rpc.org/wiki/specification)

[configure tomcat](/docs/configure_tomcat.md)

[configure host-sflow](/docs/configure_host_flow.md)

[API](/docs/api.md)
----

#### Performing requests
Once you've set up the servlet, you can use remote HTTP requests to call the [API](/docs/api.md). 
To do that you need to send HTTP POST requests to the `/api`. For example, if your `sflow-monitor` is installed under `http://localhost:8080/sflow-monitor`, 
the HTTP request to call the `host.description` method may look like this:

    POST http://localhost:8080/sflow-monitor/api HTTP/1.1
    Content-Type: application/json-rpc
      
    {
      "jsonrpc":"2.0",
      "method":"host.description",
      "id":1,
      "params":{}
    }

Let's take a closer look at the request object. It has the following properties: 

* `jsonrpc` - the version of the JSON-RPC protocol used by the API; the `slow-monitor` [API](/docs/api.md) implements JSON-RPC version 2.0;
* `method` - the API method being called;
* `params` - parameters that will be passed to the API method;
id - an arbitrary identifier of the request;

If you provided the credentials correctly, the response returned by the [API](/docs/api.md) will contain the host description token: 

    {
      "jsonrpc": "2.0",
      "result": [
        {
        "host_ip": "10.12.25.25",
        "hostname": "cloud25025",
        "machine_type": "x86_64",
        "os_name": "linux",
        "os_release": "3.10.0-123.8.1.el7.x86_64"
      },
        {
        "host_ip": "10.12.31.1",
        "hostname": "cloud31001",
        "machine_type": "x86_64",
        "os_name": "linux",
        "os_release": "3.10.0-123.8.1.el7.x86_64"
      },
       .....
    ],
      "id": 1
    }
    
The response object in turn contains the following properties: 
* `jsonrpc` - again, the version of the JSON-RPC protocol;
* `result` - the data returned by the method;
* `id` - identifier of the corresponding request
    
#### Error handling

rpc call of non-existent method:

    --> {"jsonrpc": "2.0", "method": "foobar", "id": "1"}
    <-- {"jsonrpc": "2.0", "error": {"code": -32601, "message": "Method not found"}, "id": 1}
    
rpc call with invalid JSON:

    --> {"jsonrpc": "2.0", "method": "foobar, "params": "bar", "baz]
    <-- {"jsonrpc": "2.0", "error": {"code": -32700,"message": "Parse error"}, "id": null}
    
rpc call with invalid Request object:

    --> {"jsonrpc": "2.0", "method": "foobar", "params": "bar"}
    <-- {"jsonrpc": "2.0", "error": {"code": -32600, "message": "Invalid Request"}, "id": null}
    
#### Performing requests using json-rpc
This is obviously taken from a console session:

    python3
    >>> import jsonrpclib
    >>> server = jsonrpclib.ServerProxy('http://localhost:8080/api')
    >>> server.virt.memory(hostname = '1ff5a54e-c885-4153-9005-f42478261ada')
       
    {
      "jsonrpc": "2.0",
      "result": [
        {
        "host_ip": "10.12.25.25",
        "timestamp": 1494484658661,
        "hostname": "1ff5a54e-c885-4153-9005-f42478261ada",
        "vmem_memory": 8589934592,
        "vmem_max_memory": 8589934592
      },
        {
        "host_ip": "10.12.25.25",
        "timestamp": 1494484694732,
        "hostname": "1ff5a54e-c885-4153-9005-f42478261ada",
        "vmem_memory": 8589934592,
        "vmem_max_memory": 8589934592
      },
      ...
    ],
      "id": "34bf7218-d5ba-4b60-b4f3-31dd3071c534"
    }
    
compile and deployment
----------------------

在主服务器编译运行 sflow-monitor， 启动 tomcat, [configure tomcat](/docs/configure_tomcat.md)

在每个物理节点安装 host-sflow, 配置指向主服务器, [configure host-flow](docs/configure_host_flow.md)
    
    



