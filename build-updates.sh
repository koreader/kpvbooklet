#! /bin/bash

HACKNAME="kpvbooklet"
PKGNAME="${HACKNAME}"
PKGVER="20130107"

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
cp -f uninstall.ffs build/uninstall/uninstall.ffs
cp -f mimes.sql build/install/mimes.sql
cp -f build/jar/KPVBooklet.jar build/install/KPVBooklet.jar

## K5
# Install
kindletool create ota2 -d k5w -d k5g -d k5gb -d pw -d pwg -d pwgb -C build/install update_${PKGNAME}-${PKGVER}_install-k5.bin
# Uninstall
kindletool create ota2 -d k5w -d k5g -d k5gb -d pw -d pwg -d pwgb -C build/uninstall update_${PKGNAME}-${PKGVER}_uninstall-k5.bin

zip -m kindle-kpvbooklet-${PKGVER} *.bin

