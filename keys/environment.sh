#!/bin/bash

PROPERTIES_FILE=private.properties

source $PROPERTIES_FILE
export $(cut -d= -f1 $PROPERTIES_FILE)
