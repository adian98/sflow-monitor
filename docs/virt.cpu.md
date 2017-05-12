virt.cpu
----------------

#### Description

返回虚拟机 CPU 信息

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
|vcpu_state      |long  |virtDomainState|
|vcpu_cpu_time   |long  |the CPU time used (ms)|
|vcpu_cpu_count  |float |number of virtual CPUs for the domain|

#### Examples

Request: 

    {
      "jsonrpc": "2.0",
      "method": "virt.cpu",
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
        "vcpu_state": 1,
        "vcpu_cpu_time": 14757320,
        "vcpu_cpu_count": 1
      },
        {
        "host_ip": "10.12.25.25",
        "timestamp": 1494550256390,
        "hostname": "b5ffdda1-801c-4dc0-905e-03594c8151cd",
        "vcpu_state": 1,
        "vcpu_cpu_time": 14762740,
        "vcpu_cpu_count": 1
      },
      ...
    ],
      "id": "ffdad9f0-167e-4292-88fe-b84ba74d13b9"
    }