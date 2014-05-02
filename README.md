Cutlass
========

CS0320 Term Project, Brown University, Spring 2014

Dilip Arumugam, Gaurav Manek, Miles Holland, Zachary Zagorski

Website: http://codewithcutlass.es

Laskura Server (for testing): laskura.codewithcutlass.es


Launching
---------

```
Cutlass
CSCI 0320, Brown University, Fall 2014
Dilip Arumugam, Gaurav Manek, Miles Holland, Zachary Zagorski

Usage: ./cutlass [OPTIONS] [FILES]

OPTIONS
  -h --help      Display this help and exit.

  -n --silent    Silent mode, do not output anything on the commandline.
  -v --verbose   Verbose mode, output all logged information.
  -w --warnings  Verbose mode, output all logged warnings and errors.
  -e --error     Verbose mode, output all errors.

  -f --fresh     Discard saved state and start a brand new session.
  --laskura URL  Connect to a Laskura server at URL instead of using the local disk.

FILES
   Paths of files to load on startup.

Exit status:
  0	if OK,
  1	if the command-line options cannot be parsed,
  2	if the configuration file data cannot be loaded.
```
