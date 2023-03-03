```
REGISTRATION ENDPOINT

var axios = require('axios');
var data = JSON.stringify({
  "token": "1362",
  "email": "lekan.sofuyi1@gmail.com"
});

var config = {
  method: 'get',
  url: 'http://localhost:8080/api/v1/poll/recent',
  headers: { 
    'Content-Type': 'application/json', 
    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWthbi5zb2Z1eWkxQGdtYWlsLmNvbSIsImlhdCI6MTY3Nzc2MzM3NCwiZXhwIjoxNjc3NzY0ODE0fQ.7ERohFa2wya1BxxfElFSt2RbiXL00oh-srfy88q48i0'
  },
  data : data
};

axios(config)
.then(function (response) {
  console.log(JSON.stringify(response.data));
})
.catch(function (error) {
  console.log(error);
});

```
```
    SIGN OUT ENDPOINT
    var axios = require('axios');
var data = JSON.stringify({
  "token": "1362",
  "email": "lekan.sofuyi1@gmail.com"
});

var config = {
  method: 'get',
  url: 'http://localhost:8080/api/v1/poll/recent',
  headers: { 
    'Content-Type': 'application/json', 
    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWthbi5zb2Z1eWkxQGdtYWlsLmNvbSIsImlhdCI6MTY3Nzc2MzM3NCwiZXhwIjoxNjc3NzY0ODE0fQ.7ERohFa2wya1BxxfElFSt2RbiXL00oh-srfy88q48i0'
  },
  data : data
};

axios(config)
.then(function (response) {
  console.log(JSON.stringify(response.data));
})
.catch(function (error) {
  console.log(error);
});

```