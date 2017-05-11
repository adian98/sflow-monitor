host.description
----------------


#### Description

This method allows to retrieve the physical host descriptions

#### Parameters

none

#### Return values

array:


|     Key        | Type |                                             Description                                 |
|----------------|------|-----------------------------------------------------------------------------------------|
|host_ip         |string|ip|
|hostname        |string|hostname|
|machine_type    |string|the processor family|
|os_name         |string|Operating system|
|os_release      |string|e.g. 2.6.9-42.ELsmp,xp-sp3, empty if unknown|

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
