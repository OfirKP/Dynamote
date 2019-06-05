from pywinauto import Application, win32functions, win32defines
import time

PROGRAMS = ["Adobe Photoshop", "Word", "Explorer"]
windows = {program: None for program in PROGRAMS}

def get_window(title):
    try:
        window = Application()
        window.connect(title_re=(title + ".*"))
        return window.top_window()
    except:
        return None

def update_windows(windows):
    for title in windows:
        if not windows[title] or not windows[title].exists(timeout=1):
            windows[title] = get_window(title)

def get_fg_title(windows, current):
    # fix when program closes
    if current and windows[current] and windows[current].has_focus():
        return current
    else:
        for title, window in windows.items():
            if window and window.has_focus():
                return title
    return None

def main():
    current_fg = None

    while True:
        update_windows(windows)
        current_fg = get_fg_title(windows, current_fg)
        print windows, "Foreground Program:", current_fg
        time.sleep(1.5)

#main()