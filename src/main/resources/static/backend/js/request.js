(function (win) {
  axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
  // Create an Axios instance
  const service = axios.create({
    //The base URL option is configured for the request in axios,
    // which indicates the public part of the request URL
    baseURL: '/',
    // timeout
    timeout: 10000
  })
  // REQUEST INTERCEPTOR
  service.interceptors.request.use(config => {
    // Whether a token needs to be set
    // const isToken = (config.headers || {}).isToken === false
    // if (getToken() && !isToken) {
    //   config.headers['Authorization'] = 'Bearer ' + getToken() // Let each request carry a custom token
    // }
    // GET REQUEST TO MAP THE PARAMS PARAMETER
    if (config.method === 'get' && config.params) {
      let url = config.url + '?';
      for (const propName of Object.keys(config.params)) {
        const value = config.params[propName];
        var part = encodeURIComponent(propName) + "=";
        if (value !== null && typeof(value) !== "undefined") {
          if (typeof value === 'object') {
            for (const key of Object.keys(value)) {
              let params = propName + '[' + key + ']';
              var subPart = encodeURIComponent(params) + "=";
              url += subPart + encodeURIComponent(value[key]) + "&";
            }
          } else {
            url += part + encodeURIComponent(value) + "&";
          }
        }
      }
      url = url.slice(0, -1);
      config.params = {};
      config.url = url;
    }
    return config
  }, error => {
      console.log(error)
      Promise.reject(error)
  })

  // Respond to the interceptor
  service.interceptors.response.use(res => {
      if (res.data.code === 0 && res.data.msg === 'NOTLOGIN') {// Return to the login page
        console.log('---/backend/page/login/login.html---')
        localStorage.removeItem('userInfo')
        window.top.location.href = '/backend/page/login/login.html'
      } else {
        return res.data
      }
    },
    error => {
      console.log('err' + error)
      let { message } = error;
      if (message == "Network Error") {
        message = "The backend interface is abnormally connected";
      }
      else if (message.includes("timeout")) {
        message = "The system interface request timed out";
      }
      else if (message.includes("Request failed with status code")) {
        message = "System interfaces" + message.substr(message.length - 3) + "abnormal";
      }
      window.ELEMENT.Message({
        message: message,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(error)
    }
  )
  win.$axios = service
})(window);
