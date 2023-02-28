# Spring Demo app plan

This is a computer buying app:

- The client can buy computers only if there are enough
- The provider can provision/deprovision computer until it reaches the limit
- Computers have 3 types: Windows, Mac, Linux

## Webservices endpoints

See index in /

## Data structures

Computer:

- int id
- String brand
- String version
- String description
- String serialNumber
- double price

Computer Store:

- int computer_id
- int stock
- Date lastProvisionDate
- Boolean enabled

Sale:

- int id
- String clientName
- String salesman
- int computer_id
- int quantity
- Date saleDate

## New webservice ideas

- Make the computer limit modifiable
- Add the store concept (is not available at that store)
- Add the money concept
- Add a complaint webservice

## TODO

- More tests
- I lost the game

## How to setup the mysql database for Windows

- Download Mysql, make sure Mysql service will be launched at startup
- If not started, start mysql by lauching mysqld.exe in Mysql installation folder
- If mysql is still not reachable, execute the windows command services.msc, look for mysql, start the service, and make
  sure it will be launched at startup
- For Dbeaver, Edit Connection, go to Driver Settings, in Driver Properties, set allowPublicKeyRetrieval to True