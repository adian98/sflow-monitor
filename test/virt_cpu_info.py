import config
import jsonrpclib
import json
import unittest


class TestHostCpu(unittest.TestCase):
    def setUp(self):
        self.server = jsonrpclib.ServerProxy(config.url)

    def test_success(self):
        ret = self.server.virt.description()
        for each in ret:
            hostname = each['hostname']
            desc = self.server.virt.cpu(hostname = hostname, timestamp=config.now)
            print(json.dumps(desc, indent=2))

        for each in ret:
            hostname = each['hostname']
            desc = self.server.virt.cpu(hostname = hostname)
            print(json.dumps(desc, indent=2))

    def test_no_timestamp(self):
        ret = self.server.host.cpu(ip="10.12.25.25")
        print(json.dumps(ret, indent=2))

    def test_no_ip(self):

        with self.assertRaises(Exception):
            ret = self.server.virt.cpu()

        with self.assertRaises(Exception):
            ret = self.server.virt.cpu(ipp="10.12.25.25")

        with self.assertRaises(Exception):
            ret = self.server.virt.cpu(ipp="10.12.25.25", timestamp = config.now)


if __name__ =='__main__':
    unittest.main()