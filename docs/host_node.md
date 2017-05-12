host.node
----------------

#### Description

返回物理节点的资源信息

#### Parameters

|Parameter|Required|  Type  |                             Description                                      |
|---------|--------|--------|------------------------------------------------------------------------------|
|ip       |true    |string  |节点IP|
|timestamp|false   |long    |UNIX 时间戳(milliseconds), 返回该时间戳及10分钟之前的所有信息，如果不指定返回当前时间及10分钟之前的所有信息|

#### Return values

array:

|     Key         | Type |                                             Description                                 |
|-----------------|------|-----------------------------------------------------------------------------------------|
|host_ip          |string|ip|
|timestamp        |long  |timestamp (milliseconds)|
|vnode_mhz        |long  |expected CPU frequency |
|vnode_cpus       |long  |the number of active CPUs|
|vnode_memory     |long  |memory size in bytes|
|vnode_memory_free|long  |unassigned memory in bytes|
|vnode_num_domains|long  |number of active domains(虚拟机)|

#### Examples

Request: 

    {
      "jsonrpc": "2.0",
      "method": "host.node", 
      "params": {"ip": "10.12.25.25", 
                 "timestamp": 1494498014465
      },
      "id": "4a157505-a201-4b94-aa6c-39c77d65dbd1"
    }
    
Response: 

    {
      "jsonrpc": "2.0",
      "result": [
        {
        "host_ip": "10.12.25.25",
        "timestamp": 1494497436718,
        "vnode_mhz": 2199,
        "vnode_cpus": 40,
        "vnode_memory": 134994153472,
        "vnode_memory_free": 454225920,
        "vnode_num_domains": 10
      },
        {
        "host_ip": "10.12.25.25",
        "timestamp": 1494497472579,
        "vnode_mhz": 2199,
        "vnode_cpus": 40,
        "vnode_memory": 134994153472,
        "vnode_memory_free": 455331840,
        "vnode_num_domains": 10
      },
      ...
    ],
      "id": "4a157505-a201-4b94-aa6c-39c77d65dbd1"
    }