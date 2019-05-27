import bluetooth
import uuid

services = bluetooth.find_service(name="HelloWorld", uuid=bluetooth.SERIAL_PORT_CLASS)

for i in range(len(services)):
    match = services[i]
    if match["name"] == "HelloWorld":
        port = match["port"]
        name = match["name"]
        host = match["host"]

        print(name, port, host)

        client_sock = bluetooth.BluetoothSocket( bluetooth.RFCOMM )
        client_sock.connect((host, port))
        while True:
            send_msg = input("input : ")
            client_sock.send(send_msg)
            print("sent [%s]" % send_msg)
            recv_msg = client_sock.recv()
            print("received [%s]" % recv_msg)
        client_sock.close()
        break
