FROM picoded/ubuntu-openjdk-8-jdk
RUN mkdir /root/scripts
RUN apt-get update
RUN apt-get install -y python3
RUN rm -f /usr/bin/python
RUN ln -s /usr/bin/python3 /usr/bin/python
RUN apt-get install -y python3-pip
RUN pip3 install --upgrade pip
COPY install-python-modules.sh /root/scripts/.
RUN /root/scripts/install-python-modules.sh
RUN apt-get install -y openssh-server
RUN echo "PermitRootLogin yes" > /etc/ssh/sshd_config
RUN echo "StrictHostKeyChecking no" > /etc/ssh/ssh_config
RUN rm -rf /root/.ssh
RUN mkdir /root/.ssh
COPY id_rsa /root/.ssh/.
COPY id_rsa.pub /root/.ssh/.
COPY authorized_keys /root/.ssh/.
COPY config.sh /root/scripts/.
RUN cat /root/scripts/config.sh >> /root/.bashrc
COPY start-ssh.sh /root/scripts/.
CMD /root/scripts/start-ssh.sh
