```
REGISTRATION ENDPOINT

const axios = require('axios');
let data = JSON.stringify({
  "firstName": "Lekan",
  "lastName": "Daniel",
  "email": "gab.oyinlola@gmail.com",
  "phoneNumber": "08069580949",
  "password": "Test123!@#",
  "category": "COHORT_III"
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

```
Response from Registration End-Point

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

```
Create User End Point

const axios = require('axios');
let data = JSON.stringify({
  "token": "1268",
  "email": "gab.oyinlola@gmail.com"
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

```
Response from Create User EndPoint

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

```
Login in User EndPoint
const axios = require('axios');
let data = JSON.stringify({
  "email": "gab.oyinlola@gmail.com",
  "password": "Test123!@#"
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

```
Response from Login User EndPoint
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
```
Resend Token EndPoint
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

```
Response from Resend Token Endpoint
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