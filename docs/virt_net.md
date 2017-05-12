virt.net
----------------

#### Description

返回虚拟机网络 I/O 信息

#### Parameters

|Parameter|Required|  Type  |                             Description                                      |
|---------|--------|--------|------------------------------------------------------------------------------|
|hostname |true    |string  |hostname(虚拟机id)|
|timestamp|false   |long    |UNIX 时间戳(milliseconds), 返回该时间戳及10分钟之前的所有信息，如果不指定返回当前时间及10分钟之前的所有信息|

#### Return values

array:

|     Key        | Type |                                             Description                                 |
|----------------|------|-----------------------------------------------------------------------------------------|
|host_ip         |string|当时所在物理节点的 ip|
|timestamp       |long  |timestamp (milliseconds)|
|hostname        |string|hostname(虚拟机id)|
|vnio_bytes_in   |long  |total bytes received|
|vnio_packets_in |long  |total packets received|
|vnio_errs_in    |long  |total receive errors|
|vnio_drops_in   |long  |total receive drops|
|vnio_bytes_out  |long  |total bytes transmitted|
|vnio_packets_out|long  |total packets transmitted|
|vnio_errs_out   |long  |total transmit errors|
|vnio_drops_out  |long  |total transmit drops|

#### Examples

Request: 

    {
      "jsonrpc": "2.0",
      "method": "virt.net",
      "params": {"hostname": "b5ffdda1-801c-4dc0-905e-03594c8151cd",
                 "timestamp": 1494550545200,
      },
      "id": "ffdad9f0-167e-4292-88fe-b84ba74d13b9" 
    }
    
Response: 

    {
      "jsonrpc": "2.0",
      "result": [
        {
        "host_ip": "10.12.25.25",
        "timestamp": 1494550220301,
        "hostname": "b5ffdda1-801c-4dc0-905e-03594c8151cd",
        "vnio_bytes_in": 70423200,
        "vnio_packets_in": 441637,
        "vnio_errs_in": 0,
        "vnio_drops_in": 0,
        "vnio_bytes_out": 869138582,
        "vnio_packets_out": 1141061,
        "vnio_errs_out": 0,
        "vnio_drops_out": 0
      },
        {
        "host_ip": "10.12.25.25",
        "timestamp": 1494550256390,
        "hostname": "b5ffdda1-801c-4dc0-905e-03594c8151cd",
        "vnio_bytes_in": 70439294,
        "vnio_packets_in": 441757,
        "vnio_errs_in": 0,
        "vnio_drops_in": 0,
        "vnio_bytes_out": 869219176,
        "vnio_packets_out": 1141231,
        "vnio_errs_out": 0,
        "vnio_drops_out": 0
      },
      ...
    ],
      "id": "ffdad9f0-167e-4292-88fe-b84ba74d13b9"
    }