host-sflow install
------------------

git clone git@github.com:sflow/host-sflow.git

cd host-sflow

sudo yum install libpcap-devel

make FEATURES="HOST KVM OVS PCAP"

sudo make install

sudo vim /etc/hsflowd.conf

   
    sflow {
           polling = 30
           sampling = 400
           collector { ip=127.0.0.1 udpport=6343 } #sflow-monitor 服务器 IP
           pcap { dev = virbr0 }
           kvm { }
    }
     
sudo systemctl enable hsflowd.service

sudo systemctl restart hsflowd.service

sudo systemctl status hsflowd.service



