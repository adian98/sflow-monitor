import unittest
import jsonrpclib
import config

class TestAdd(unittest.TestCase):
    def setUp(self):
        self.server = jsonrpclib.ServerProxy(config.url)

    def test_add(self):
        ret = self.server.add(x = 6, y = 7);
        self.assertTrue(ret == 13)

        with self.assertRaises(Exception):
            self.server.notfoundmethod(x=6, y=7);

if __name__ == '__main__':
    unittest.main()
