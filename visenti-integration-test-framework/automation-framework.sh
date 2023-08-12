#!/bin/bash
app_name="visenti.automation.framework";
work_dir="/home/dilshani/visenti-test-framework";
pwd_dir=$pwd

cd $work_dir;

if [ "$1" = "" -o "$1" = "start" ]; then
  printf "\n\nStarting $app_name as a background process \n"
  §
fi;


if [ "$1" = "stop" ]; then
  printf "\n\n############# Request to stop $app_name process ###########\n\n"
fi;

cd $pwd_dir;
