host.cpu
----------------


#### Description

返回物理节点的 CPU 信息

#### Parameters

* `ip` - 必填, 节点IP
* `timestamp` - 可选， UNIX 时间戳(milliseconds), 返回该时间戳及10分钟之前的所有信息，如果不制定默认为当前时间

#### Return values

array:


| Tables        | Are           | Cool  |
| ------------- |:-------------:| -----:|
| col 3 is      | right-aligned | $1600 |
| col 2 is      | centered      |   $12 |
| zebra stripes | are neat      |    $1 |



* `host_ip` - ip, type : string
* `timestamp` - hostname, empty if unknown, type : float
* `cpu_load_one` - 1 minute load avg., -1.0 = unknown, type : float
* `cpu_load_five` - 5 minute load avg., -1.0 = unknown, type : float
* `cpu_load_fifteen` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_proc_run` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_proc_total` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_num` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_speed` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_uptime` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_user` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_nice` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_system` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_idle` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_waiting_io` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_intr` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_sintr` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_interrupts` - 1 minute load avg., -1.0 = unknown, type : long
* `cpu_contexts` - 1 minute load avg., -1.0 = unknown, type : long



#### Examples

Request: 

    {
      "jsonrpc":"2.0",
      "method":"host.description",
      "id":1,
      "params":{}
    }
    
Response: 

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



    


