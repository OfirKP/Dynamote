from receiver import BroadCastReceiver
import socket
import pyautogui
import pyperclip
import PSUtils
from programs import update_windows, get_fg_title
import time
import thread

PORT = 8083
current_fg = None
PROGRAMS = ["Adobe Photoshop", "Word", "Explorer"]
windows = {program: None for program in PROGRAMS}

def handle_program_changes(soc):
    current_fg = None
    while True:
        update_windows(windows)
        old_fg = current_fg
        current_fg = get_fg_title(windows, current_fg)
        if old_fg != current_fg:
            st = "switch " + str(current_fg)
            soc.send(st + "\n")
            print st
        time.sleep(1.5)

def handle_auto_connect():
    print "---------- Listening for devices --------------\n"
    r = BroadCastReceiver(8888, timeout=25)
    for data, address in r:
        print 'Got packet from %s: %s' % (address, data)
        # Confirm data from device
        if data == 'connect':
            r.sock.sendto(str(PORT), address)
            break
    print "\n---------- Finished listening for devices --------------\n"

handle_auto_connect()

server = socket.socket()
server.bind(('0.0.0.0', PORT))
server.listen(5)

host_ip = socket.gethostbyname(socket.gethostname())
print "Listening on", "<IP:", host_ip, "Port:", str(PORT) + ">"
print "Ready for connections..."

s, addr = server.accept()
print 'New connection from', addr, "\n"
s.send("Connected to" + host_ip + "\n")
thread.start_new_thread(handle_program_changes, (s,))

while True:
    data = s.recv(1024)
    text = data[1:].strip().lstrip('0')
    params = text.split()
    print text
    if text == "quit":
        break
    elif text == "bold":
        pyautogui.hotkey('ctrl','b')
    elif text == "italic":
        pyautogui.hotkey('ctrl','i')
    elif text == "underline":
        pyautogui.hotkey('ctrl','u')
    elif text == "mark":
        pyautogui.hotkey('ctrl','alt', 'h')
    elif text == "click":
        pyautogui.click()
    elif text == "forward":
        pyautogui.hotkey('pagedown')
    elif text == "back":
        pyautogui.hotkey('pageup')
    elif text == "increase":
        pyautogui.hotkey('ctrl','shift', '>')
    elif text == "decrease":
        pyautogui.hotkey('ctrl','shift', '<')
    elif params[0] == "rel":
        pyautogui.moveRel(int(params[1]),int(params[2]), 0.5)
    elif params[0] == "type":
        pyautogui.typewrite(" ".join(params[1:]), interval=.01)
    elif params[0] == "pos":
        pyautogui.moveTo(int(params[1]),int(params[2]),1)
    elif params[0] == "color":
        pyperclip.copy(params[1])
        PSUtils.set_fg_color(params[1])
    elif params[0] == "tool":
        PSUtils.set_tool(params[1])
    else:
        pyperclip.copy(text)
s.close()
