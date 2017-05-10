import config
import jsonrpclib
import json

if __name__ =='__main__':
    server = jsonrpclib.ServerProxy(config.url)
    ret = server.virt.description()
    print(json.dumps(ret,indent=2))