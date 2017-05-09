import unittest
import jsonrpclib

class TestAdd(unittest.TestCase):
    def setUp(self):
        self.server = jsonrpclib.ServerProxy('http://localhost:8080/api')

    def test_add(self):
        ret = self.server.add(x = 6, y = 7);
        self.assertTrue(ret == 13)



if __name__ == '__main__':
    unittest.main()
