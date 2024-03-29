#! /bin/bash

# Ekstrahiraj direktorij u kojem se nalazi ova skripta:
PRG="$0"
PRGDIR=`dirname "$PRG"`

# Iz tog istog direktorija pozovi postavljanje varijabli okruženja za podatke o derby instalaciji
. "$PRGDIR"/setenv.sh

echo "Pokretanje Apache Derby servera..."
java -Dderby.system.home=$DERBY_DATABASES -jar $DERBY_INSTALL/lib/derbyrun.jar server start
echo "Apache Derby server pokrenut."
