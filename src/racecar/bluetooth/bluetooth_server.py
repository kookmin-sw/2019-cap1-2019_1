import threading
import bluetooth

def register_service():
    service_name = "Armatus Bluetooth server"
    svc_dsc = "A HERMIT server that interfaces with the Armatus Android app"
    service_prov = "Armatus"

    svc_uuid_str = "00001101-0000-1000-8000-00805F9B34FB"
    #svc_uuid = uuid.UUID(svc_uuid_str)
    print("Registering UUID %s" % svc_uuid_str)

    bluetooth.advertise_service(server_sock, "HelloWorld",\
                         service_classes=[bluetooth.SERIAL_PORT_CLASS],\
                         profiles=[bluetooth.SERIAL_PORT_PROFILE],\
                         provider=service_prov, description=svc_dsc)

def read_server(client_sock):
    # read data from the client
    data = client_sock.recv(1024)
    data = data.decode("utf-8")
    print("received :", data)
    return data

def write_server(client_sock):
    # send data to the client
    msg = input("input msg : ")
    client_sock.send(msg)
    print("sent :", msg)

def ThreadMain(client_sock):
    # id=0
    while True:
        # print("Thread", id); id+=1
        recv_message = read_server(client_sock)
        if recv_message is None:
            print("client disconnected")
            break
        # print(recv_message)
        # write_server(client_sock)
    print("disconnected")
    client.close()
    return 0

if __name__ == '__main__':
    # allocate socket
    server_sock = bluetooth.BluetoothSocket( bluetooth.RFCOMM )
    print("socket() returned...")

    # bind socket to some port of the first abailable
    port = bluetooth.PORT_ANY
    server_sock.bind(("", port))
    print("bind() on channel %d returned..." % port)

    # put socket into listening mode
    server_sock.listen(1)
    print("listen() returned...")

    # register service
    register_service()

    threadId = 0
    while True:
        # accept one connection
        print("calling accept()")
        client_sock, address = server_sock.accept()
        print("accept() returned...")

        thread_main = threading.Thread(name='ThreadMain'+str(threadId), target=ThreadMain, args=(client_sock,))
        thread_main.start()
        threadId += 1
