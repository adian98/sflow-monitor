host.net
----------------

#### Description

返回物理节点的网络 I/O 信息

#### Parameters

|Parameter|Required|  Type  |                             Description                                      |
|---------|--------|--------|------------------------------------------------------------------------------|
|ip       |true    |string  |节点IP|
|timestamp|false   |long    |UNIX 时间戳(milliseconds), 返回该时间戳及10分钟之前的所有信息，如果不指定返回当前时间及10分钟之前的所有信息|

#### Return values

array:

|     Key        | Type |                                             Description                                 |
|----------------|------|-----------------------------------------------------------------------------------------|
|host_ip         |string|ip|
|timestamp       |long  |timestamp (milliseconds)|
|bytes_in        |long  |total bytes in |
|packets_in      |long  |total packets in|
|errs_in         |long  |total errors in|
|drops_in        |long  |total drops in|
|bytes_out       |long  |total bytes out|
|packets_out     |long  |total packets out|
|errs_out        |long  |total errors out|
|drops_out       |long  |total drops out|

#### Examples

Request: 

    {
      "jsonrpc": "2.0",
      "method": "host.net", 
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
        "bytes_in": 496120640946,
        "packets_in": 583606453,
        "errs_in": 0,
        "drops_in": 0,
        "bytes_out": 453856636220,
        "packets_out": 528791881,
        "errs_out": 0,
        "drops_out": 2575312
      },
        {
        "host_ip": "10.12.25.25",
        "timestamp": 1494497472579,
        "bytes_in": 496122389387,
        "packets_in": 583624676,
        "errs_in": 0,
        "drops_in": 0,
        "bytes_out": 453868221066,
        "packets_out": 528821433,
        "errs_out": 0,
        "drops_out": 2575833
      },
      ...
    ],
      "id": "4a157505-a201-4b94-aa6c-39c77d65dbd1"
    }