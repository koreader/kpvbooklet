#! /bin/bash

set -e

HACKNAME="kpvbooklet"
PKGNAME="${HACKNAME}"
PKGVER="0.6.6"

# We need kindletool (https://github.com/NiLuJe/KindleTool) in $PATH
if (( $(kindletool version | wc -l) == 1 )) ; then
	HAS_KINDLETOOL="true"
fi

if [[ "${HAS_KINDLETOOL}" != "true" ]] ; then
	echo "You need KindleTool (https://github.com/NiLuJe/KindleTool) to build this package."
	exit 1
fi

# We also need GNU tar
TAR_BIN="tar"
if [[ "$OSTYPE" == "darwin"* ]] ; then
	TAR_BIN="gtar"
fi
if ! ${TAR_BIN} --version | grep "GNU tar" > /dev/null 2>&1 ; then
	echo "You need GNU tar to build this package."
	exit 1
fi

## Move stuff around a bit to have everything in the right place in the least amount of steps, and without duplicating stuff in the repo
[ ! -d build/install ] && mkdir -p build/install
[ ! -d build/uninstall ] && mkdir -p build/uninstall

cp -f install.ffs build/install/install.ffs
cp -f build/jar/KPVBooklet.jar build/install/KPVBooklet.jar
cp -f mimes.install.sql build/install/mimes.install.sql
cp -f whispertouch.install.sql build/install/whispertouch.install.sql
cp -rf extensions build/install/

cp -f uninstall.ffs build/uninstall/uninstall.ffs
cp -f mimes.uninstall.sql build/uninstall/mimes.uninstall.sql

## K5
# Install
kindletool create ota2 -d kindle5 -C build/install update_${PKGNAME}_${PKGVER}_install.bin
# Uninstall
kindletool create ota2 -d kindle5 -C build/uninstall update_${PKGNAME}_${PKGVER}_uninstall.bin

zip kindle-kpvbooklet-${PKGVER}.zip *.bin README_FIRST.txt ChangeLog.txt

rm *.bin

