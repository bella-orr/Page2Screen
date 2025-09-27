# Group7_EnterpriseApplicationDev

## Introduction

Page2Screen is a web application that enables users to consolidate all their book and movie reviews in one location. Instead 
By browsing different websites to find personal reviews and ratings, users can conveniently write, store, and share their 
opinions on what they read and watch. Within the simple UI, users can add titles, types of media, authors/directors, ratings, 
and comments on their most loved or most disliked works. Page2Screen allows story lovers to create a personalized hub for thoughts and connections.

## Storyboard

## Functional Requirements

1. As a user, I want to be able to add a review for a book or movie, so that I can keep track of my opinions and share them with others. \
**Given**: I am logged in to my account \
**When**: I enter a title, media, rating, or comment and click save \
**Then**: the web application saves my review. 

2. As a user, I want to be able to update or delete a review for a book or movie, so that I can change my opinion or remove content I no longer want to share. \
**Given**: I have already created a review \
**When**: I choose the edit/delete button on that review \
**Then**: the web application updates the changes made. 

2. As a user, I want to be able to see reviews of a book or movie, so I can understand others' opinions on them and decide whether or not to also read/ watch it. \
**Given**: A title exists in the database \
**When**: I search for the title and select it \
**Then**: I can see all the reviews, ratings, and comments with that title name. 

## Class Diagram
```mermaid
classDiagram
direction LR

class Work {
  +UUID id
  +String title
  +MediaType mediaType
  +Integer releaseYear
  +List<WorkCredit> credits
  +ExternalIds externalIds
  +Instant createdAt
  +Instant updatedAt
}

class Review {
  +UUID id
  +UUID workId
  +UserPublic author
  +int rating 1..5
  +String title
  +String body
  +boolean containsSpoilers
  +int likes
  +Instant createdAt
  +Instant updatedAt
}

class UserPublic {
  +UUID id
  +String displayName
}

class WorkCredit {
  +String role
  +String name
}

class ExternalIds {
  +String isbn10
  +String isbn13
  +String imdbId
}

enum MediaType {
  BOOK
  MOVIE
}

Work "1" o-- "0..*" WorkCredit : credits
Work "1" *-- "0..1" ExternalIds : externalIds
Work "1" --> "0..*" Review : has reviews
Review "1" --> "1" UserPublic : author


## JSON Schema

## Roles
* UI Specialist(s): Zach Filla, Andrew Bagby
* Business Logic/Persistence: Elijah Cassidy
* DevOps/Product Owner/Scrum Master/GitHub Admin: Bella Orr

## Github link
[Group7_EnterpriseApplicationDev](https://github.com/bella-orr/Group7_EnterpriseApplicationDev.git)

## Scrum Board

## Stand Up Link
[We meet 8:00 PM Eastern on Sunday in Teams](https://teams.microsoft.com/l/meetup-join/19%3ameeting_NDYzZGU4MzgtMWUwZS00OTU1LThjNDQtZWUzYjdiZWM2OTMx%40thread.v2/0?context=%7b%22Tid%22%3a%22f5222e6c-5fc6-48eb-8f03-73db18203b63%22%2c%22Oid%22%3a%2226d681d5-1ec6-40ee-aa28-ef79944434d3%22%7d 
)
