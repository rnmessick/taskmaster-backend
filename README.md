# Taskmaster

## [Deployed Endpoint](http://taskmasterbackend-env.yzch9c73jx.us-west-2.elasticbeanstalk.com/api/v1/tasks)
## [Deployed Frontend]( http://taskmasterrenee.s3-website-us-west-2.amazonaws.com)

### Available Routes
* GET: `/api/v1/tasks` - returns all tasks from the db in `json` format.
* GET: `/api/v1/users/{name}/tasks` - returns all task assigned to the `name`
* POST: `/api/v1/tasks` - takes in `json` data in the post body to create a new task.
    * Example post request
        ```
        {
            "title": "submit assignment",
            "description": "submit it and get some points!"
        }
        ```
* PUT: `/api/v1/tasks/{id}/state` - takes in a task `id` as a path variable, and updates that task to the next status.
    * Statuses progress from:  `Available` -> `Assigned` -> `Accepted` -> `Finished`.
* PUT: `/api/v1/tasks/{id}/assign/{assignee}` - assigns a task to an assignee
* POST `/tasks/{id}/images` - Adds an image related to a particular task(by id).

## Collaborators
* Kevin Couture
* Travis Cox
* Marisha Hosa
* Renee Messick
* Nick Paro

