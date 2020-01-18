import os
import shutil
from msvcrt import getch
from pathlib import Path

file = Path(r'build\libs\SkyBlock-1.0.jar')
target = Path(r'C:\Users\lapto\Desktop\jars\server\plugins')
server = Path(r'C:\Users\lapto\Desktop\start_server.bat.lnk')


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

print('\nstart server? [Y/N]: ')
if getch().decode().lower() == 'y':
    print(f'started server at "{server}"')
    os.startfile(server)

