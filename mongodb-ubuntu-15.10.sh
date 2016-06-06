#! /bin/bash
# Script to install MongoDB on Ubuntu 15.10. See more details here: http://askubuntu.com/questions/724749/install-mongo-3-2-on-ubuntu-15-10
# Date of creation: June 01, 2016

# Installing MongoDB 3.2
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927
echo "deb http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.2 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.2.list
sudo apt-get update
sudo apt-get install mongodb-org

# Preparing the SYSTEMD script
echo '[Unit]
Description=High-performance, schema-free document-oriented database
After=syslog.target network.target
 
[Service]
User=mongodb
Group=mongodb
ExecStart=/usr/bin/mongod -f /etc/mongod.conf
 
[Install]
WantedBy=multi-user.target' > /lib/systemd/system/mongod.service

# Setup the required directories
mkdir -p /var/run/mongodb/
mkdir -p /var/log/mongodb/
mkdir -p /var/lib/mongodb/
mkdir -p /data/db/

chown mongodb:mongodb /var/run/mongodb/
chown mongodb:mongodb /var/log/mongodb/
chown mongodb:mongodb /var/lib/mongodb/
chown mongodb:mongodb /data/db

chmod 0755 /var/log/mongodb/
chmod 0755 /var/run/mongodb/
chmod 0755 /var/lib/mongodb/
chmod 0755 /data/db/

# Start the new service and enable it on boot
systemctl --system daemon-reload
systemctl enable mongod.service

echo "Starting"
systemctl start mongod.service