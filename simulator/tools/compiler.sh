#!/bin/bash

# Get function from functions library
#. /etc/init.d/functions


# init function that should be called always
init() {
	local DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
	cd $DIR/..
	HOME_DIR=$(pwd)
	#echo "Current dir:" $(pwd)
}

# output the action that is being taken
output() {
	echo ""
	echo "=== $1 ACTION ==="
	echo ""
}
	

# compile
compile() {
	output "COMPILE"
	javac $(find ./src -name *.java) -cp $(find ./lib -name *.jar | tr "\\n" ":") -Xlint:unchecked
}


# build
build() {
	output "BUILD"
	mkdir -p build
	for fn in `find ./src -name *.class`; do
		local sfn=${fn#./src/} # without "./src" prefix 
		local pfn=${sfn%/*} # without ".src" prefix and filename (only path)
		mkdir -p ./build/$pfn
		mv $fn $_ -v # $_ expands to .build/$pnf
		#echo "Moving $fn -> $_/"
	done
}


# run
run() {
	output "RUN"
	java -cp $(find $HOME_DIR/lib -name *.jar | tr "\\n" ":")build com.md.mechevo.Main 
	#echo $(find $HOME_DIR/lib -name *.jar | tr "\\n" ":")build
	#echo HOME: $HOME_DIR
	#echo $(find $HOME_DIR/build -name *.class)
	
}


# clean
clean() {
	output "CLEAN"
	rm -r -v ./build/*
}


# info
info() {
	echo "Home Dir:" $HOME_DIR
	
}


### main logic ###
case "$1" in
	compile)
		init
		compile
		;;
	build)
		init
		build
		;;
	run)
		init
		;;
	clean)
		init
		clean
		;;
	all)
		init
		clean
		compile
		build
		run
		;;
	info)
		init
		info
		;;
	*)
		echo $"Usage: $0 ( compile | build | run [args] | clean | all [run-args] | info)"
		exit 1
esac

exit 0




#cd ..
#javac $(find ./src -name *.java) -cp $(find ./lib -name *.jar | tr "\\n" ":")

#echo "value: $1"


