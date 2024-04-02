#!/bin/bash

# Function to get the current month
getMonth() {
    date +"%Y-%m"
}

# Function to get the current datestamp
getCurrentDatestamp() {
    date +"%Y-%m-%d"
}

# Function to get the current timestamp
getCurrentTimestamp() {
    date +"%H-%M-%S"
}

# Set up log directory
outputDirectory="./target/$(getMonth)/$(getCurrentDatestamp)/$(getCurrentTimestamp)"
echo "$outputDirectory"