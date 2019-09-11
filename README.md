### TaskMaster
#### Feature Tasks
  - User should be able to receive JSON data representing all of the tasks
    - Tasks should have a title, description and status
  - User should be able to Post request to /tasks and create posts
  - User should be able to update task status using PUT

[API Endpoint - Deployed application](http://taskmaster-newenv.us-west-2.elasticbeanstalk.com/)

#### Routes
  - GET route: /api/v1/tasks - returns task data in JSON format
  - POST route: /api/v1/tasks - takes title and description as body parameters and adds a new task. All tasks start with a status of "Available".
  - PUT route: /api/v1/tasks/{taskid}/state - advances status of task.
    - Tasks advance from Available to Assigned to Accepted to Finished.