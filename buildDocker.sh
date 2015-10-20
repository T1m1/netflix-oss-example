#!/usr/bin/env bash

cd microservices/core/user-service;                                         		mvn clean install package docker:build; cd -
cd microservices/core/message-service;                                         		mvn clean install package docker:build; cd -
cd microservices/core/document-service;                                 			mvn clean install package docker:build; cd -

cd microservices/composite/mailbox-service;                               			mvn clean install package docker:build; cd -

cd microservices/support/discovery-server;                                        	mvn clean install package docker:build; cd -
cd microservices/support/edge-server;	                                        	mvn clean install package docker:build; cd -		                               			
cd microservices/support/dashboard-service;											mvn clean install package docker:build; cd -

cd web;																				docker build -t t1m1/web .; cd -

find . -name *SNAPSHOT.jar -exec du -h {} \;
docker images