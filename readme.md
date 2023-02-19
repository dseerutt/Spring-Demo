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

Tests Hibernate SQL Hibernate JPQL DSL Autorisation roles """