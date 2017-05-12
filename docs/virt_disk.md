virt.disk
----------------

#### Description

返回虚拟机硬盘 I/O 信息

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
|vdsk_capacity   |long  |logical size in bytes|
|vdsk_allocation |long  |current allocation in bytes|
|vdsk_available  |long  |remaining free bytes|
|vdsk_rd_req     |long  |number of read requests|
|vdsk_rd_bytes   |long  |number of read bytes|
|vdsk_wr_req     |long  |number of write requests|
|vdsk_wr_bytes   |long  |number of  written bytes|
|vdsk_errs       |long  |read/write errors|

#### Examples

Request: 

    {
      "jsonrpc": "2.0",
      "method": "virt.disk",
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
        "vdsk_capacity": 354334801920,
        "vdsk_allocation": 236434870272,
        "vdsk_available": 117899931648,
        "vdsk_rd_req": 2506559,
        "vdsk_rd_bytes": 67064101376,
        "vdsk_wr_req": 838702,
        "vdsk_wr_bytes": 32016053760,
        "vdsk_errs": 0
      },
        {
        "host_ip": "10.12.25.25",
        "timestamp": 1494550256390,
        "hostname": "b5ffdda1-801c-4dc0-905e-03594c8151cd",
        "vdsk_capacity": 354334801920,
        "vdsk_allocation": 236434870272,
        "vdsk_available": 117899931648,
        "vdsk_rd_req": 2506785,
        "vdsk_rd_bytes": 67068067328,
        "vdsk_wr_req": 838809,
        "vdsk_wr_bytes": 32016500736,
        "vdsk_errs": 0
      },
      ...
    ],
      "id": "ffdad9f0-167e-4292-88fe-b84ba74d13b9"
    }