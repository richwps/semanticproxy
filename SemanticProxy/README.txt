README

RichWPS SemanticProxy
- System currently under development



System requirements
----------------------
Java SE 1.7
JDK 1.7


Preceding explanations
----------------------
The software provides a directory server for publishing of RDF information via a REST interface.
Described are resources withing spatial data infrastructures:
1. WPS services
2. WPS processes
Formats can be looked up in the sample files.

The SemanticProxy (SP) permits CRUD access to these resources via the REST interface. Since the SP implements the linked data prinicple, all resources must reside within the domain name associated with the server and respect the provided vocabulary.

Create:
A description of the resource can be sent to the list-url of the resource kind specified in the documentation using HTTP POST. The sample data can server as reference. Posted data will be checked for consistence.

Read:
The Data can be browsed using the links in the RDF data. As starting points may serve the list-urls or the root-url specified in the configuration.

Update:
Sent a new resource to the endpoint of an existing one with the same RDF id via HTTP PUT. The resource has to follow the same rules as for a create.

Delete:
Sent a HTTP Delete to the url of the resource to be deleted.



Installation
----------------------
Make sure the SP finds a well configured config.xml file in the CWD. If there is no file, start it once and it will generat default configuration file. You edit this to your wishes. See chapter Configuration.

At start a set of RDF data can be loaded automatically. Therefore you need to specify it in the configuration and provide the RDF files. See the sample data.



Configuration
----------------------
The SP uses an XML file for configuration. See chapter installation.

RDFDirectory:
The directory where to put the db file and logs

StartClean:
true or false to indicate whether the db shall be deleted at system start useful for testing

Owner:
Person or organization that owns/governs the target infrastructure

Domain:
Name or description of the target network

PreloadFiles:
Specifies RDF data to be loaded at system start; Is only done when there is a clean db.
	WPS:     A file that contains RDF information on a WPS
	Process: A file that contains RDF information on a process
	
HTTPEndpoints:
Specifies urls for specialized resources
	Host:        The url of the host machine
	Application: Path segment of the server url
	Resources:   Path segment of the root resource
	Vocabulary:  Path segment of the vocabulary resource
	WPSList:     Path segment of the overview of WPS, servers for create endpoint
	ProcessList: Like WPSList, just for processes
	Search:      Path segment of the search engine
Resulting endpoints
host/appliation/resources
host/appliation/resources/vocabulary
host/appliation/resources/wpslist
host/appliation/resources/processlist
host/appliation/resources/search
	
RDFNamingEndpoints:
Specifies the urls used for naming of resources
	WPSNaming
	ProcessNaming
	InputNaming
	OutputNaming
e.g. host/application/resources/<input>/inputA
