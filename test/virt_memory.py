import config
import jsonrpclib
import json
import unittest


class TestVirtMemory(unittest.TestCase):
    def setUp(self):
        self.server = jsonrpclib.ServerProxy(config.url)

    def test_success(self):
        ret = self.server.virt.description()
        for each in ret:
            hostname = each['hostname']
            desc = self.server.virt.memory(hostname = hostname, timestamp=config.now)
            print(json.dumps(desc, indent=2))

        for each in ret:
            hostname = each['hostname']
            desc = self.server.virt.memory(hostname = hostname)
            print(json.dumps(desc, indent=2))

    def test_no_hostname(self):

        with self.assertRaises(Exception):
            ret = self.server.virt.memory()

        with self.assertRaises(Exception):
            ret = self.server.virt.memory(ipp="10.12.25.25")

        with self.assertRaises(Exception):
            ret = self.server.virt.memory(ipp="10.12.25.25", timestamp = config.now)


if __name__ =='__main__':
    unittest.main()