import requests
import config
import unittest
import jsonrpclib


class TestFail(unittest.TestCase):
    def setUp(self):
        self.server = jsonrpclib.ServerProxy(config.url)


    def test_invalid_json(self):
        data = '{"jsonrpc": "2.0", "method": "foobar, "params": "bar", "baz]'
        r = requests.post(config.url, data=data)
        #{"jsonrpc": "2.0", "error": {"code": -32700, "message": "Parse error"}, "id": null}
        print("json error : {0}".format(r.json()))

    def test_invalid_request(self):
        data = '{"jsonrpc": "2.0", "method": 1, "params": "bar"}'
        r = requests.post(config.url, data=data)
        print("request error : {0}".format(r.json()))

    def test_invalid_method(selfs):
        data = '{"jsonrpc": "2.0", "method": "foobar", "id": "1"}'
        r = requests.post(config.url, data=data)
        print("method error : {0}".format(r.json()))







if __name__ == '__main__':
    unittest.main()