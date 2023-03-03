# REGISTRATION ENDPOINT
*For New Users*
1.  This endpoint allows user to register to the platform.
2.  This user remains unverified until email verified. 
3. Unverified User will not be allowed access to dashboard
4. This endpoint sends a token which expires in 10 minutes for
   verification to a users valid email address.
5. A code sample in Node JS using Axios is shown below
```
const axios = require('axios');
let data = JSON.stringify({
  "firstName": "Lekan", // This field is required
  "lastName": "Daniel", // This field is optional
  "email": "gab.oyinlola@gmail.com",
  // email field is required with a correct email format
  "phoneNumber": "08069580949",
  // Phone number field is required with a correct Nigerian phoneNumber format
  "password": "Test123!@#",
  // Password field is required with at least 8 caracters, 1 uppercase caracter, 1 lowercase caracter and 1 symbol
  "category": "COHORT_III"
  // Category field is required with a list of the available voting category available
  "imageURL": "www.cloudinary.com" //This field is required
});

let config = {
  method: 'post',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/registration/register',
  headers: { 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});
```

# Response from Registration End-Point
*This is a sample of the response from the Registration !!!*
```
{
    "timestamp": "2023-03-03 || 11:03:56",
    "status": "CREATED",
    "data": {
        "data": "User Registration successful"
    },
    "path": "/api/v1/registration/register",
    "successful": true
}
```

# Create User End Point
*For User Verification*

1.  This endpoint allows user to verify account to the platform. 
2. Unverified User will not be allowed access to dashboard
3. This endpoint responds with a Json Web Token which expires in 3 Hours
4. The Json web Token would be used to access other end-points
5. A code sample in Node JS using Axios is shown below
```
const axios = require('axios');
let data = JSON.stringify({
  "token": "1268", // This field is required
  "email": "gab.oyinlola@gmail.com" // This field is required
});

let config = {
  method: 'post',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/user/create',
  headers: { 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});
```

# Response from Create User EndPoint
*This is a sample of the response from the Verify User Endpoint !!!*

```
{
    "timestamp": "2023-03-03 || 11:08:17",
    "status": "OK",
    "data": {
        "data": <token>
    },
    "path": "/api/v1/user/create",
    "successful": true
}
```

# Login in User EndPoint

*Login Verified Users*

1.  This endpoint allows user to logIn to the platform.
2. Unverified User will not be allowed to logIn to the dashboard
3. Unverified User would have to resend Token to a Valid email from the resend token endpoint
4. This endpoint responds with a Json Web Token which expires in 3 Hours 
5. The Json web Token would be used to access other end-points
6. A code sample in Node JS using Axios is shown below

```
const axios = require('axios');
let data = JSON.stringify({
  "email": "gab.oyinlola@gmail.com", // This field is required
  "password": "Test123!@#" // This field is required
});

let config = {
  method: 'post',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/user/login',
  headers: { 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});
```

# Response from Login User EndPoint
*This is a sample of the response from the Login User Endpoint !!!*
```
{
    "timestamp": "2023-03-03 || 11:08:17",
    "status": "OK",
    "data": {
        "data": <token>
    },
     "path": "/api/v1/user/login",
    "successful": true
}
```
# Resend Token EndPoint

*For Users with unverified account*

1.  This endpoint resends a token which expires in 10 minutes for
verification to a users valid email address.
2. Once verified User can access the login endpoint to login to endpoint
```
const axios = require('axios');
let data = JSON.stringify({
  "email": "gab.oyinlola@gmail.com"
});

let config = {
  method: 'post',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/user/resend',
  headers: { 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});
```

# Response from Resend Token Endpoint
*This is a sample of the response from the Resend Token Endpoint !!!*
```
{
  "timestamp": "2023-03-03 || 11:15:19",
  "status": "OK",
  "data": {
    "data": "Token sent to email"
  },
  "path": "/api/v1/user/resend",
  "successful": true
}
```

# Verify Token Endpoint

1. This endpoint verifies token provided by the user

```
const axios = require('axios');
let data = JSON.stringify({
  "token": "1193",
  "email": "lekan.sofuyi01@gmail.com"
});

let config = {
  method: 'post',
maxBodyLength: Infinity,
  url: 'http://localhost:8080/api/v1/user/verify',
  headers: { 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});

```
# Response from Verify  Token EndPoint
*This is a sample of the response from the Verify Token Endpoint !!!*

```
{
    "timestamp": "2023-03-03 || 16:34:04",
    "status": "OK",
    "data": {
        "data": "Token Verified"
    },
    "path": "/api/v1/user/verify",
    "successful": true
}
```


# Create a Poll
*To create a Poll End-point*

1. This endpoint is used to create a poll for other users in the same category
2. It is only accessed by a verified user with a valid authorization
```
const axios = require('axios');
let data = JSON.stringify({
  "title": "Vote for next Cohort Priest",
  "question": "Whos the Next Cohort Priest",
  "startDateTime": "2023-03-03 13:33:00",
  "endDateTime": "2023-04-01 15:00:00",
  "category": "COHORT_IV",
  "candidates": [
    {
      "name": "Lekan"
    },
    {
      "name": "Yusuf"
    },
    {
      "name": "James"
    }
  ]
});

let config = {
  method: 'post',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/poll/create',
  headers: { 
    'Authorization': 'Bearer <Json Web Token>', 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});

```
# Create poll response
*This is a sample of the response from the Create Poll !!!*
```
{
    "timestamp": "2023-03-03 || 13:30:20",
    "status": "OK",
    "data": "Poll Successfully created",
    "path": "/api/v1/poll/create",
    "successful": true
}
```

# View Active Poll
1. This endpoint is used to view the active polls
2. It is only accessed by a verified user with a valid authorization

```
const axios = require('axios');

let config = {
  method: 'get',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/poll/active',
  headers: { 
    'Authorization': 'Bearer <Json Web Token>'
  }
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});

```

# View Active Poll Response
*This is a sample of the response from the View Active Poll Endpoint !!!*
```
{
    "timestamp": "2023-03-03 || 13:39:07",
    "status": "OK",
    "data": [
        {
            "id": 1,
            "title": "Vote for next Cohort Priest",
            "question": "Whos the Next Cohort Priest",
            "startDateTime": "2023-03-03T13:33:00",
            "endDateTime": "2023-04-01T15:00:00",
            "category": "COHORT_IV",
            "candidates": [
                {
                    "id": 1,
                    "name": "Lekan",
                    "result": {
                        "id": 1,
                        "noOfVotes": 0
                    }
                },
                {
                    "id": 2,
                    "name": "Yusuf",
                    "result": {
                        "id": 2,
                        "noOfVotes": 0
                    }
                },
                {
                    "id": 3,
                    "name": "James",
                    "result": {
                        "id": 3,
                        "noOfVotes": 0
                    }
                }
            ]
        }
    ],
    "path": "/api/v1/poll/active",
    "successful": true
}
```
# View Recent Poll
1. This endpoint is used to view the Non-active polls
2. It is only accessed by a verified user with a valid authorization
```
const axios = require('axios');

let config = {
  method: 'get',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/poll/recent',
  headers: { 
    'Authorization': 'Bearer <Json Web Token>'
  }
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});
```

# View Recent Poll Response
*This is a sample of the response from the View Recent Poll !!!*

```
{
    "timestamp": "2023-03-03 || 13:42:11",
    "status": "OK",
    "data": [],
    "path": "/api/v1/poll/recent",
    "successful": true
}
```

# Vote Endpoint
1. This endpoint is used to cast votes
2. It is only accessed by a verified user with a valid authorization
```
const axios = require('axios');
let data = JSON.stringify({
  "candidateId": 1
});

let config = {
  method: 'put',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/poll/vote/1',
  headers: { 
    'Authorization': 'Bearer <Json Web Token>', 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});
```

# Vote Endpoint Response
*This is a sample of the response from the Vote Endpoint !!!*
```
{
    "timestamp": "2023-03-03 || 13:57:40",
    "status": "OK",
    "data": "You have successfully casted your vote",
    "path": "/api/v1/poll/vote/1",
    "successful": true
}
```