host.disk
----------------


#### Description

返回物理节点的硬盘 I/O 信息

#### Parameters


|Parameter|Required|  Type  |                             Description                                      |
|---------|--------|--------|------------------------------------------------------------------------------|
|ip       |true    |string  |节点IP|
|timestamp|false   |long    |UNIX 时间戳(milliseconds), 返回该时间戳及10分钟之前的所有信息，如果不指定默认为当前时间|

#### Return values

array:

|     Key        | Type |                                             Description                                 |
|----------------|------|-----------------------------------------------------------------------------------------|
|host_ip         |string|ip|
|timestamp       |long  |timestamp|
|disk_total      |long  |total disk size in bytes|
|disk_free       |long  |total disk free in bytes|
|part_max_used   |float |utilization of most utilized partition|
|reads           |long  |reads issued|
|bytes_read      |long  |bytes read|
|read_time       |long  |read time (ms)|
|writes          |long  |writes completed|
|bytes_written   |long  |bytes written|
|write_time      |long  |write time (ms)|


#### Examples

Request: 

    {
      "jsonrpc": "2.0",
      "method": "host.disk", 
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
        "disk_total": 7886442244096,
        "disk_free": 5492770930688,
        "part_max_used": 49.75,
        "reads": 12761694,
        "bytes_read": 966276791296,
        "read_time": 60031297,
        "writes": 95505528,
        "bytes_written": 2335413551104,
        "write_time": 529538150
      },
        {
        "host_ip": "10.12.25.25",
        "timestamp": 1494497472579,
        "disk_total": 7886442244096,
        "disk_free": 5492770824192,
        "part_max_used": 49.75,
        "reads": 12761879,
        "bytes_read": 966277749760,
        "read_time": 60031432,
        "writes": 95509425,
        "bytes_written": 2335453519872,
        "write_time": 529543107
      },
      ....
    ],
      "id": "4a157505-a201-4b94-aa6c-39c77d65dbd1"
    }
