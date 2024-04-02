#!/bin/bash

outputDir=$(./setupOutputDirectory.sh)
configFilePath=""
tag=""
platform=""

for arg in "$@"; do
    case $arg in
        configFilePath=*)
            configFilePath="${arg#*=}"
            ;;
        Tag=*)
            tag="${arg#*=}"
            ;;
        Platform=*)
            platform="${arg#*=}"
            ;;
        *)
            echo "Invalid argument: $arg"
            exit 1
            ;;
    esac
done

# Check if all required arguments are provided
if [ -z "$configFilePath" ] || [ -z "$tag" ] || [ -z "$platform" ]; then
    echo "Usage: $0 configFilePath=<config file path> Tag=<tag> Platform=<platform>"
    exit 1
fi

# Call Maven with the constructed arguments
mvn compile exec:java -Dexec.mainClass="com.runner.Main" -Dexec.args="$configFilePath $tag $platform $outputDir"