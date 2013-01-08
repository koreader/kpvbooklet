#! /bin/bash

HACKNAME="kpvbooklet"
PKGNAME="${HACKNAME}"
PKGVER="0.2.N"

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
if ! ${TAR_BIN} --version | grep "GNU tar" > /dev/null 2>&1 ; then
	echo "You need GNU tar to build this package."
	exit 1
fi

## Move stuff around a bit to have everything in the right place in the least amount of steps, and without duplicating stuff in the repo
[ ! -d build/install ] && mkdir build/install
[ ! -d build/uninstall ] && mkdir build/uninstall

cp -f install.ffs build/install/install.ffs
cp -f build/jar/KPVBooklet.jar build/install/KPVBooklet.jar
cp -f mimes.sql build/install/mimes.sql

cp -f uninstall.ffs build/uninstall/uninstall.ffs

## K5
# Install
kindletool create ota2 -d k5w -d k5g -d k5gb -d pw -d pwg -d pwgb -C build/install update_${PKGNAME}_${PKGVER}_install.bin
# Uninstall
kindletool create ota2 -d k5w -d k5g -d k5gb -d pw -d pwg -d pwgb -C build/uninstall update_${PKGNAME}_${PKGVER}_uninstall.bin

zip kindle-kpvbooklet-${PKGVER}.zip *.bin README_FIRST.txt ChangeLog.txt

