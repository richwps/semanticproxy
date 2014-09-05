README
----------------------

RichWPS SemanticProxy
- System currently under development


TOC
----------------------
* System requirements
* Preceding explanations
* Configuration
* Start
  * Installation


System requirements
----------------------
* Java SE 1.7
* JDK 1.7


Preceding explanations
----------------------
The software provides a directory server for publishing of RDF information via a REST interface.
Described are resources within spatial data infrastructures:

1. WPS services
2. WPS processes

Formats can be looked up in the sample files.

The SemanticProxy (SP) permits CRUD access to these resources via the REST interface. Since the SP implements the linked data prinicples, all resources must reside within the domain name associated with the server and respect the provided vocabulary.

**Create:**
A description of the resource can be sent to the list-URL of the resource kind, specified in the documentation using HTTP POST. The sample data can serve as reference. Posted data will be checked for consistence.

**Read:**
The data can be browsed using the links in the RDF data. As starting points may serve the list-URLs or the root-URL specified in the configuration.

**Update:**
Send a new resource to the endpoint of an existing one with the same RDF id via HTTP PUT. The resource has to follow the same rules as for a create. If there is no resource at this endpoint the resource will be created.

**Delete:**
Send a HTTP Delete to the URL of the resource to be deleted.



Configuration
----------------------
Make sure the SP finds a well configured config.xml file in the CWD. If there is no file, start it once and it will generate a default configuration file. You may edit this to your wishes.
At start a set of RDF data can be loaded automatically. Therefore you need to specify it in the configuration and provide the RDF files. See the sample data.
In the following the configuration options are outlined:

**RDFDirectory:**
The directory where to put the dDB file and logs

**StartClean:**
true or false to indicate whether the DB shall be deleted at system start useful for testing

**Owner:**
Person or organization that owns/governs the target infrastructure

**Domain:**
Name or description of the target network

**Port:**
Port to use by the web server

**PreloadFiles:**
Specifies RDF data to be loaded at system start; Is only done when there is a fresh DB.

	WPS:             A file that contains RDF information on a WPS
	Process:         A file that contains RDF information on a process
	ReplaceableHost: Attribute; Contains wildcard string to be replaced with the host name
	
**HTTPEndpoints:**
Specifies URLs for specialized resources

	Host:        The URL of the host machine
	Application: Path segment of the server URL
	Resources:   Path segment of the root resource
	Vocabulary:  Path segment of the vocabulary resource
	WPSList:     Path segment of the overview of WPS, servers for create endpoint
	ProcessList: Like WPSList, just for processes
	Search:      Path segment of the search engine

Resulting endpoints

* host/application/resources
* host/application/resources/vocabulary
* host/application/resources/wpslist
* host/application/resources/processlist
* host/application/resources/search
	
RDFNamingEndpoints:
Specifies the URLs used for naming of resources

	WPSNaming
	ProcessNaming
	InputNaming
	OutputNaming
e.g. host/application/resources/<input>/inputA


Start
----------------------
1. CD to directory [...]\SemanticProxy
2. type: java -jar target\SemanticProxy-1.0-SNAPSHOT.jar

# Installation

## Ubuntu

* `sudo adduser semanticproxy --disabled-password`
* `sudo su - semanticproxy`

* place binaries at /SemanticProxy

* edit `~/startproxy.sh`

.

	#!/bin/bash
	cd /home/semanticproxy/SemanticProxy
	nohup java -jar target/SemanticProxy-1.0-SNAPSHOT.jar &

* `chmod u+x startproxy.sh`

As root.

* edit `/etc/init.d/semanticproxy`

.
	
	#! /bin/sh
	### BEGIN INIT INFO
	# Provides:          Starts semantic proxy.
	# Required-Start:    
	# Required-Stop:     
	# Default-Start:     2 3 4 5
	# Default-Stop:      0 1 6
	# Short-Description: Kurze Beschreibung
	# Description:       Längere Bechreibung
	### END INIT INFO
	# Author: Name <email@domain.tld>

	user=semanticproxy
	bin_dir=/home/semanticproxy
	start_log=${bin_dir}/init.log

	case "$1" in
	    start)
		echo "Starting semanticproxy."
	        sudo -u ${user} /home/semanticproxy/startproxy.sh >> ${start_log}
	        ;;
	    stop)
		echo "Stoping semanticproxy"
		pgrep -u semanticproxy java | xargs kill	
	        ;;
	    restart)
		echo "Restarting semanticproxy"
		pgrep -u semanticproxy java | xargs kill
		sudo -u ${user} /home/semanticproxy/startproxy.sh >> ${start_log}
	        ;;
	esac

	exit 0

* `update-rc.d semanticproxy default`
