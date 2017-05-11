host.cpu
----------------


#### Description

返回物理节点的 CPU 信息

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
|cpu_load_one    |float |1 minute load avg., -1.0 = unknown|
|cpu_load_five   |float |5 minute load avg., -1.0 = unknown|
|cpu_load_fifteen|float |1 minute load avg., -1.0 = unknown|
|cpu_proc_run    |long  |total number of running processes |
|cpu_proc_total  |long  |total number of processes |
|cpu_num         |long  |number of CPUs |
|cpu_speed       |long  |speed in MHz of CPU |
|cpu_uptime      |long  |seconds since last reboot |
|cpu_user        |long  |user time (ms) |
|cpu_nice        |long  |nice time (ms) |
|cpu_system      |long  |system time (ms) |
|cpu_idle        |long  |idle time (ms) |
|cpu_waiting_io  |long  |time waiting for I/O to complete (ms) |
|cpu_intr        |long  |time servicing interrupts (ms) |
|cpu_sintr       |long  |time servicing soft interrupts (ms) |
|cpu_interrupts  |long  |interrupt count |
|cpu_contexts    |long  |context switch count |




#### Examples

Request: 

    {
      "jsonrpc": "2.0",
      "method": "host.cpu", 
      "params": {"timestamp": 1494494478860, 
                 "ip": "10.12.25.25"
      }, 
      "id": "cd5809f0-7196-45ed-8e74-eac63b12703b"
    }
    
Response: 

    {
      "jsonrpc": "2.0",
      "result": [
        {
        "host_ip": "10.12.25.25",
        "timestamp": 1494493898563,
        "cpu_load_one": 4.62,
        "cpu_load_five": 4.93,
        "cpu_load_fifteen": 5.07,
        "cpu_proc_run": 5,
        "cpu_proc_total": 950,
        "cpu_num": 40,
        "cpu_speed": 2199,
        "cpu_uptime": 800726,
        "cpu_user": 3417511050,
        "cpu_nice": 19340,
        "cpu_system": 876107960,
        "cpu_idle": 2044988784,
        "cpu_waiting_io": 41357100,
        "cpu_intr": 2140,
        "cpu_sintr": 18457350,
        "cpu_interrupts": 3748316162,
        "cpu_contexts": 4051211051
      },
        {
        "host_ip": "10.12.25.25",
        "timestamp": 1494493934665,
        "cpu_load_one": 4.62,
        "cpu_load_five": 4.9,
        "cpu_load_fifteen": 5.06,
        "cpu_proc_run": 3,
        "cpu_proc_total": 938,
        "cpu_num": 40,
        "cpu_speed": 2200,
        "cpu_uptime": 800762,
        "cpu_user": 3417643420,
        "cpu_nice": 19340,
        "cpu_system": 876165720,
        "cpu_idle": 2046246494,
        "cpu_waiting_io": 41358350,
        "cpu_intr": 2140,
        "cpu_sintr": 18458100,
        "cpu_interrupts": 3748953622,
        "cpu_contexts": 4055372956
      },
      ...
    ],
      "id": "cd5809f0-7196-45ed-8e74-eac63b12703b"
    }
