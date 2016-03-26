sudo apt-get -y install mdadm
sudo umount -l /mnt
sudo mdadm --create --verbose /dev/md0 --level=0 --name=MY_RAID --raid-devices=2 /dev/xvdb /dev/xvdc
sudo mkfs.ext4 -L MY_RAID /dev/md0
sudo mkdir -p /mnt/raid
sudo mount LABEL=MY_RAID /mnt/raid
cd /mnt/raid
sudo mkdir data
sudo chmod -R 777 data


