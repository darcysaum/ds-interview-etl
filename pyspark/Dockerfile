FROM bde2020/spark-submit:2.3.0-hadoop2.7

MAINTAINER Cecile Tonglet <cecile.tonglet@tenforce.com>

COPY template.sh /

# Copy the requirements.txt first, for separate dependency resolving and downloading
COPY requirements.txt /app/
RUN cd /app \
      && pip3 install -r requirements.txt

# Copy the source code
COPY . /app

ENTRYPOINT ["/bin/bash", "/template.sh"]
