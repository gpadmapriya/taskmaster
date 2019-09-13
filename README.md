### TaskMaster
#### Feature Tasks
  - User should be able to receive JSON data representing all of the tasks
    - Tasks should have a title, description. assignee, an id and an array to store history
  - User should be able to update task status using PUT
  - User should be able to make a GET request to /users/{name}/tasks and receive JSON data representing all of the tasks assigned to that user. This should work (i.e. return an empty array) if the requested username does not have any assigned tasks
  - User should be able to make a POST request to /tasks with body parameters for title, description and assignee to add a new task
  - User should be able to make a PUT request to /tasks/{id}/assign/{assignee} to assign a user to a task
  - Application should be deployed to EC2 with database on DynamoDB

[API Endpoint - Deployed application](http://taskmaster-newenv.us-west-2.elasticbeanstalk.com/)

#### Routes
  - GET route: /api/v1/tasks - returns task data in JSON format
  - POST route: /api/v1/tasks - takes title, assignee and description as body parameters and adds a new task. All tasks start with a status of "Available".
  - PUT route: /api/v1/tasks/{taskid}/state - advances status of task.
    - Tasks advance from Available to Assigned to Accepted to Finished.
  - GET route: /api/v1/users/{name}/tasks - returns JSON data representing all of the tasks assigned to that user
  - PUT route: /api/v1/tasks/{id}/assign/{assignee} - assigns a particular user to a task