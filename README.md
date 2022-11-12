# KPVBooklet for early Kindle 5.x

----

Due to the nature of this hack, and the platform it targets, it suffers from stringent technical limitations that prevent easy support for modern firmware versions.
Given that this repository is unmaintained, and that nobody (understandably) volunteered despite the many years that have passed, this repository has been archived.
If you're an end-user, the short of it is that this will *NOT* work on current firmware versions (in fact, it will likely only work properly on a few select very old firmware versions on a few select older devices).

TL;DR: Use KUAL or [KOL](https://github.com/yparitcher/KUAL_Booklet) to launch KOReader.

----

KPVBooklet is a Kindle booklet for starting KoReader/HackedUpReader
and updating last access and percentage finished information
in Kindle content catalog entry of the opened document.

User can switch the open type in KUAL KPVBooklet extentions.

KPVBooklet is licensed under the MIT license. See the file
LICENSE for more details.

## Building

- gather and install dependencies
  - getting kindle device jars
    - https://github.com/aerickson/kindle-jar-extractor
    - with ssh via usbnetwork hack (see above for directories to copy and destination path).
  - install ant
    - OS X
      - `brew install ant`
    - linux/debian
      - `apt install ant`
  - install kindletool
    - https://github.com/NiLuJe/KindleTool
  - install Java 1.4-1.8
    - Because the Kindle devices use an older version of Java, we need older JDKs that can produce bytecode that's compatible. See https://openjdk.java.net/jeps/182 for details.
      - OpenJDK 8 does work.
      - Oracle Java 10 does not work.
- build and package
  - build the jar
    - `KINDLE_EBOOK=<DIR_WITH_DEVICE_JARS> ant`
  - package
    - `./build-updates.sh`

## Troubleshooting

Please include the following in any bug reports.

- `showlog` output from your device
  - requires SSH access to your device
    - see https://wiki.mobileread.com/wiki/Kindle_Touch_Hacking#USB_Networking
- the firmware version of your device
- your device model
