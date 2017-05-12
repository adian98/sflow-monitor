virt.description
----------------

#### Description

This method allows to retrieve the virtual machine descriptions

#### Parameters

none

#### Return values

array:


|     Key        | Type |                                             Description                                 |
|----------------|------|-----------------------------------------------------------------------------------------|
|host_ip         |string|最近一次运行的物理节点 ip|
|hostname        |string|hostname(虚拟机id)|

#### Examples

Request: 

    {
      "jsonrpc":"2.0",
      "method":"virt.description",
      "id":1,
      "params":{}
    }
    
Response: 

    {
      "jsonrpc": "2.0",
      "result": [
        {
        "host_ip": "10.12.25.25",
        "hostname": "39d217c7-3b1e-4268-8031-8caf6592f1ac"
      },
        {
        "host_ip": "10.12.25.25",
        "hostname": "b71b106d-7f96-4bad-803c-a78dd51ba1ed"
      },
      ...
    ],
      "id": 1
    }
