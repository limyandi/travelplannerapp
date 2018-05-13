#!/bin/bash -x

cd $(dirname $0)
JAR=$(ls ../citygrid-*.jar|grep -v javadoc)
VER=$(echo "$JAR"|perl -ne 'print $1 if /citygrid-(.*?).jar/')
mvn install:install-file -Dfile="$JAR" -DgroupId=com.citygrid -DartifactId=citygrid -Dversion="$VER" -Dpackaging=jar 
