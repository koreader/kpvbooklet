KPVBooklet for Kindle Touch, Kindle Paperwhite, New Kindle and Kindle Voyage
======================================

KPVBooklet is a Kindle booklet for starting KoReader/HackedUpReader
and updating last access and percentage finished information
in Kindle content catalog entry of the opened document. 

User can switch the open type in KUAL KPVBooklet extentions.

KPVBooklet is licensed under the MIT license. See the file
LICENSE for more details.

## building

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

## troubleshooting

Please include the following in any bug reports.

- `showlog` output from your device
- the firmware version of your device
- your device model
