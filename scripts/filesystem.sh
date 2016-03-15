sudo mkfs -t ext4 /dev/xvdb
sudo mkdir /mnt/raid
sudo mount /dev/xvdb /mnt/raid
cd /mnt/raid
sudo mkdir data
sudo chmod -R 777 data
