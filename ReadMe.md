# REGISTRATION ENDPOINT

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