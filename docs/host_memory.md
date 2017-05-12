host.memory
----------------

#### Description

返回物理节点的内存信息

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
|mem_total       |long  |total bytes |
|mem_free        |long  |free bytes|
|mem_shared      |long  |shared bytes|
|mem_buffers     |long  |buffers bytes|
|mem_cached      |long  |cached bytes|
|swap_total      |long  |swap total bytes|
|swap_free       |long  |swap free bytes|
|page_in         |long  |page in count|
|page_out        |long  |page out count|
|swap_in         |long  |swap in count|
|swap_out        |long  |swap out count|

#### Examples

Request: 

    {
      "jsonrpc": "2.0",
      "method": "host.memory", 
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
        "mem_total": 134994153472,
        "mem_free": 454225920,
        "mem_shared": 0,
        "mem_buffers": 321040384,
        "mem_cached": 98214662144,
        "swap_total": 34359734272,
        "swap_free": 33039101952,
        "page_in": 832694718,
        "page_out": 2026008742,
        "swap_in": 891790,
        "swap_out": 1724544
      },
        {
        "host_ip": "10.12.25.25",
        "timestamp": 1494497472579,
        "mem_total": 134994153472,
        "mem_free": 455331840,
        "mem_shared": 0,
        "mem_buffers": 321093632,
        "mem_cached": 98215223296,
        "swap_total": 34359734272,
        "swap_free": 33039159296,
        "page_in": 832695338,
        "page_out": 2026044182,
        "swap_in": 891806,
        "swap_out": 1724544
      },
      ...
    ],
      "id": "4a157505-a201-4b94-aa6c-39c77d65dbd1"
    }