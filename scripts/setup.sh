#!/bin/bash
filename='confFiles/slaves'
filelines=`cat $filename`
echo Start
for p in $filelines ; do
  	echo $p:
	#scp -i prod.pem prod.pem ubuntu@$p:
	#scp -i prod.pem ssh_script.sh ubuntu@$p:
	#ssh -i prod.pem ubuntu@$p 'sudo chmod +x ssh_script.sh'
	#ssh -i prod.pem ubuntu@$p './ssh_script.sh'
	#scp -i prod.pem filesystem.sh ubuntu@$p:
	
	#ssh -i prod.pem ubuntu@$p './filesystem.sh'
	scp -i prod.pem confFiles/* ubuntu@$p:/home/ubuntu/hadoop/etc/hadoop/
	echo 'adsa'
done
