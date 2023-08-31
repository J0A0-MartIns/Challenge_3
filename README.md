# Compass Challenge 3 - Post Processing Project

This project has been developed as part of the Compass challenge for post processing. 
The project aims to create an API to manage posts and related information.

## API Features

### 1. Process Post

- Description: Processes a new post.
- Method: POST
- Path: /posts/{postId}
- Requirements:
    - postId must be a number between 1 and 100.
    - postId must not be in use.

### 2. Disable Post

- Description: Disables a post that is in the "ENABLED" state.
- Method: DELETE
- Path: /posts/{postId}
- Requirements:
    - postId must be a number between 1 and 100.
    - postId must be in the "ENABLED" state.

### 3. Reprocess Post

- Description: Reprocesses a post that is in the "ENABLED" or "DISABLED" state.
- Method: PUT
- Path: /posts/{postId}
- Requirements:
    - postId must be a number between 1 and 100.
    - postId must be in the "ENABLED" or "DISABLED" state.

### 4. Query Posts

- Description: Provides a list of posts.
- Method: GET
- Path: /posts
- Response: A list of post objects in the specified format.

## How to Run the Project

1. Make sure you have the JDK (Java Development Kit) and Maven installed.
2. Clone this repository: `git clone <REPO_URL>`
3. Navigate to the project directory: `cd directory-name`
4. Run the project using Maven: `mvn spring-boot:run`

## Endpoints

- `POST /posts/{postId}` - Process a new post.
- `DELETE /posts/{postId}` - Disable a post.
- `PUT /posts/{postId}` - Reprocess a post.
- `POST /posts` - Create a new post.
- `GET /posts` - Get the list of posts.

## Author
João Marcos de Araújo Martins
