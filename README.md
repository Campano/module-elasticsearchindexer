<!--
 ___ _            _ _    _ _    __
/ __(_)_ __  _ __| (_)__(_) |_ /_/
\__ \ | '  \| '_ \ | / _| |  _/ -_)
|___/_|_|_|_| .__/_|_\__|_|\__\___|
            |_| 
-->
![](https://docs.simplicite.io//logos/logo250.png)
* * *

`ElasticSearchIndexer` module definition
========================================

Elastic Search playground module
====================

This module is used as a way to index Simplicité's apps contents into an ElasticSearch Instance.

It is an pre-pre-alpha MVP.

Run an elastic search instance
---------------------------

Create `docker-compose.yml`

```yml
version: "3"
services:
  elasticsearch:
    image: elasticsearch:7.10.1
    restart: always    
    environment:
      discovery.type: single-node
    container_name: elasticsearch
    ports:
     - 9200:9200
     - 9300:9300
  kibana:
    image: kibana:7.10.1
    restart: always
    container_name: kibana
    ports:
      - 5601:5601
```

Run docker and expose port

```shell
sudo docker-compose up -d
ngrok http 9200
```

Configure `ESI_CONFIG` with ES instance

```json
{
	"instance": "http://78e183b5193e.ngrok.io"
}
```

