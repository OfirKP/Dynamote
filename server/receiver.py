from socket import socket, AF_INET, SOCK_DGRAM, SOL_SOCKET, SO_REUSEADDR


class BroadCastReceiver:

    def __init__(self, port, msg_len=8192, timeout=15):
        self.sock = socket(AF_INET, SOCK_DGRAM)
        self.sock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
        self.sock.settimeout(timeout)
        self.msg_len = msg_len
        self.sock.bind(('', port))

    def __iter__(self):
             return self

    def next(self):
             try:
                 addr, data = self.sock.recvfrom(self.msg_len)
                 return addr, data
             except Exception as e:
                 print("Got exception trying to recv %s" % e)
                 raise StopIteration
