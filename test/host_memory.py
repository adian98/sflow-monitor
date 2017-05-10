import config
import jsonrpclib
import json
import unittest


class TestNode(unittest.TestCase):
    def setUp(self):
        self.server = jsonrpclib.ServerProxy(config.url)

    def test_success(self):
        ret = self.server.host.memory(ip="10.12.25.25", timestamp = config.now)
        print(json.dumps(ret, indent=2))

    def test_no_timestamp(self):
        ret = self.server.host.memory(ip="10.12.25.25")
        print(json.dumps(ret, indent=2))

    def test_no_ip(self):

        with self.assertRaises(Exception):
            ret = self.server.host.memory()

        with self.assertRaises(Exception):
            ret = self.server.host.memory(ipp="10.12.25.25")

        with self.assertRaises(Exception):
            ret = self.server.host.node(ipp="10.12.25.25", timestamp = config.now)
            

if __name__ =='__main__':
    unittest.main()