import os
import shutil
from msvcrt import getch
from pathlib import Path
import psutil


def window_titles():
    import ctypes

    EnumWindows = ctypes.windll.user32.EnumWindows
    EnumWindowsProc = ctypes.WINFUNCTYPE(ctypes.c_bool, ctypes.POINTER(ctypes.c_int), ctypes.POINTER(ctypes.c_int))
    GetWindowText = ctypes.windll.user32.GetWindowTextW
    GetWindowTextLength = ctypes.windll.user32.GetWindowTextLengthW
    IsWindowVisible = ctypes.windll.user32.IsWindowVisible

    titles = []

    def foreach_window(hwnd, lParam):
        if IsWindowVisible(hwnd):
            length = GetWindowTextLength(hwnd)
            buff = ctypes.create_unicode_buffer(length + 1)
            GetWindowText(hwnd, buff, length + 1)
            titles.append(buff.value)
        return True

    EnumWindows(EnumWindowsProc(foreach_window), 0)

    return titles


file = Path(r"D:\git\spigot-private\MiscPlugin\build\libs\MiscPlugin.jar")
target = Path(r"D:\servers\spigot\1.14.4\plugins")
server = Path("D:/servers/spigot/1.14.4/launch_server.bat")
base = Path("D:/servers/spigot/1.14.4/")

SERVER_PROC_NAME = 'launch_server.bat'


def fail(err):
    reason = f'FAILED: {err}'
    pad = 'FAIL'.center(len(reason), '-')
    print(f'{pad}/n{reason}/n{pad}')
    exit()


if not file.exists():
    fail(f'file path "{file}" does not exists!')

if not target.exists():
    fail(f'target folder "{target}" does not exists')

if (final := target / file.name).exists():
    try:
        os.remove(final)
    except Exception as e:
        print(f'error trying to remove "{final}":/n/t{e}')

os.system('gradlew jar')

shutil.copy(file, target)
print(f'copied "{file}" to "{final}"')

if not any(SERVER_PROC_NAME in proc for proc in window_titles()):
    os.chdir(base)
    os.startfile(server)
    print(f'started the server at {server}')
    # os.system(str(server))
else:
    print('server already started, running reload...')

    import pyautogui

    win = pyautogui.getWindowsWithTitle(SERVER_PROC_NAME)
    if not win:
        exit()

    win = win[0]
    win.activate()

    pyautogui.typewrite('reload')
    pyautogui.press('enter')

    win = pyautogui.getWindowsWithTitle("minecraft 1.14.4")
    if not win:
        exit()

    win = win[0]
    win.activate()

    pyautogui.press('escape')

